package com.example.springmemo.controller;

import com.example.springmemo.dto.*;
import com.example.springmemo.entity.UserRoleEnum;
import com.example.springmemo.jwt.AuthenticatedUser;
import com.example.springmemo.jwt.JwtUtil;
import com.example.springmemo.service.PostService;
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
        AuthenticatedUser authenticatedUser = jwtUtil.validateAndGetInfo(token);
        return postService.createPost(requestDto, authenticatedUser.getUsername());
    }

    @PutMapping("/posts/{id}")
    public PostResponse updatePost(@PathVariable Long id, @RequestBody PostRequest requestDto, HttpServletRequest request) {
        String token = jwtUtil.resolveToken(request);
        AuthenticatedUser authenticatedUser = jwtUtil.validateAndGetInfo(token);
        return postService.updatePost(id, requestDto, authenticatedUser.getUsername());
    }

    @PutMapping("/admin/posts/{id}")
    public PostResponse adminUpdatePost(@PathVariable Long id, @RequestBody PostRequest requestDto, HttpServletRequest request) {
        String token = jwtUtil.resolveToken(request);
        AuthenticatedUser authenticatedUser = jwtUtil.validateAndGetInfo(token);

        if (!authenticatedUser.getUserRoleEnum().equals(UserRoleEnum.ADMIN)) {
            throw new IllegalArgumentException("????????? ????????????.");
        }

        return postService.adminUpdatePost(id, requestDto, authenticatedUser.getUsername());
    }

    @DeleteMapping("/posts/{id}")
    public ResponseEntity<String> deletePost(@PathVariable Long id, HttpServletRequest request) {
        // ??????
        String token = jwtUtil.resolveToken(request);
        AuthenticatedUser authenticatedUser = jwtUtil.validateAndGetInfo(token);

        postService.deletePost(id, authenticatedUser.getUsername());

        return new ResponseEntity<>("Delete Success", HttpStatus.OK);
    }

    @DeleteMapping("/admin/posts/{id}")
    public ResponseEntity<String> adminDeletePost(@PathVariable Long id, HttpServletRequest request) {
        // ??????
        String token = jwtUtil.resolveToken(request);
        AuthenticatedUser authenticatedUser = jwtUtil.validateAndGetInfo(token);

        // ??????
        if (!authenticatedUser.getUserRoleEnum().equals(UserRoleEnum.ADMIN)) {
            throw new IllegalArgumentException("????????? ????????????.");
        }

        postService.adminDeletePost(id);

        return new ResponseEntity<>("Delete Success", HttpStatus.OK);
    }
}
