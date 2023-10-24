package com.hong.repository.sub;

import com.hong.entity.sub.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecipeRepo extends JpaRepository<Recipe,Long> {
}
