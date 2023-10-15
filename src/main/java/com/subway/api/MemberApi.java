package com.subway.api;

import com.subway.dto.Message;
import com.subway.dto.Request.*;
import com.subway.dto.response.MemberInfo;
import com.subway.service.JoinService;
import com.subway.service.MemberAccountService;
import com.subway.service.MemberInfoService;
import com.subway.utils.ResponseUtils;
import io.jsonwebtoken.JwtException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;


@RestController
@RequestMapping("/member")
@Tag(name = "member", description = "회원")
@RequiredArgsConstructor
public class MemberApi {

    private final JoinService joinService;
    private final MemberInfoService memberInfoService;
    private final MemberAccountService memberAccountService;

    @Operation(summary = "비밀번호 변경(need authentication)", description = """
            ## return :
                -   Original Password validate fail : 403
                -   Password Repeat validate fail : 400
            """)
    @RequestMapping(method = RequestMethod.POST, value = "/update/password")
    public ResponseEntity<?> passwordUpdate(@RequestBody MemberPasswordUpdateRequest request) {

        try {
            memberInfoService.memberPasswordUpdate(request);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(ResponseUtils.makeJsonFormat("message", e.getMessage()), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "회원 탈퇴(need authentication)", description = """
            ## return :
                -   Password validate fail : 403
            """)
    @RequestMapping(method = RequestMethod.POST, value = "/service-out")
    public ResponseEntity<?> serviceOut(@RequestBody MemberOutRequest request) {

        memberAccountService.MemberServiceOut(request.getPassword());

        return new ResponseEntity<>(HttpStatus.OK);
    }


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

        try {
            memberInfo = memberInfoService.resolveAccessToken(authorization);
        } catch (JwtException e) {
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

    @Operation(summary = "닉네임 중복 체크", description = "")
    @RequestMapping(method = RequestMethod.GET, value = "/nickname-check")
    public ResponseEntity<?> nicknameDuplicateCheck(@RequestParam String nickname) {

        boolean isDuplicate = joinService.nicknameDuplicateCheck(nickname);
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

        try {
            joinService.join(joinRequest);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(ResponseUtils.makeJsonFormat("message", e.getMessage()), HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

}
