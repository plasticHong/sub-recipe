package com.subway.sevice;

import com.subway.dto.LoginResponse;
import com.subway.dto.Request.LoginRequest;
import com.subway.dto.TokenRefreshResponse;
import com.subway.entity.member.Member;
import com.subway.entity.member.RefreshToken;
import com.subway.repository.MemberRepo;
import com.subway.repository.RefreshTokenRepo;
import com.subway.security.JwtTokenUtil;
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
        String encodedPassword = member.getPassword();

        boolean matches = passwordEncoder.matches(rawPassword, encodedPassword);

        if (matches){
            LoginResponse loginResponse = jwtTokenMaker.makeToken(member.getId(), member.getNickName());
            refreshTokenSave(userId,loginResponse.getRefreshToken());
            return loginResponse;
        }

        return null;
    }

    @Transactional
    public void refreshTokenSave(String userId, String refreshToken) {

        RefreshToken refreshTokenEntity = RefreshToken.builder()
                .refreshToken(refreshToken)
                .userId(userId)
                .build();

        boolean isExists = refreshTokenRepo.existsByUserId(userId);

//               리프레시 토큰 확인, DB에 있으면 삭제 후 save
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

        return createRefreshJson(createdAccessToken);
    }

    public TokenRefreshResponse createRefreshJson(TokenRefreshResponse createdAccessToken){

        if(createdAccessToken == null){
            return null;
        }

        return createdAccessToken;
    }

}
