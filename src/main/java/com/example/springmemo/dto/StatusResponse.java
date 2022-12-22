package com.example.springmemo.dto;

import lombok.Getter;

import javax.servlet.http.HttpServletResponse;

@Getter
public class StatusResponse {
    private String msg;
    private int statusCode;

    public StatusResponse(HttpServletResponse response) {
        this.msg = "success";
        this.statusCode = response.getStatus();
    }
}
