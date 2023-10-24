package com.hong.api.sub;

import com.hong.dto.LoginResponse;
import com.hong.dto.Request.LoginRequest;
import com.hong.dto.Request.NicknameRegistrationRequest;
import com.hong.dto.Request.TokenRefreshRequest;
import com.hong.dto.TokenRefreshResponse;
import com.hong.service.SocialLoginService;
import com.hong.service.UserAuthenticationService;
import com.hong.utils.ResponseUtils;
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
import java.util.HashMap;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/auth")
@Tag(name = "auth", description = "회원")
@RequiredArgsConstructor
public class AuthApi {

    @Value("${kakao.api.key}")
    private String key;

    @Value("${server.domain}")
    private String domain;

    private final SocialLoginService socialLoginService;
    private final UserAuthenticationService userAuthenticationService;

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


    @Operation(summary = "로그인", description = "")
    @RequestMapping(method = RequestMethod.POST, value = "/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {

        HashMap<Object, Object> res = new HashMap<>();

        try {

            LoginResponse token = userAuthenticationService.login(loginRequest);

            if (token == null) {

                res.put("message", "wrong password");
                return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
            }

            return new ResponseEntity<>(token, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            res.put("message", "wrong userId");
            return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
        }

    }

    @Operation(summary = "토큰 리프레시", description = "")
    @RequestMapping(method = RequestMethod.POST, value = "/refresh")
    public ResponseEntity<?> validateRefreshToken(@RequestBody TokenRefreshRequest req) {

        try {

            TokenRefreshResponse token = userAuthenticationService.validateRefreshToken(req.getRefreshToken());

            if (token == null) {
                return new ResponseEntity<>("token is expired", HttpStatus.FORBIDDEN);
            }

            return new ResponseEntity<>(token, HttpStatus.OK);

        } catch (NoSuchElementException e) {

            return new ResponseEntity<>("invalid token", HttpStatus.FORBIDDEN);
        }

    }

}
