package com.subway.sevice;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.subway.dto.Request.RecipeSearchCondition;
import com.subway.dto.Request.RecipeSearchRequest;
import com.subway.dto.response.RecipeSearchResponse;
import com.subway.dto.response.data.RecipeData;
import com.subway.entity.QRecipe;
import com.subway.repository.custom.CustomRecipeRepo;
import com.subway.utils.AuthenticationUtils;
import com.subway.wrapper.CustomMapper;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.jaxb.SpringDataJaxb;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FindRecipeService {

    private final CustomRecipeRepo customRecipeRepo;
    private final CustomMapper mapper;

    public RecipeSearchResponse findSavedRecipe(Integer pageNum){

        Long currentMemberId = AuthenticationUtils.getCurrentMemberId();
        Page<RecipeData> memberFavoriteRecipe = customRecipeRepo.findMemberFavoriteRecipe(currentMemberId, PageRequest.of(pageNum, 10));

        return new RecipeSearchResponse(memberFavoriteRecipe);
    }

    public RecipeSearchResponse findRecipe(RecipeSearchRequest request){

        OrderSpecifier<?> orderCondition = getOrderSpecifierBySortOption(request.getSortOption(), request.getSortDirection());
        RecipeSearchCondition searchCondition = mapper.map(request, RecipeSearchCondition.class);

        int pageNum = request.getPageNum() != null ? request.getPageNum() : 0;
        int pageSize = request.getPageSize() != null ? request.getPageSize() : 10;
        Page<RecipeData> recipe = customRecipeRepo.findRecipe(orderCondition, searchCondition, PageRequest.of(pageNum,pageSize));

        return new RecipeSearchResponse(recipe);
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
            return new OrderSpecifier<>(orderDirection, QRecipe.recipe.totalPrice);
        }
        if (sortOption.trim().equalsIgnoreCase("respect")) {
            return new OrderSpecifier<>(orderDirection, QRecipe.recipe.respectPoint);
        }
        if (sortOption.trim().equalsIgnoreCase("jmt")) {
            return new OrderSpecifier<>(orderDirection, QRecipe.recipe.jmtPoint);
        }
        if (sortOption.trim().equalsIgnoreCase("new")) {
            return new OrderSpecifier<>(orderDirection, QRecipe.recipe.createTime);
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
