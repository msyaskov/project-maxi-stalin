package it.maxi.project.stalin.service.vk;

import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.ServiceActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.objects.wall.responses.GetResponse;
import it.maxi.project.stalin.model.vk.VkGroup;
import it.maxi.project.stalin.model.vk.VkPost;
import it.maxi.project.stalin.repository.vk.VkGroupRepository;
import it.maxi.project.stalin.repository.vk.VkPostRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
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

        this.getAllVkGroups().forEach(vkGroup -> {
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
                requestedPosts.addAll(response.getItems().stream().map(VkPost::from).collect(Collectors.toSet()));
            }
        });

        return requestedPosts;
    }

    public VkPost save(VkPost vkPost) {
        VkGroup vkGroup = vkGroupRepository.findById(vkPost.getGroupId()).orElse(null);
        if (vkGroup == null) {
            // add new group
        } else {
            if (vkPost.getPublicationDate().compareTo(vkGroup.getLastPostDate()) > 0) {
                vkGroup.setLastPostDate(vkPost.getPublicationDate());
                vkGroupRepository.save(vkGroup);
            }
        }
        return vkPostRepository.save(vkPost);
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
