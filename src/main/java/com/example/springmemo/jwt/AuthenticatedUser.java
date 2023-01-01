package com.example.springmemo.jwt;

import com.example.springmemo.entity.UserRoleEnum;
import lombok.Getter;


@Getter
public class AuthenticatedUser {
    private final UserRoleEnum userRoleEnum;
    private final String username;

    public AuthenticatedUser(UserRoleEnum role, String username) {
        this.userRoleEnum = role;
        this.username = username;
    }
}
