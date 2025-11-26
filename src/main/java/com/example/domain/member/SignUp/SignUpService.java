package com.example.domain.member.SignUp;

import com.example.domain.member.Member;
import com.example.domain.member.MemberRepository;
import com.example.domain.member.SignUp.dto.SignUpRequest;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SignUpService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public Long register(SignUpRequest request) {
        // 1. 이메일 중복 체크
        if (memberRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("이미 존재하는 이메일입니다.");
        }

        // 2. 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(request.getPassword());

        // 3. Member 엔티티 생성
        Member member = Member.builder()
                .email(request.getEmail())
                .password(encodedPassword)
                .nickname(request.getNickname())
                .build();

        // 4. DB 저장
        Member saved = memberRepository.save(member);

        return saved.getId(); // 생성된 회원 ID 반환
    }
}