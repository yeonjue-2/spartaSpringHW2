package com.example.springmemo.service;

import com.example.springmemo.dto.CommentRequest;
import com.example.springmemo.dto.CommentResponse;
import com.example.springmemo.entity.Comment;
import com.example.springmemo.entity.Post;
import com.example.springmemo.entity.User;
import com.example.springmemo.repository.CommentRepository;
import com.example.springmemo.repository.PostRepository;
import com.example.springmemo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    public CommentResponse saveComment(Long postId, CommentRequest requestDto, String usernameOfToken) {

        Post post = postRepository.findById(postId).orElseThrow(
                () -> new IllegalArgumentException("포스트가 존재하지 않습니다.")
        );

        User user = userRepository.findByUsername(usernameOfToken).orElseThrow(
                () -> new IllegalArgumentException("사용자가 존재하지 않습니다.")
        );

        Comment comment = new Comment(requestDto, post, user);
        commentRepository.save(comment);
        return new CommentResponse(comment, post, user);
    }
}
