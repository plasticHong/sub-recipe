package com.subway.repository.custom;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.subway.entity.member.Member;
import com.subway.entity.member.QMember;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CustomMemberRepoImpl implements CustomMemberRepo{

    private final JPAQueryFactory queryFactory;

    private final QMember member = QMember.member;

    @Override
    public List<Member> findTop10MemberByJmtPointDesc() {
        return queryFactory.select(member)
                .from(member)
                .where(member.useYn.isTrue())
                .orderBy(member.jmtPoint.desc())
                .limit(10)
                .offset(0)
                .fetch();
    }

    @Override
    public List<Member> findTop10MemberByRespectPointDesc() {
        return queryFactory.select(member)
                .from(member)
                .where(member.useYn.isTrue())
                .orderBy(member.respectPoint.desc())
                .limit(10)
                .offset(0)
                .fetch();
    }
}
