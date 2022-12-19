package it.maxi.project.stalin.model.vk;

import com.vk.api.sdk.objects.photos.Photo;
import com.vk.api.sdk.objects.photos.PhotoSizes;
import com.vk.api.sdk.objects.wall.WallpostAttachment;
import com.vk.api.sdk.objects.wall.WallpostFull;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "vkpost")
@IdClass(VkPost.VkPostId.class)
public class VkPost {

    @NoArgsConstructor
    @AllArgsConstructor
    public static class VkPostId implements Serializable {

        @Getter@Setter
        private Integer postId;

        @Getter@Setter
        private Integer groupId;

        @Override
        public int hashCode() {
            return postId.hashCode() + groupId.hashCode();
        }

        @Override
        public boolean equals(Object obj) {
            return (obj instanceof VkPostId vkPostId) && postId.equals(vkPostId.postId) && groupId.equals(vkPostId.groupId);
        }
    }

    @Id
    @Getter@Setter
    private Integer postId;

    @Id
    @Getter@Setter
    private Integer groupId;

    @Getter@Setter
    @Column(length = 4096)
    private String text;

    @Getter@Setter
    @Column(nullable = false)
    private Date publicationDate;

    @Getter@Setter
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumns({
            @JoinColumn(name="post_id"),
            @JoinColumn(name="group_id")
    })
    private List<VkPhoto> photos;

    public static VkPost from(WallpostFull wallpostFull) {
        VkPost vkPost = new VkPost();
        vkPost.setGroupId(wallpostFull.getOwnerId());
        vkPost.setPostId(wallpostFull.getId());
        vkPost.setText(wallpostFull.getText());
        vkPost.setPublicationDate(new Date(wallpostFull.getDate() * 1000L));

        List<VkPhoto> vkPhotos = new ArrayList<>();
        List<WallpostAttachment> attachments = wallpostFull.getAttachments();
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

        return vkPost;
    }

}
