package com.subway.dto.response;


import com.subway.dto.response.data.MemberWithJmt;
import com.subway.dto.response.data.MemberWithRespect;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class MemberRankingResponse {

    private final List<MemberWithJmt> mostJmtPointsMembers;
    private final List<MemberWithRespect> mostRespectPointsMembers;

    @Builder
    public MemberRankingResponse(List<MemberWithJmt> jmt, List<MemberWithRespect> respect) {
        this.mostJmtPointsMembers = jmt;
        this.mostRespectPointsMembers = respect;
    }

}
