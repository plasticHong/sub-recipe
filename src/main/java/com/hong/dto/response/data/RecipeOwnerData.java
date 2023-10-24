package com.hong.dto.response.data;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class RecipeOwnerData extends RecipeResource{
    private Long memberId;
    private String nickname;
}
