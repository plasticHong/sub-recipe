package com.subway.dto.response;

import com.subway.dto.response.data.RecipeData;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class RecipeSearchResponse {

    private final List<RecipeData> recipeDataList;

}
