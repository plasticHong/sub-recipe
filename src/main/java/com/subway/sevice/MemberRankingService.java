package com.subway.sevice;

import com.subway.dto.response.MemberRankingResponse;
import com.subway.dto.response.data.MemberWithJmt;
import com.subway.dto.response.data.MemberWithRespect;
import com.subway.entity.member.Member;
import com.subway.repository.custom.CustomMemberRepo;
import com.subway.wrapper.CustomMapper;
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
