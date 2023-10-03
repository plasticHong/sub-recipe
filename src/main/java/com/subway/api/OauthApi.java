package com.subway.api;

import com.subway.dto.LoginResponse;
import com.subway.dto.Request.NicknameRegistrationRequest;
import com.subway.sevice.SocialLoginService;
import com.subway.utils.ResponseUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/oauth")
@Tag(name = "oauth", description = "회원")
@RequiredArgsConstructor
public class OauthApi {

    @Value("${kakao.api.key}")
    private String key;

    @Value("${server.domain}")
    private String domain;

    private final SocialLoginService socialLoginService;

    @Operation(summary = "kakaoLogin", description = """
            
            # 카카오 로그인
            
            # response
            - 로그인 성공
                - loginResponse (Object) : accessToken, RefreshToken
            
            - 닉네임 입력대상 (statusCode 303)
                - memberId (Long) : memberId
                
            """)
    @ApiResponse(responseCode = "303", description = "Register your nickname")
    @RequestMapping(method = RequestMethod.GET, value = "/login/kakao")
    public void redirect(HttpServletResponse response) throws IOException {

        String url = "https://kauth.kakao.com/oauth/authorize?client_id=" +
                key +
                "&redirect_uri=" +
                domain +
                "/oauth/login/kakao/code&response_type=code";

        response.sendRedirect(url);
    }

    @Operation(summary = "카카오 redirect 포인트", description = "")
    @RequestMapping(method = RequestMethod.GET, value = "/login/kakao/code")
    public ResponseEntity<?> getCode(@RequestParam String code) {

        LoginResponse loginResponse = socialLoginService.kakaoLogin(code);

        if (loginResponse.getAccessToken() == null){
            Long memberId = loginResponse.getMemberId();
            return new ResponseEntity<>(ResponseUtils.makeJsonFormat("memberId",memberId),HttpStatus.SEE_OTHER);
        }

        return new ResponseEntity<>(loginResponse,HttpStatus.OK);
    }

    @Operation(summary = "카카오 회원 닉네임 등록", description = "")
    @RequestMapping(method = RequestMethod.POST, value = "/registration/nickname")
    public ResponseEntity<?> getCode(@RequestBody NicknameRegistrationRequest request) {

        socialLoginService.nicknameRegistration(request);

        return new ResponseEntity<>(ResponseUtils.makeJsonFormat("message","Please log in again"),HttpStatus.OK);
    }

}
