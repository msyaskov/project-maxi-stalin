package it.maxi.project.stalin;

import it.maxi.project.stalin.model.user.User;
import it.maxi.project.stalin.repository.user.RoleRepository;
import it.maxi.project.stalin.service.user.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Set;

@SpringBootApplication
public class StalinApplication {

    public static void main(String[] args) {
        SpringApplication.run(StalinApplication.class, args);
    }

    @Bean
    public CommandLineRunner clr() {
        return (args) -> {
        };
    }

}
