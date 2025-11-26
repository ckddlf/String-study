package com.example.domain.member.SignUp;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import com.example.domain.member.SignUp.dto.SignUpRequest;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class SignUpController {

    private final SignUpService memberService;

    @PostMapping("/register")
    public String register(@RequestBody SignUpRequest request) {
        Long memberId = memberService.register(request);
        return "회원가입 성공! 회원 ID = " + memberId;
    }
}