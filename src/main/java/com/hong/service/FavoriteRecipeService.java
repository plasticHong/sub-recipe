package com.hong.service;

import com.hong.entity.sub.MemberFavoriteRecipe;
import com.hong.repository.sub.mapping.MemberFavoriteRecipesRepo;
import com.hong.utils.AuthenticationUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FavoriteRecipeService {

    private final MemberFavoriteRecipesRepo memberFavoriteRecipesRepo;

    @Transactional()
    public Long saveFavoriteRecipe(Long recipeId) {

        Long memberId = AuthenticationUtils.getCurrentMemberId();

        return memberFavoriteRecipesRepo.save(new MemberFavoriteRecipe(recipeId, memberId)).getId();
    }

}
