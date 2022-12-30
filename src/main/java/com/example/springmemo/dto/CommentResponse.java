package com.example.springmemo.dto;

import com.example.springmemo.entity.Comment;
import com.example.springmemo.entity.Post;
import com.example.springmemo.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class CommentResponse {
    private Long postId;
    private Long userId;
    private Long id;
    private String contents;
    private LocalDateTime modifiedAt;

    public CommentResponse(Comment comment) {
        this.postId = comment.getPost().getId();
        this.userId = comment.getUser().getId();
        this.id = comment.getId();
        this.contents = comment.getContents();
        this.modifiedAt = comment.getModifiedAt();
    }
}
