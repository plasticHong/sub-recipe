package com.subway.sevice;

import com.subway.dto.response.MemberInfo;
import com.subway.entity.member.Member;
import com.subway.repository.MemberRepo;
import com.subway.security.JwtTokenUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Map;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class MemberInfoService {

    private final MemberRepo memberRepo;
    private final JwtTokenUtil jwtTokenUtil;

    public String getUserNickName(String userId) {
        Member member = memberRepo.findByUserId(userId).orElseThrow(NoSuchElementException::new);
        return member.getNickName();
    }
    public MemberInfo resolveAccessToken(String bearerToken) {

        MemberInfo memberInfo = jwtTokenUtil.getUserInfoFromToken(bearerToken);

        return memberInfo;
    }


}
