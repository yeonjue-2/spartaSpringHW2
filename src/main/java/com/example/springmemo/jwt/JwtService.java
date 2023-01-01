package com.example.springmemo.jwt;

import com.example.springmemo.entity.UserRoleEnum;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JwtService {
    private final JwtUtil jwtUtil;

    public AuthenticatedUser validateAndGetInfo(String token) {
        if (jwtUtil.validateToken(token)) {
            Claims claims = jwtUtil.getUserInfoFromToken(token);
            String username = claims.getSubject();
            UserRoleEnum role = UserRoleEnum.valueOf(claims.get("auth").toString());
            return new AuthenticatedUser(role, username );
        } else {
            throw new IllegalArgumentException("Token Error");
        }
    }
 }
