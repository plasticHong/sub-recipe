package com.hong.service;

import com.hong.dto.response.MemberRankingResponse;
import com.hong.dto.response.data.MemberWithJmt;
import com.hong.dto.response.data.MemberWithRespect;
import com.hong.entity.sub.member.Member;
import com.hong.repository.sub.custom.CustomMemberRepo;
import com.hong.wrapper.CustomMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@RequiredArgsConstructor
@Service
public class MemberRankingService {

    private final CustomMemberRepo memberRepo;
    private final CustomMapper mapper;

    public MemberRankingResponse getMemberRanking() {

        List<Member> top10MemberByJmtPointDesc = memberRepo.findTop10MemberByJmtPointDesc();
        List<Member> top10MemberByRespectPointDesc = memberRepo.findTop10MemberByRespectPointDesc();

        return MemberRankingResponse.builder()
                .jmt(mapper.entityListToDtoList(top10MemberByJmtPointDesc, MemberWithJmt.class))
                .respect(mapper.entityListToDtoList(top10MemberByRespectPointDesc, MemberWithRespect.class))
                .build();
    }

}
