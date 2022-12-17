package it.maxi.project.stalin;

import com.vk.api.sdk.objects.wall.WallpostFull;
import it.maxi.project.stalin.model.user.User;
import it.maxi.project.stalin.repository.user.RoleRepository;
import it.maxi.project.stalin.service.user.UserService;
import it.maxi.project.stalin.service.vk.VkService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;
import java.util.Set;

@Slf4j
@SpringBootApplication
public class StalinApplication {

    public static void main(String[] args) {
        SpringApplication.run(StalinApplication.class, args);
    }

    @Bean
    public CommandLineRunner clr(@Value("${it.maxi.project.stalin.resource.vk}") String[] vkResources, VkService vkService) {
        return (args) -> {
            for (String vkResource : vkResources) {
                List<WallpostFull> wallPosts = vkService.getWallPosts(Integer.parseInt(vkResource), 2);
                wallPosts.forEach(wallPost -> {
                    log.info("Downloaded post: {}:{}", wallPost.getOwnerId(), wallPost.getId());
                });
            }
        };
    }

}
