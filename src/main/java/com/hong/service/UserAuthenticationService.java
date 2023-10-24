package com.hong.service;

import com.hong.dto.LoginResponse;
import com.hong.dto.Request.LoginRequest;
import com.hong.dto.TokenRefreshResponse;
import com.hong.entity.sub.member.Member;
import com.hong.entity.sub.member.RefreshToken;
import com.hong.repository.sub.MemberRepo;
import com.hong.repository.sub.RefreshTokenRepo;
import com.hong.security.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserAuthenticationService {

    private final MemberRepo memberRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenUtil jwtTokenMaker;
    private final RefreshTokenRepo refreshTokenRepo;

    @Transactional
    public LoginResponse login(LoginRequest loginRequest) {

        String userId = loginRequest.getUserId();
        String rawPassword = loginRequest.getPassword();

        Member member = memberRepo.findByUserIdAndUseYnIsTrue(userId).orElseThrow(NoSuchElementException::new);

        boolean matches = memberPasswordValidate(member,rawPassword);

        if (matches){
            LoginResponse loginResponse = jwtTokenMaker.makeToken(member.getId(), member.getNickName());
            refreshTokenSave(userId,loginResponse.getRefreshToken());
            return loginResponse;
        }

        return null;
    }
    public boolean memberPasswordValidate(Long memberId, String rawPassword) {

        Member member = memberRepo.findByIdAndUseYnIsTrue(memberId).orElseThrow(NoSuchElementException::new);
        String encodedPassword = member.getPassword();

        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

    public boolean memberPasswordValidate(Member member, String rawPassword) {

        String encodedPassword = member.getPassword();

        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

    @Transactional
    public void refreshTokenSave(String userId, String refreshToken) {

        RefreshToken refreshTokenEntity = RefreshToken.builder()
                .refreshToken(refreshToken)
                .userId(userId)
                .build();

        boolean isExists = refreshTokenRepo.existsByUserId(userId);

        if (isExists) {
            refreshTokenRepo.deleteByUserId(userId);
        }

        refreshTokenRepo.save(refreshTokenEntity);
    }
    public Optional<RefreshToken> getRefreshToken(String refreshToken){

        return refreshTokenRepo.findByRefreshToken(refreshToken);
    }

    @Transactional
    public TokenRefreshResponse validateRefreshToken(String reqRefreshToken){

        RefreshToken refreshToken = getRefreshToken(reqRefreshToken).orElseThrow(NoSuchElementException::new);
        TokenRefreshResponse createdAccessToken = jwtTokenMaker.validateRefreshToken(refreshToken);

        refreshTokenSave(refreshToken.getUserId(),createdAccessToken.getRefreshToken());

        return createdAccessToken;
    }

}
