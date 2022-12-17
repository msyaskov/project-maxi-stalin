package it.maxi.project.stalin.service.vk;

import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.ServiceActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.objects.wall.WallpostFull;
import com.vk.api.sdk.objects.wall.responses.GetResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VkServiceImpl implements VkService {

    @Autowired
    private ServiceActor serviceActor;

    @Autowired
    private VkApiClient vkApiClient;

    @Override
    public List<WallpostFull> getWallPosts(Integer ownerId, int count) {
        GetResponse response;
        try {
            response = vkApiClient.wall().get(serviceActor)
                    .ownerId(ownerId)
                    .count(count)
                    .execute();
        } catch (ApiException | ClientException e) {
            throw new RuntimeException(e);
        }

        return (response != null && response.getItems() != null) ? response.getItems() : List.of();
    }
}
