package com.hong.dto.response;

import com.hong.dto.response.data.RecipeData;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;

@Getter
@RequiredArgsConstructor
public class RecipeSearchResponse {

    private final Page<RecipeData> recipeDataList;

}
