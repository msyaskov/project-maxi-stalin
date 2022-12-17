package it.maxi.project.stalin.service.vk;

import com.vk.api.sdk.objects.wall.WallpostFull;

import java.util.List;

public interface VkService {

    List<WallpostFull> getWallPosts(Integer ownerId, int count);

}
