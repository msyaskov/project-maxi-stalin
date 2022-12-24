package it.maxi.project.stalin.controller.post;

import it.maxi.project.stalin.model.vk.VkPost;
import it.maxi.project.stalin.service.vk.VkService;
import it.maxi.project.stalin.util.Json;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;
import java.util.stream.Collectors;

public class PostController {

    @Autowired
    private VkService vkService;

    @PostMapping("/post/list-id")
    @ResponseBody
    public String getPostIdList() {
        String collect = vkService.getAllVkPosts().stream().map(VkPost::getId).map(String::valueOf).collect(Collectors.joining(","));
        return "{ \"status\": 200, \"data\": [" + collect + "]}";
    }

    @PostMapping("/post/{postId}")
    public String getPostById(@PathVariable("postId") int postId, Model model) {
        VkPost vkPost = vkService.getVkPost(postId);
        model.addAttribute("post",vkPost);
        return "moder/post_row";
    }

    @PostMapping("/post/publish-{id}")
    @ResponseBody
    public String publishById(@PathVariable("id") int postId) {
        return Json.of(Map.of("status", "200"));
    }

    @PostMapping("/post/edit-{id}")
    @ResponseBody
    public String editById(@PathVariable("id") int postId) {
        return Json.of(Map.of("status", "200"));
    }

    @PostMapping("/post/delete-{id}")
    @ResponseBody
    public String deleteById(@PathVariable("id") int postId) {
        return Json.of(Map.of("status", "200"));
    }

}
