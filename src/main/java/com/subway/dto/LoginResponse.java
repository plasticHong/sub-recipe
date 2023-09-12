package com.subway.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.java.Log;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse {

    private String grantType;
    private String accessToken;
    private String refreshToken;
    private String message;
    private Long memberId;

    @Builder
    public LoginResponse(String message, Long memberId) {
        this.message = message;
        this.memberId = memberId;
    }

}
