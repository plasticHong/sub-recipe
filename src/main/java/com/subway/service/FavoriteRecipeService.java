package com.subway.service;

import com.subway.entity.MemberFavoriteRecipe;
import com.subway.repository.mapping.MemberFavoriteRecipesRepo;
import com.subway.utils.AuthenticationUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FavoriteRecipeService {

    private final MemberFavoriteRecipesRepo memberFavoriteRecipesRepo;

    @Transactional
    public Long saveFavoriteRecipe(Long recipeId) {

        Long memberId = AuthenticationUtils.getCurrentMemberId();

        return memberFavoriteRecipesRepo.save(new MemberFavoriteRecipe(recipeId, memberId)).getId();
    }

}
