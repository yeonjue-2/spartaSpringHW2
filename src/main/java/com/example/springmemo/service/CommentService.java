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

    public CommentResponse saveComment(Long postId, CommentRequest requestDto, String username) {

        Post post = postRepository.findById(postId).orElseThrow(
                () -> new IllegalArgumentException("포스트가 존재하지 않습니다.")
        );

        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new IllegalArgumentException("사용자가 존재하지 않습니다.")
        );

        Comment comment = new Comment(requestDto, post, user);
        commentRepository.save(comment);
        return new CommentResponse(comment);
    }

    public CommentResponse modifyComment(Long id, CommentRequest requestDto, String username) {
        Comment comment = commentRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("댓글이 존재하지 않습니다.")
        );

        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new IllegalArgumentException("사용자가 존재하지 않습니다.")
        );

        if (comment.isWriter(user.getUsername())) {
            comment.update(requestDto, user);
            commentRepository.save(comment);
        } else {
            throw new IllegalArgumentException("해당 유저만 사용할 수 있습니다.");
        }

        return new CommentResponse(comment);
    }

    public CommentResponse adminModifyComment(Long id, CommentRequest requestDto, String username) {
        Comment comment = commentRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("댓글이 존재하지 않습니다.")
        );

        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new IllegalArgumentException("사용자가 존재하지 않습니다.")
        );

        comment.update(requestDto, user);
        commentRepository.save(comment);

        return new CommentResponse(comment);
    }

    public void removeComment(Long id, String username) {
        Comment comment = commentRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("댓글이 존재하지 않습니다.")
        );

        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new IllegalArgumentException("사용자가 존재하지 않습니다.")
        );

        if (comment.isWriter(user.getUsername())) {
            commentRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("해당 유저만 사용할 수 있습니다.");
        }
    }

    public void adminRemoveComment(Long id) {
        Comment comment = commentRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("댓글이 존재하지 않습니다.")
        );

        commentRepository.deleteById(id);

    }
}
