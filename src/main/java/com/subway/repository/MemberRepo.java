package com.subway.repository;

import com.subway.entity.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepo extends JpaRepository<Member,Long> {

    Optional<Member> findByUserIdAndUseYnIsTrue(String userId);
    Optional<Member> findByIdAndUseYnIsTrue(Long memberId);

}
