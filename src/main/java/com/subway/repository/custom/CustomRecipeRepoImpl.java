package com.subway.repository.custom;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.*;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.subway.dto.Request.RecipeSearchCondition;
import com.subway.entity.QRecipe;
import com.subway.entity.Recipe;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CustomRecipeRepoImpl implements CustomRecipeRepo{

    private final JPAQueryFactory queryFactory;

    private final QRecipe recipe = QRecipe.recipe;

    @Override
    public List<Recipe> findRecipe(OrderSpecifier<?> orderCondition, RecipeSearchCondition searchCondition) {

        return queryFactory.select(recipe)
                .from(recipe)
                .orderBy(orderCondition)
                .where(
                        eqSandwichBaseId(searchCondition.getSandwichBaseId()),
                        withOutCucumber(searchCondition.getIsWithOutCucumber()),

                        loeMaxKcal(searchCondition.getMaxKcal()),
                        goeMinKcal(searchCondition.getMinKcal()),

                        loeMaxFat(searchCondition.getMaxFat()),
                        goeMinFat(searchCondition.getMinFat()),

                        loeMaxProtein(searchCondition.getMaxProtein()),
                        goeMinProtein(searchCondition.getMinProtein()),

                        loeMaxPrice(searchCondition.getMaxPrice()),
                        goeMinPrice(searchCondition.getMinPrice())

                )
                .fetch();
    }

    private BooleanExpression withOutCucumber(Boolean isWithOutCucumber) {
        if (isWithOutCucumber == null || !isWithOutCucumber) return null;

        final Long cucumberId = 3L;
        final Long pickleId = 6L;

        return recipe.veggie1.isNull().or(recipe.veggie1.ne(cucumberId).and(recipe.veggie1.ne(pickleId)))
                .and(
                        recipe.veggie2.isNull().or(recipe.veggie2.ne(cucumberId).and(recipe.veggie2.ne(pickleId)))
                )
                .and(
                        recipe.veggie3.isNull().or(recipe.veggie3.ne(cucumberId).and(recipe.veggie3.ne(pickleId)))
                )
                .and(
                        recipe.veggie4.isNull().or(recipe.veggie4.ne(cucumberId).and(recipe.veggie4.ne(pickleId)))
                )
                .and(
                        recipe.veggie5.isNull().or(recipe.veggie5.ne(cucumberId).and(recipe.veggie5.ne(pickleId)))
                )
                .and(
                        recipe.veggie6.isNull().or(recipe.veggie6.ne(cucumberId).and(recipe.veggie6.ne(pickleId)))
                )
                .and(
                        recipe.veggie7.isNull().or(recipe.veggie7.ne(cucumberId).and(recipe.veggie7.ne(pickleId)))
                )
                .and(
                        recipe.veggie8.isNull().or(recipe.veggie8.ne(cucumberId).and(recipe.veggie8.ne(pickleId)))
                );

    }

    private BooleanExpression eqSandwichBaseId(final Long sandwichBaseId) {
        if (sandwichBaseId == null) return null;
        return recipe.sandwichBaseId.eq(sandwichBaseId);
    }

    private BooleanExpression loeMaxKcal(final Double maxKcal) {
        if (maxKcal == null) return null;
        return recipe.totalKcal.loe(maxKcal);
    }

    private BooleanExpression goeMinKcal(final Double minKcal) {
        if (minKcal == null) return null;
        return recipe.totalKcal.goe(minKcal);
    }

    private BooleanExpression loeMaxFat(final Double maxFat) {
        if (maxFat == null) return null;
        return recipe.totalFat.loe(maxFat);
    }

    private BooleanExpression goeMinFat(final Double minFat) {
        if (minFat == null) return null;
        return recipe.totalFat.goe(minFat);
    }

    private BooleanExpression loeMaxProtein(final Double maxProtein) {
        if (maxProtein == null) return null;
        return recipe.totalProtein.loe(maxProtein);
    }

    private BooleanExpression goeMinProtein(final Double minProtein) {
        if (minProtein == null) return null;
        return recipe.totalProtein.goe(minProtein);
    }

    private BooleanExpression loeMaxPrice(final Integer maxPrice) {
        if (maxPrice == null) return null;
        return recipe.totalPrice.loe(maxPrice);
    }

    private BooleanExpression goeMinPrice(final Integer minPrice) {
        if (minPrice == null) return null;
        return recipe.totalPrice.goe(minPrice);
    }

}
