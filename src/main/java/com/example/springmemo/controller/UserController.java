package com.example.springmemo.controller;

import com.example.springmemo.dto.LoginRequest;
import com.example.springmemo.dto.SignupRequest;
import com.example.springmemo.dto.StatusResponse;
import com.example.springmemo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/signup")
    public StatusResponse signup(@RequestBody @Valid SignupRequest request, HttpServletResponse response) {
        userService.signup(request);
        return new StatusResponse(response);
    }

    @PostMapping("/login")
    public StatusResponse login(@RequestBody LoginRequest request, HttpServletResponse response) {
        userService.login(request, response);
        return new StatusResponse(response);
    }
}
