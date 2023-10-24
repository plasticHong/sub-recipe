package com.hong.repository.sub.mapping;

import com.hong.entity.sub.MemberFavoriteRecipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberFavoriteRecipesRepo extends JpaRepository<MemberFavoriteRecipe, Long> {
}
