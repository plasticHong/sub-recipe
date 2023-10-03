package com.subway.repository.custom;

import com.querydsl.core.types.OrderSpecifier;
import com.subway.dto.Request.RecipeSearchCondition;
import com.subway.dto.response.data.RecipeData;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomRecipeRepo {

    Page<RecipeData> findRecipe(OrderSpecifier<?> orderCondition, RecipeSearchCondition searchCondition, Pageable pageable);

}
