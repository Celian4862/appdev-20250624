package com.example.demo.controller;
import com.example.demo.service.PostService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/posts")
public class PostController {
    private final PostService postService;
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/photo")
    public String findAll() {
        return postService.findAll();
    }

    @GetMapping("/photo/{id}")
    public String findById(@PathVariable Long id) {
        return postService.findById(id);
    }

    @PostMapping(path = {"/", ""}, consumes = {"text/plain", "application/json"}, produces = "application/json")
    public @ResponseBody Object createPost(@RequestBody String postData) {
        return postService.createPost(postData);
    }

    @PostMapping(path = {"/{id}/comments", "/{id}/comments/"}, consumes = {"text/plain", "application/json"}, produces = "application/json")
    public @ResponseBody Object createComment(@PathVariable Long id, @RequestBody String commentData) {
        return postService.createComment(id, commentData);
    }

    @PostMapping(path = {"/{id}/likes", "/{id}/likes/"}, produces = "application/json")
    public @ResponseBody Object likePost(@PathVariable Long id) {
        return postService.likePost(id);
    }
}
