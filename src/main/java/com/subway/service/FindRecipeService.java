package com.subway.service;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.subway.dto.Request.RecipeSearchCondition;
import com.subway.dto.Request.RecipeSearchRequest;
import com.subway.dto.response.RecipeDetailResponse;
import com.subway.dto.response.RecipeSearchResponse;
import com.subway.dto.response.data.*;
import com.subway.entity.QRecipe;
import com.subway.entity.Recipe;
import com.subway.entity.Usable;
import com.subway.repository.MemberRepo;
import com.subway.repository.RecipeRepo;
import com.subway.repository.custom.CustomRecipeRepo;
import com.subway.repository.material.*;
import com.subway.utils.AuthenticationUtils;
import com.subway.utils.SortUtils;
import com.subway.wrapper.CustomMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class FindRecipeService {

    private final CustomRecipeRepo customRecipeRepo;
    private final RecipeRepo recipeRepo;
    private final SandwichBaseRepo sandwichBaseRepo;
    private final BreadRepo breadRepo;
    private final CheeseRepo cheeseRepo;
    private final VeggieRepo veggieRepo;
    private final SauceRepo sauceRepo;
    private final MemberRepo memberRepo;

    private final CustomMapper mapper;

    @Transactional
    public RecipeDetailResponse getRecipeDetails(Long recipeId) {

        Recipe recipe = recipeRepo.findById(recipeId)
                .orElseThrow(() -> new NoSuchElementException("recipeId : {" + recipeId + "}"));

        RecipeDetailResponse recipeDetailResponse = mapper.map(recipe, RecipeDetailResponse.class);

        Long memberId = recipe.getMemberId();
        RecipeOwnerData recipeOwnerData = getMaterialInfo(memberRepo, memberId, RecipeOwnerData.class);
        recipeDetailResponse.setOwnerData(recipeOwnerData);

        Long sandwichBaseId = recipe.getSandwichBaseId();
        SandwichBaseData sandwichBaseData = getMaterialInfo(sandwichBaseRepo, sandwichBaseId, SandwichBaseData.class);
        recipeDetailResponse.setSandwichBaseData(sandwichBaseData);

        Long breadId = recipe.getBreadId();
        BreadData breadData = getMaterialInfo(breadRepo, breadId, BreadData.class);
        recipeDetailResponse.setBreadData(breadData);

        Long cheeseId = recipe.getCheeseId();
        CheeseData cheeseData = getMaterialInfo(cheeseRepo, cheeseId, CheeseData.class);
        recipeDetailResponse.setCheeseData(cheeseData);

        List<Long> veggieIds = recipe.getVeggieIds();
        List<VeggieData> veggieDataList = getMaterialInfoList(veggieRepo, veggieIds, VeggieData.class);
        recipeDetailResponse.setVeggieDataList(veggieDataList);

        List<Long> sauceIds = recipe.getSauceIds();
        List<SauceData> sauceDataList = getMaterialInfoList(sauceRepo, sauceIds, SauceData.class);
        recipeDetailResponse.setSauceDataList(sauceDataList);

        System.out.println("recipeDetailResponse = " + recipeDetailResponse);
        return recipeDetailResponse;
    }

    private <T,U extends RecipeResource> U getMaterialInfo(JpaRepository<T, Long> repo, Long id, Class<U> dtoClass) {

        if(id==null){
            return null;
        }

        T entity = repo.findById(id).orElseThrow(NoSuchElementException::new);

        return mapper.map(entity, dtoClass);
    }

    private <T,U extends RecipeResource> List<U> getMaterialInfoList(JpaRepository<T, Long> repo, List<Long> ids, Class<U> dtoClass) {

        if(ids==null){
            return null;
        }

        List<T> entities = repo.findAllById(ids);

        return mapper.entityListToDtoList(entities, dtoClass);
    }

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
