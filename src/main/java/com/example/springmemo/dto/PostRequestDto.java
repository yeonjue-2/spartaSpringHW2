package com.example.springmemo.dto;

import lombok.Getter;

@Getter
public class PostRequestDto {
    private String title;
    private String userName;
    private String contents;
    private int password;
}
