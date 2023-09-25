package com.subway.repository.mapping;

import com.subway.entity.MemberFavoriteRecipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberFavoriteRecipesRepo extends JpaRepository<MemberFavoriteRecipe, Long> {
}
