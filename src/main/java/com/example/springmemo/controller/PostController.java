package com.example.springmemo.controller;

import com.example.springmemo.dto.*;
import com.example.springmemo.jwt.JwtUtil;
import com.example.springmemo.service.PostService;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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

    @GetMapping("/posts/{id}")
    public PostResponse getPost(@PathVariable Long id) {
        return postService.getPost(id);
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

    @PutMapping("/posts/{id}")
    public PostResponse updatePost(@PathVariable Long id, @RequestBody PostRequest requestDto, HttpServletRequest request) {
        String token = jwtUtil.resolveToken(request);
        Claims claims;

        if (token != null) {
            if (!jwtUtil.validateToken(token)) {
                throw new IllegalArgumentException("토큰값이 잘못되었습니다.");
            }
            claims = jwtUtil.getUserInfoFromToken(token);
            String usernameOfToken = claims.getSubject();

            return postService.updatePost(id, requestDto, usernameOfToken);
        } else {
            throw new IllegalArgumentException("토큰이 존재하지 않습니다.");
        }
    }

    @DeleteMapping("/posts/{id}")
    public ResponseEntity<String> deletePost(@PathVariable Long id, HttpServletRequest request) {
        postService.deletePost(id, request);
        return new ResponseEntity<>("Delete Success", HttpStatus.OK);
    }
}
