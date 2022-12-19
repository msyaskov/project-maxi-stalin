package it.maxi.project.stalin.service.vk;

import com.vk.api.sdk.objects.wall.WallpostFull;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class VkServiceImplTest {

    @Autowired
    private VkService vkService;

    @Test
    public void getWallPosts() {
//        List<WallpostFull> wallPosts = vkService.requestNewPosts(-217453646, 1);
//        assertNotEquals(0, wallPosts.size());
//
//        WallpostFull wallPost = wallPosts.get(0);
//        assertEquals(-217453646, wallPost.getOwnerId());
//        assertNotNull(wallPost.getId());
    }

}