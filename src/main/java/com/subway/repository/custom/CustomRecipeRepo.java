package com.subway.repository.custom;

import com.querydsl.core.types.OrderSpecifier;
import com.subway.dto.Request.RecipeSearchCondition;
import com.subway.dto.response.data.RecipeData;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomRecipeRepo {

//    public List<Recipe> findRecipe(OrderSpecifier<?> orderCondition, RecipeSearchCondition searchCondition);
    public List<RecipeData> findRecipe(OrderSpecifier<?> orderCondition, RecipeSearchCondition searchCondition);

}
