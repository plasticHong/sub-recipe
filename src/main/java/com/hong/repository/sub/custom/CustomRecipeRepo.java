package com.hong.repository.sub.custom;

import com.querydsl.core.types.OrderSpecifier;
import com.hong.dto.Request.RecipeSearchCondition;
import com.hong.dto.response.data.RecipeData;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomRecipeRepo {

    Page<RecipeData> findRecipe(OrderSpecifier<?> orderCondition, RecipeSearchCondition searchCondition, Pageable pageable);
    Page<RecipeData> findMemberFavoriteRecipe(Long memberId,Pageable pageable);

}
