package com.hong.service;

import com.hong.entity.sub.member.Member;
import com.hong.exception.CustomAuthException;
import com.hong.repository.sub.MemberRepo;
import com.hong.utils.AuthenticationUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class MemberAccountService {

    private final MemberRepo memberRepo;
    private final UserAuthenticationService authenticationService;

    @Transactional
    public void memberDelete() {

        LocalDateTime mountAgo = LocalDateTime.now().minusMonths(1);

        List<Member> deleteTargetMembers = memberRepo.findMemberByDeleteTimeBeforeAndUseYnIsFalse(mountAgo);

        System.out.println("deleteTargetMembers = " + deleteTargetMembers);

        memberRepo.deleteAll(deleteTargetMembers);
    }

    @Transactional
    public void MemberServiceOut(String password) {

        final Long currentMemberId = AuthenticationUtils.getCurrentMemberId();
        boolean passwordCheck = authenticationService.memberPasswordValidate(currentMemberId, password);

        if (!passwordCheck){
            throw new CustomAuthException("check password");
        }

        Member member = memberRepo.findById(currentMemberId)
                .orElseThrow(() -> new NoSuchElementException("memberId : " + currentMemberId));

        member.softDelete();
    }

}
