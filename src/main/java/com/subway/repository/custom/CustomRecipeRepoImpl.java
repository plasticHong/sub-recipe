package com.subway.repository.custom;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.subway.entity.QRecipe;
import com.subway.entity.Recipe;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CustomRecipeRepoImpl implements CustomRecipeRepo{

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Recipe> findRecipe(OrderSpecifier<?> orderSpec) {

        QRecipe qRecipe = QRecipe.recipe;

        return queryFactory.select(qRecipe)
                .from(qRecipe)
                .orderBy(orderSpec)
                .fetch();
    }

}
