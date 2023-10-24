package com.hong.repository.sub.custom;

import com.hong.entity.sub.member.Member;

import java.util.List;

public interface CustomMemberRepo {

    List<Member> findTop10MemberByJmtPointDesc();
    List<Member> findTop10MemberByRespectPointDesc();

}
