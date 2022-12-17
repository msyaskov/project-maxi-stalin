package it.maxi.project.stalin.controller.user.sign;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserSignInController {

    @GetMapping("/user/signin")
    public String signIn() {
        System.out.println("GET SingIn");
        return "user/signin";
    }

}
