package it.maxi.project.stalin.repository.vk;

import it.maxi.project.stalin.model.vk.VkPost;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VkPostRepository extends JpaRepository<VkPost, VkPost.VkPostId> {
}
