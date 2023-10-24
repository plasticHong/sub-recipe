package com.hong.repository.sub;

import com.hong.entity.sub.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface MemberRepo extends JpaRepository<Member,Long> {

    Optional<Member> findByUserIdAndUseYnIsTrue(String userId);
    Optional<Member> findByUserId(String userId);
    Optional<Member> findByNickNameAndUseYnIsTrue(String nickname);
    Optional<Member> findByIdAndUseYnIsTrue(Long memberId);
    Optional<Member> findByNickName(String nickname);
    List<Member> findMemberByDeleteTimeBeforeAndUseYnIsFalse(LocalDateTime dateTime);

}
