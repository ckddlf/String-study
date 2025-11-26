package com.example.domain.member.SignUp.dto;

import lombok.Getter;

@Getter
public class SignUpRequest {
    private String email;
    private String password;
    private String nickname;
}