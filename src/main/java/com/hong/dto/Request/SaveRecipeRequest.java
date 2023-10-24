package com.hong.dto.Request;

import com.hong.dto.Request.data.RecipeData;
import lombok.Getter;
import lombok.RequiredArgsConstructor;


@Getter
@RequiredArgsConstructor
public class SaveRecipeRequest {

    private final Long ownerId;

    private final RecipeData recipeData;

}
