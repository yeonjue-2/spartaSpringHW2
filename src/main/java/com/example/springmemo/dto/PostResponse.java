package com.example.springmemo.dto;

import com.example.springmemo.entity.Comment;
import com.example.springmemo.entity.Post;
import com.example.springmemo.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
public class PostResponse {
    private String title;
    private String userName;
    private String contents;
    private String modifiedAt;
    private List<CommentResponse> commentList;


    public PostResponse(Post post, User user) {
        this.title = post.getTitle();
        this.userName = user.getUsername();
        this.contents = post.getContents();
        this.modifiedAt = String.valueOf(post.getModifiedAt());
    }

    public PostResponse(Post post, User user, List<Comment> commentList) {
        this.title = post.getTitle();
        this.userName = user.getUsername();
        this.contents = post.getContents();
        this.modifiedAt = String.valueOf(post.getModifiedAt());
        this.commentList = commentList.stream()
                            .map(CommentResponse::new)
                            .collect(Collectors.toList());
    }
}
