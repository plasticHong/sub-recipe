package com.subway.repository.custom;

import com.subway.entity.member.Member;

import java.util.List;

public interface CustomMemberRepo {

    List<Member> findTop10MemberByJmtPointDesc();
    List<Member> findTop10MemberByRespectPointDesc();

}
