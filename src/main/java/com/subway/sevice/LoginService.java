package com.subway.sevice;

import com.subway.dto.LoginResponse;
import com.subway.dto.Request.LoginRequest;
import com.subway.entity.member.Member;
import com.subway.repository.MemberRepo;
import com.subway.security.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final MemberRepo memberRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenUtil jwtTokenMaker;

    public LoginResponse login(LoginRequest loginRequest) {

        String userId = loginRequest.getUserId();
        String rawPassword = loginRequest.getPassword();

        Member member = memberRepo.findByUserId(userId).orElseThrow(NoSuchElementException::new);
        String encodedPassword = member.getPassword();

        boolean matches = passwordEncoder.matches(rawPassword, encodedPassword);
        if (matches){
            return jwtTokenMaker.makeToken(member.getId(), member.getNickName());
        }

        return null;
    }



}
