package it.maxi.project.stalin;

import it.maxi.project.stalin.model.vk.VkPost;
import it.maxi.project.stalin.repository.vk.VkPostRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@Slf4j
@SpringBootApplication
public class StalinApplication {

    public static void main(String[] args) {
        SpringApplication.run(StalinApplication.class, args);
    }

    @Bean
    public CommandLineRunner clr(VkPostRepository vkPostRepository) {
        return (args) -> {
            VkPost vkPost = new VkPost();
            vkPost.setPostId(5);
            vkPost.setGroupId(-217453646);
            vkPost.setText("test text");
            vkPostRepository.save(vkPost);
        };
    }

}
