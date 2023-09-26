package com.subway.api;

import com.subway.dto.LoginResponse;
import com.subway.dto.Message;
import com.subway.dto.Request.LoginRequest;
import com.subway.dto.Request.MemberJoinRequest;
import com.subway.dto.Request.TokenRefreshRequest;
import com.subway.dto.TokenRefreshResponse;
import com.subway.dto.response.MemberInfo;
import com.subway.sevice.JoinService;
import com.subway.sevice.UserAuthenticationService;
import com.subway.sevice.MemberInfoService;
import io.jsonwebtoken.JwtException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
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

    @Operation(summary = "내 정보 (need authentication)", description = """
            #parameter - 토큰 첨부 필요 Authorization 헤더, grantType : Bearer
            
            <hr>
            
            #return
            - id(Long) : PK
            - nickName(String) : nickName
            """)
    @RequestMapping(method = RequestMethod.GET, value = "/my-info")
    public ResponseEntity<?> myInfo(HttpServletRequest req) {

        String authorization = req.getHeader("Authorization");
        MemberInfo memberInfo;

        try{
            memberInfo = memberInfoService.resolveAccessToken(authorization);
        }catch (JwtException e){
            return new ResponseEntity<>(new Message(e.getMessage()), HttpStatus.FORBIDDEN);
        }

        return new ResponseEntity<>(memberInfo, HttpStatus.OK);
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
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {

        HashMap<Object, Object> res = new HashMap<>();

        try {

            LoginResponse token = userAuthService.login(loginRequest);

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

            TokenRefreshResponse token = userAuthService.validateRefreshToken(req.getRefreshToken());

            if (token == null) {
                return new ResponseEntity<>("token is expired", HttpStatus.FORBIDDEN);
            }

            return new ResponseEntity<>(token,HttpStatus.OK);

        } catch (NoSuchElementException e) {

            return new ResponseEntity<>("invalid token", HttpStatus.FORBIDDEN);
        }

    }


}
