package com.subway.dto.Request;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberJoinRequest {

    private String userId;

    private String password;

    private String nickName;
}
