package it.maxi.project.stalin.service.vk;

import it.maxi.project.stalin.model.vk.VkGroup;
import it.maxi.project.stalin.model.vk.VkPost;

import java.util.List;
import java.util.Set;

public interface VkService extends Runnable {

    Set<VkPost> requestNewPosts(int count);

    VkPost save(VkPost vkPost);

    Set<VkPost> getAllVkPosts();

    Set<VkGroup> getAllVkGroups();

    void start();

    void stop();

}
