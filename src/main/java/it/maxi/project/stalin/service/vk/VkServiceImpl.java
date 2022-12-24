package it.maxi.project.stalin.service.vk;

import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.ServiceActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.objects.photos.Photo;
import com.vk.api.sdk.objects.photos.PhotoSizes;
import com.vk.api.sdk.objects.video.Video;
import com.vk.api.sdk.objects.wall.WallpostAttachment;
import com.vk.api.sdk.objects.wall.responses.GetResponse;
import it.maxi.project.stalin.model.vk.VkGroup;
import it.maxi.project.stalin.model.vk.VkPhoto;
import it.maxi.project.stalin.model.vk.VkPost;
import it.maxi.project.stalin.repository.vk.VkGroupRepository;
import it.maxi.project.stalin.repository.vk.VkPostRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLIntegrityConstraintViolationException;
import java.time.ZoneId;
import java.util.*;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Slf4j
@Service
public class VkServiceImpl implements VkService {

    @Autowired
    private ScheduledExecutorService executorService;

    private ScheduledFuture<?> scheduledFuture;

    @Autowired
    private ServiceActor serviceActor;

    @Autowired
    private VkApiClient vkApiClient;

    @Autowired
    private VkPostRepository vkPostRepository;

    @Autowired
    private VkGroupRepository vkGroupRepository;

    @Override
    public Set<VkPost> requestNewPosts(int countFromEachVkGroup) {
        Set<VkPost> requestedPosts = new HashSet<>();
        Set<VkGroup> vkGroups = this.getAllVkGroups();

        vkGroups.forEach(vkGroup -> {
            GetResponse response;
            try {
                response = vkApiClient.wall().get(serviceActor)
                        .ownerId(vkGroup.getId())
                        .count(countFromEachVkGroup)
                        .execute();
            } catch (ApiException | ClientException e) {
                throw new RuntimeException(e);
            }

            if (response != null && response.getItems() != null) {
                response.getItems().forEach(wallPostFull -> {
                    VkPost vkPost = new VkPost();
                    vkPost.setPostId(wallPostFull.getId());
                    vkPost.setGroup(vkGroup);
                    vkPost.setText(wallPostFull.getText());
                    vkPost.setVkPublicationDate(new Date(wallPostFull.getDate() * 1000L).toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
                    vkPost.setLikes(wallPostFull.getLikes() == null ? 0 : wallPostFull.getLikes().getCount());
                    vkPost.setComments(wallPostFull.getComments() == null ? 0 : wallPostFull.getComments().getCount());
                    vkPost.setViews(wallPostFull.getViews() == null ? 0 : wallPostFull.getViews().getCount());

                    List<VkPhoto> vkPhotos = new ArrayList<>();
                    List<WallpostAttachment> attachments = wallPostFull.getAttachments();
                    if (attachments != null) {
                        attachments.forEach(
                                attachment ->  {
                                    Photo photo = attachment.getPhoto();
                                    if (photo != null) {
                                        List<PhotoSizes> photoSizes = photo.getSizes();
                                        if (photoSizes != null && photoSizes.size() > 0) {
                                            VkPhoto vkPhoto = new VkPhoto();
                                            vkPhoto.setUrl(photoSizes.get(photoSizes.size() - 1).getUrl().toString());
                                            vkPhotos.add(vkPhoto);
                                        }
                                    }
                                }
                        );
                    }
                    vkPost.setPhotos(vkPhotos);

                    requestedPosts.add(vkPost);
                });
            }
        });

        return requestedPosts;
    }

    public VkPost save(VkPost vkPost) {
        if (vkPost.getVkPublicationDate().compareTo(vkPost.getGroup().getLastPostDate()) > 0) {
            vkPost.getGroup().setLastPostDate(vkPost.getVkPublicationDate());
            vkGroupRepository.save(vkPost.getGroup());

        }
        VkPost saved = null;
        try {
            saved = vkPostRepository.save(vkPost);
        } catch (Exception e) {
            Throwable cause = e.getCause();
            while (cause.getCause() != null) {
                cause = cause.getCause();
            }
            if (cause instanceof SQLIntegrityConstraintViolationException sqlE && sqlE.getErrorCode() == 1062) {
                // duplicate vkpost.post_id & vkpost.group_id
            } else {
                throw new RuntimeException(e);
            }
        }
        return saved;
    }

    @Override
    public VkPost getVkPost(Integer id) {
        return vkPostRepository.findById(id).orElse(null);
    }

    @Override
    public Set<VkPost> getAllVkPosts() {
        return new HashSet<>(vkPostRepository.findAll());
    }

    @Override
    public Set<VkGroup> getAllVkGroups() {
        return new HashSet<>(vkGroupRepository.findAll());
    }

    @Override
    public void start() {
        if (scheduledFuture == null) {
            scheduledFuture = executorService.scheduleAtFixedRate(this, 0, 10, TimeUnit.SECONDS);
        } else {
            throw new RuntimeException("executorService was started");
        }
    }

    @Override
    public void stop() {
        scheduledFuture.cancel(false);
        scheduledFuture = null;
    }

    @Override
    public void run() {
        Set<VkPost> vkPosts = requestNewPosts(10);
        log.info("response {} posts", vkPosts.size());
        vkPosts.forEach(this::save);
    }
}
