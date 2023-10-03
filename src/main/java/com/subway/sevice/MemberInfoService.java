package com.subway.sevice;

import com.subway.dto.response.MemberInfo;
import com.subway.entity.member.Member;
import com.subway.repository.MemberRepo;
import com.subway.security.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class MemberInfoService {

    private final MemberRepo memberRepo;
    private final JwtTokenUtil jwtTokenUtil;

    public String getUserNickName(String userId) {
        Member member = memberRepo.findByUserIdAndUseYnIsTrue(userId).orElseThrow(NoSuchElementException::new);
        return member.getNickName();
    }
    public MemberInfo resolveAccessToken(String bearerToken) {

        MemberInfo memberInfo = jwtTokenUtil.getUserInfoFromToken(bearerToken);

        return memberInfo;
    }


}
