package com.example.springmemo.dto;

import com.example.springmemo.entity.Post;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PostResponseDto {
    private String title;
    private String userName;
    private String contents;
    private String modifiedAt;


    public PostResponseDto(Post post) {
        this.title = post.getTitle();
        this.userName = post.getUserName();
        this.contents = post.getContents();
        this.modifiedAt = String.valueOf(post.getModifiedAt());
    }
}
