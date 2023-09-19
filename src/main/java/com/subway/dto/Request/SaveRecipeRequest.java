package com.subway.dto.Request;

import com.subway.dto.Request.data.RecipeData;
import lombok.Getter;
import lombok.RequiredArgsConstructor;


@Getter
@RequiredArgsConstructor
public class SaveRecipeRequest {

    private final Long ownerId;

    private final RecipeData recipeData;

}
