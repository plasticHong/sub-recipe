package com.subway.repository.custom;

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
    public List<Recipe> findRecipe() {

        QRecipe qRecipe = QRecipe.recipe;

        return queryFactory.select(qRecipe)
                .from(qRecipe)
                .fetch();
    }

}
