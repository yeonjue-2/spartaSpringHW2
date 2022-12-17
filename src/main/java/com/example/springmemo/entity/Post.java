package com.example.springmemo.entity;

import com.example.springmemo.dto.PostRequestDto;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post extends Timestamped{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String userName;

    @Column(nullable = false)
    private String contents;

    @Column(nullable = false)
    private int password;

    public Post(PostRequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.userName = requestDto.getUserName();
        this.contents = requestDto.getContents();
        this.password = requestDto.getPassword();
    }

    public void update(Long id, PostRequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.userName = requestDto.getUserName();
        this.contents = requestDto.getContents();
    }

    public boolean isValidPasswaord(int password) {
        if (this.password == password) {
            return true;
        }
        return false;
    }
}
