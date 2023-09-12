package com.subway.controller;

import com.subway.dto.LoginResponse;
import com.subway.dto.Request.LoginRequest;
import com.subway.dto.Request.MemberJoinRequest;
import com.subway.sevice.JoinService;
import com.subway.sevice.LoginService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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

    private final LoginService loginService;
    private final JoinService joinService;

    @Operation(summary = "아이디 중복 체크", description = "")
    @RequestMapping(method = RequestMethod.GET, value = "/id-check")
    public ResponseEntity<?> userIdDuplicateCheck(@RequestParam String userId) {

        boolean isDuplicate = joinService.userIdDuplicateCheck(userId);
        HashMap<Object, Boolean> res = new HashMap<>();

        if (isDuplicate){
            res.put("duplicate", true);
            return new ResponseEntity<>(res,HttpStatus.OK);
        }

        res.put("duplicate", false);
        return new ResponseEntity<>(res,HttpStatus.OK);
    }

    @Operation(summary = "회원가입", description = "")
    @RequestMapping(method = RequestMethod.POST, value = "/join")
    public ResponseEntity<?> join(@RequestBody MemberJoinRequest joinRequest) {

        System.out.println("join");
        joinService.join(joinRequest);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "로그인", description = "")
    @RequestMapping(method = RequestMethod.POST, value = "/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {

        HashMap<Object, Object> res = new HashMap<>();

        try {

            LoginResponse token = loginService.login(loginRequest);

            if (token == null){

                res.put("message", "wrong password");
                return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
            }

            return new ResponseEntity<>(token, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            res.put("message", "wrong userId");
            return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
        }

    }



}
