package it.maxi.project.stalin.controller;

import it.maxi.project.stalin.model.vk.VkPost;
import it.maxi.project.stalin.service.vk.VkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Set;

@Controller
public class ModerationController {

    @Autowired
    private VkService vkService;

    @GetMapping("/moderation")
    public String moderation(Model model) {
        Set<VkPost> vkPosts = vkService.getAllVkPosts();
        model.addAttribute("vkposts", vkPosts);
        return "moderation";
    }

}
