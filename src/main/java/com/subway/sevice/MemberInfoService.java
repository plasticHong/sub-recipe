package com.subway.sevice;

import com.subway.entity.member.Member;
import com.subway.repository.MemberRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class MemberInfoService {

    private final MemberRepo memberRepo;

    public String getUserNickName(String userId) {
        Member member = memberRepo.findByUserId(userId).orElseThrow(NoSuchElementException::new);
        return member.getNickName();
    }

}
