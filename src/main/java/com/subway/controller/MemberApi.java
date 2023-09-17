package com.subway.controller;

import com.subway.dto.LoginResponse;
import com.subway.dto.Request.LoginRequest;
import com.subway.dto.Request.MemberJoinRequest;
import com.subway.dto.Request.TokenRefreshRequest;
import com.subway.dto.TokenRefreshResponse;
import com.subway.sevice.JoinService;
import com.subway.sevice.UserAuthenticationService;
import com.subway.sevice.MemberInfoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.NoSuchElementException;


@RestController
@RequestMapping("/member")
@Tag(name = "member", description = "회원")
@RequiredArgsConstructor
public class MemberApi {

    private final UserAuthenticationService userAuthService;
    private final JoinService joinService;
    private final MemberInfoService memberInfoService;

    @Operation(summary = "내 닉네임 (need authentication)", description = """
            # - 임시
            # - 토큰 첨부 필요 Authorization 헤더, grantType : Bearer
            """)
    @RequestMapping(method = RequestMethod.GET, value = "/nickname/{userId}")
    public ResponseEntity<?> myNickName(@PathVariable("userId") String userId) {

        String nickName = memberInfoService.getUserNickName(userId);

        return new ResponseEntity<>(nickName, HttpStatus.OK);
    }

    @Operation(summary = "아이디 중복 체크", description = "")
    @RequestMapping(method = RequestMethod.GET, value = "/id-check")
    public ResponseEntity<?> userIdDuplicateCheck(@RequestParam String userId) {

        boolean isDuplicate = joinService.userIdDuplicateCheck(userId);
        HashMap<Object, Boolean> res = new HashMap<>();

        if (isDuplicate) {
            res.put("duplicate", true);
            return new ResponseEntity<>(res, HttpStatus.OK);
        }

        res.put("duplicate", false);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @Operation(summary = "회원가입", description = "")
    @RequestMapping(method = RequestMethod.POST, value = "/join")
    public ResponseEntity<?> join(@RequestBody MemberJoinRequest joinRequest) {

        joinService.join(joinRequest);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "로그인", description = "")
    @RequestMapping(method = RequestMethod.POST, value = "/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest, HttpServletResponse servletResponse) {

        HashMap<Object, Object> res = new HashMap<>();

        try {

            LoginResponse token = userAuthService.login(loginRequest);

            if (token == null) {

                res.put("message", "wrong password");
                return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
            }

//            addCookie(servletResponse, "accessToken", token.getAccessToken());
//            addCookie(servletResponse, "refreshToken", token.getRefreshToken());

            return new ResponseEntity<>(token, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            res.put("message", "wrong userId");
            return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
        }

    }

    @RequestMapping(method = RequestMethod.POST, value = "/refresh")
    public ResponseEntity<?> validateRefreshToken(@RequestBody TokenRefreshRequest req, HttpServletResponse servletResponse) {

        try {

            TokenRefreshResponse token = userAuthService.validateRefreshToken(req.getRefreshToken());

            if (token == null) {
                return new ResponseEntity<>("token is expired", HttpStatus.FORBIDDEN);
            }

            addCookie(servletResponse, "accessToken", token.getAccessToken());
            addCookie(servletResponse, "refreshToken", token.getRefreshToken());


            return new ResponseEntity<>(HttpStatus.OK);

        } catch (NoSuchElementException e) {

            return new ResponseEntity<>("invalid token", HttpStatus.FORBIDDEN);
        }

    }

    public static void addCookie(HttpServletResponse response, String name, String value) {
        ResponseCookie cookie = ResponseCookie.from(name, value)
                .path("/")
                .httpOnly(false)
                .sameSite("None")
                .secure(true)
                .maxAge(60 * 60 * 24 * 7)
                .build();

        response.addHeader("Set-Cookie", cookie.toString());
    }


}
