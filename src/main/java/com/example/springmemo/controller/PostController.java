package com.example.springmemo.controller;

import com.example.springmemo.dto.*;
import com.example.springmemo.jwt.JwtUtil;
import com.example.springmemo.service.PostService;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final JwtUtil jwtUtil;

    @GetMapping("/posts")
    public List<PostResponse> getPosts() {
        return postService.getPosts();
    }

    @PostMapping("/posts")
    public PostResponse createPost(@RequestBody PostRequest requestDto, HttpServletRequest request) {
        String token = jwtUtil.resolveToken(request);
        Claims claims;

        if (token != null) {
            if (!jwtUtil.validateToken(token)) {
                throw new IllegalArgumentException("토큰값이 잘못되었습니다.");
            }
            claims = jwtUtil.getUserInfoFromToken(token);
            String usernameOfToken = claims.getSubject();

            return postService.createPost(requestDto, usernameOfToken);
        } else {
            throw new IllegalArgumentException("토큰이 존재하지 않습니다.");
        }
    }

    @GetMapping("/posts/{id}")
    public PostResponse getPost(@PathVariable Long id) {
        return postService.getPost(id);
    }

    @PutMapping("/posts/{id}")
    public PostResponse updatePost(@PathVariable Long id, @RequestBody PostRequest requestDto, HttpServletRequest request) {
        return postService.updatePost(id, requestDto, request);
    }

    @DeleteMapping("/posts/{id}")
    public StatusResponse deletePost(@PathVariable Long id, HttpServletRequest request, HttpServletResponse response) {
        return postService.deletePost(id, request, response);
    }
}
