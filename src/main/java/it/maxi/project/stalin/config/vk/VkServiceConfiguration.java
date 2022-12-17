package it.maxi.project.stalin.config.vk;

import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.ServiceActor;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class VkServiceConfiguration {

    @Bean
    public VkApiClient vkApiClient() {
        return new VkApiClient(new HttpTransportClient());
    }

    @Bean
    public ServiceActor vkServiceActor(
            @Value("${it.maxi.project.stalin.vk.app.id}") Integer appId,
            @Value("${it.maxi.project.stalin.vk.app.secret}") String secret,
            @Value("${it.maxi.project.stalin.vk.app.token}") String token
    ) {
        return new ServiceActor(appId, secret, token);
    }

}
