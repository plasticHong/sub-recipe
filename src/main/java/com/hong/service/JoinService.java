package com.hong.service;

import com.hong.dto.Request.MemberJoinRequest;
import com.hong.entity.sub.member.Member;
import com.hong.repository.sub.MemberRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class JoinService {

    private final MemberRepo memberRepo;
    private final PasswordEncoder passwordEncoder;

    public boolean userIdDuplicateCheck(String userId) {
        return memberRepo.findByUserId(userId).isPresent();
    }

    public boolean nicknameDuplicateCheck(String nickname) {
        return memberRepo.findByNickName(nickname).isPresent();
    }

    @Transactional
    public void join(MemberJoinRequest request) {

        String rawPassword = request.getPassword();
        String encodedPassword = passwordEncoder.encode(rawPassword);

        String userId = request.getUserId();

        boolean userIdPresent = userIdDuplicateCheck(userId);
        boolean nicknamePresent = nicknameDuplicateCheck(request.getNickName());

        if (userIdPresent){
            throw new IllegalArgumentException("already exist userId");
        }
        if (nicknamePresent){
            throw new IllegalArgumentException("already exist nickname");
        }

        Member member = Member.builder()
                .userId(userId)
                .nickName(request.getNickName())
                .password(encodedPassword)
                .build();

        memberRepo.save(member);

    }

}
