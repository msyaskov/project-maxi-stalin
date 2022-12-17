package it.maxi.project.stalin.model.vk;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

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

}
