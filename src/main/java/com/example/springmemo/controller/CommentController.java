package com.example.springmemo.controller;

import com.example.springmemo.dto.CommentRequest;
import com.example.springmemo.dto.CommentResponse;
import com.example.springmemo.entity.Comment;
import com.example.springmemo.jwt.JwtUtil;
import com.example.springmemo.service.CommentService;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/comments")
public class CommentController {
    private final CommentService commentService;
    private final JwtUtil jwtUtil;

    @PostMapping("/{postId}")
    public CommentResponse createComment(@PathVariable Long postId, @RequestBody CommentRequest requestDto, HttpServletRequest request) {
        String token = jwtUtil.resolveToken(request);
        Claims claims;

        if (token != null) {
            if (!jwtUtil.validateToken(token)) {
                throw new IllegalArgumentException("토큰값이 잘못되었습니다.");
            }
            claims = jwtUtil.getUserInfoFromToken(token);
            String usernameOfToken = claims.getSubject();

            return commentService.saveComment(postId, requestDto, usernameOfToken);
        } else {
            throw new IllegalArgumentException("토큰이 존재하지 않습니다.");
        }
    }
}