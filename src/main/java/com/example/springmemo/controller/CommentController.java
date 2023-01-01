package com.example.springmemo.controller;

import com.example.springmemo.dto.CommentRequest;
import com.example.springmemo.dto.CommentResponse;
import com.example.springmemo.entity.UserRoleEnum;
import com.example.springmemo.jwt.AuthenticatedUser;
import com.example.springmemo.jwt.JwtService;
import com.example.springmemo.jwt.JwtUtil;
import com.example.springmemo.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/")
public class CommentController {
    private final CommentService commentService;
    private final JwtUtil jwtUtil;
    private final JwtService jwtService;

    @PostMapping("/posts/{postId}/comments")
    public CommentResponse createComment(@PathVariable Long postId, @RequestBody CommentRequest requestDto, HttpServletRequest request) {

        String token = jwtUtil.resolveToken(request);
        AuthenticatedUser authenticatedUser = jwtService.validateAndGetInfo(token);
        return commentService.saveComment(postId, requestDto, authenticatedUser.getUsername());
    }

    @PutMapping("/comments/{id}")
    public CommentResponse updateComment(@PathVariable Long id, @RequestBody CommentRequest requestDto, HttpServletRequest request) {
        String token = jwtUtil.resolveToken(request);
        AuthenticatedUser authenticatedUser = jwtService.validateAndGetInfo(token);
        return commentService.modifyComment(id, requestDto, authenticatedUser.getUsername());
    }

    @PutMapping("/admin/comments/{id}")
    public CommentResponse adminUpdateComment(@PathVariable Long id, @RequestBody CommentRequest requestDto, HttpServletRequest request) {
        String token = jwtUtil.resolveToken(request);
        AuthenticatedUser authenticatedUser = jwtService.validateAndGetInfo(token);

        if (!authenticatedUser.getUserRoleEnum().equals(UserRoleEnum.ADMIN)) {
            throw new IllegalArgumentException("권한이 없습니다.");
        }

        return commentService.adminModifyComment(id, requestDto, authenticatedUser.getUsername());
    }

    @DeleteMapping("/comments/{id}")
    public ResponseEntity<String> deleteComment(@PathVariable Long id, HttpServletRequest request) {
        String token = jwtUtil.resolveToken(request);
        AuthenticatedUser authenticatedUser = jwtService.validateAndGetInfo(token);

        commentService.removeComment(id, authenticatedUser.getUsername());

        return new ResponseEntity<>("Delete Success", HttpStatus.OK);
    }

    @DeleteMapping("/admin/comments/{id}")
    public ResponseEntity<String> adminDeleteComment(@PathVariable Long id, HttpServletRequest request) {
        String token = jwtUtil.resolveToken(request);
        AuthenticatedUser authenticatedUser = jwtService.validateAndGetInfo(token);

        if (!authenticatedUser.getUserRoleEnum().equals(UserRoleEnum.ADMIN)) {
            throw new IllegalArgumentException("권한이 없습니다.");
        }

        commentService.adminRemoveComment(id);

        return new ResponseEntity<>("Delete Success", HttpStatus.OK);
    }
}
