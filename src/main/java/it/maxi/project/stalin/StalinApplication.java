package it.maxi.project.stalin;

import it.maxi.project.stalin.service.vk.VkService;
import it.maxi.project.utilita.array.Ints;
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
        System.out.println(Ints.of(1, 2, 3));
    }

    @Bean
    public CommandLineRunner clr(VkService vkService) {
        return (args) -> {
//            vkService.start();
        };
    }

}
