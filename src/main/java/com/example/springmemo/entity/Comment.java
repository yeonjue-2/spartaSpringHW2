package com.example.springmemo.entity;

import com.example.springmemo.dto.CommentRequest;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment extends Timestamped{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column(nullable = false)
    private String contents;

    @JoinColumn(name = "user_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @JoinColumn(name = "post_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Post post;

    public Comment(CommentRequest requestDto, Post post, User user) {
        this.contents = requestDto.getContents();
        this.user = user;
        this.post = post;

    }

    public void update(CommentRequest requestDto, User user) {
        this.contents = requestDto.getContents();
        this.user = user;
    }

    public boolean isWriter(String username) {
        return this.user.getUsername().equals(username);
    }
}
