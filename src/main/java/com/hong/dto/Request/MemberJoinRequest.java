package com.hong.dto.Request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberJoinRequest {

    private String userId;

    private String password;

    private String nickName;
}
