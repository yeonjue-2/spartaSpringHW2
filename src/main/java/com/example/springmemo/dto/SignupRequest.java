package com.example.springmemo.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
public class SignupRequest {

    @Pattern(regexp = "^[a-z0-9]*$")
    @Size(min = 4, max = 10, message = "이름은 4 ~ 10자이어야 합니다.")
    private String username;

    @Pattern(regexp = "^[a-zA-Z0-9`~!@#$%^&*()-_=+]*$")
    @Size(min = 8, max = 15, message = "비밀번호는 8 ~ 15자이어야 합니다.")
    private String password;

    private boolean admin = false;
    private String adminToken = "";
}
