package com.subway.dto.Request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class NicknameRegistrationRequest {
    private final Long memberId;
    private final String nickname;
}
