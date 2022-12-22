package com.example.springmemo.dto;

import com.example.springmemo.entity.Post;
import com.example.springmemo.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PostResponse {
    private String title;
    private String userName;
    private String contents;
    private String modifiedAt;


    public PostResponse(Post post, User user) {
        this.title = post.getTitle();
        this.userName = user.getUsername();
        this.contents = post.getContents();
        this.modifiedAt = String.valueOf(post.getModifiedAt());
    }
}
