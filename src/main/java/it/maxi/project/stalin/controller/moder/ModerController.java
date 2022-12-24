package it.maxi.project.stalin.controller.moder;

import it.maxi.project.stalin.model.vk.VkPost;
import it.maxi.project.stalin.service.vk.VkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class ModerController {

    @Autowired
    private VkService vkService;

    @GetMapping("/moder")
    public String get(Model model) {
        List<VkPost> vkPosts = vkService.getAllVkPosts()
                .stream()
                .sorted(Comparator.comparing(VkPost::getVkPublicationDate))
                .collect(Collectors.toList());
        model.addAttribute("posts", vkPosts);
        return "moder/index";
    }

}
