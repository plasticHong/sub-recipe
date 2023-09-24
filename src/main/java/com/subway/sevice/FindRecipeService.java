package com.subway.sevice;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.subway.entity.QRecipe;
import com.subway.entity.Recipe;
import com.subway.repository.custom.CustomRecipeRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FindRecipeService {

    private final CustomRecipeRepo customRecipeRepo;


    public List<Recipe> findRecipe(String sortOption, String sortDirection){

        OrderSpecifier<?> orderSpec = getOrderSpecifierBySortOption(sortOption, sortDirection);
        List<Recipe> recipe = customRecipeRepo.findRecipe(orderSpec);

        return recipe;
    }

    private OrderSpecifier<?> getOrderSpecifierBySortOption(String sortOption,String sortDirection) {

        Order orderDirection = getOrderDirection(sortDirection);

        if (sortOption==null){
            return new OrderSpecifier<>(orderDirection, QRecipe.recipe.id);
        }

        if (sortOption.trim().equalsIgnoreCase("kcal")) {
            return new OrderSpecifier<>(orderDirection, QRecipe.recipe.totalKcal);
        }
        if (sortOption.trim().equalsIgnoreCase("protein")) {
            return new OrderSpecifier<>(orderDirection, QRecipe.recipe.totalProtein);
        }
        if (sortOption.trim().equalsIgnoreCase("fat")) {
            return new OrderSpecifier<>(orderDirection, QRecipe.recipe.totalFat);
        }
        if (sortOption.trim().equalsIgnoreCase("price")) {
            return new OrderSpecifier<>(orderDirection, QRecipe.recipe.total_price);
        }
        if (sortOption.trim().equalsIgnoreCase("respect")) {
            return new OrderSpecifier<>(orderDirection, QRecipe.recipe.respectPoint);
        }
        if (sortOption.trim().equalsIgnoreCase("star")) {
            return new OrderSpecifier<>(orderDirection, QRecipe.recipe.starPoint);
        }

        return new OrderSpecifier<>(orderDirection, QRecipe.recipe.id);
    }

    private Order getOrderDirection(String sortDirection) {
        if(sortDirection==null){
            return Order.ASC;
        }
        return sortDirection.trim().equalsIgnoreCase("DESC")? Order.DESC:Order.ASC;
    }

}
