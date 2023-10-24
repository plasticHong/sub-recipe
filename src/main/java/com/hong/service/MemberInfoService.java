package com.hong.service;

import com.hong.dto.Request.MemberPasswordUpdateRequest;
import com.hong.dto.response.MemberInfo;
import com.hong.entity.sub.member.Member;
import com.hong.exception.CustomAuthException;
import com.hong.repository.sub.MemberRepo;
import com.hong.security.JwtTokenUtil;
import com.hong.utils.AuthenticationUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class MemberInfoService {

    private final MemberRepo memberRepo;
    private final JwtTokenUtil jwtTokenUtil;
    private final UserAuthenticationService authenticationService;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void memberPasswordUpdate(MemberPasswordUpdateRequest req) {

        final Long currentMemberId = AuthenticationUtils.getCurrentMemberId();
        final String originalPassword = req.getOriginalPassword();
        final String newPassword = req.getNewPassword();
        final String newPasswordRepeat = req.getNewPasswordRepeat();

        boolean passwordValidate = authenticationService.memberPasswordValidate(currentMemberId, originalPassword);

        if (!passwordValidate){
            throw new CustomAuthException("check password");
        }
        if(!newPassword.equals(newPasswordRepeat)){
            throw new IllegalArgumentException("check repeat password");
        }

        Member member = memberRepo.findByIdAndUseYnIsTrue(currentMemberId)
                    .orElseThrow(() -> new NoSuchElementException("memberId : " + currentMemberId));

        member.passwordUpdate(passwordEncoder.encode(req.getNewPassword()));
    }

    public String getUserNickName(String userId) {
        Member member = memberRepo.findByUserIdAndUseYnIsTrue(userId).orElseThrow(NoSuchElementException::new);
        return member.getNickName();
    }

    public MemberInfo resolveAccessToken(String bearerToken) {

        MemberInfo memberInfo = jwtTokenUtil.getUserInfoFromToken(bearerToken);

        return memberInfo;
    }


}
