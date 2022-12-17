package com.example.springmemo.controller;

import com.example.springmemo.dto.PostDeleteRequestDto;
import com.example.springmemo.dto.PostRequestDto;
import com.example.springmemo.dto.PostResponseDto;
import com.example.springmemo.entity.Post;
import com.example.springmemo.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping("/posts")
    public List<PostResponseDto> getPosts() {
        return postService.getPosts();
    }

    @PostMapping("/posts")
    public Post createPost(@RequestBody PostRequestDto requestDto) {
        return postService.createPost(requestDto);
    }

    @GetMapping("/posts/{id}")
    public PostResponseDto getPost(@PathVariable Long id) {
        return postService.getPost(id);
    }

    @PutMapping("/posts/{id}")
    public PostResponseDto updatePost(@PathVariable Long id, @RequestBody PostRequestDto requestDto) {
        return postService.updatePost(id, requestDto);
    }

    @DeleteMapping("/posts/{id}")
    public String deletePost(@PathVariable Long id, @RequestBody PostDeleteRequestDto requestDto) {
        return postService.deletePost(id, requestDto);
    }
}
