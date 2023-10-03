package com.subway.dto.Request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class MemberPasswordUpdateRequest {

    private final String originalPassword;

    private final String newPassword;
    private final String newPasswordRepeat;

}