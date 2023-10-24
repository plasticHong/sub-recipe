package com.hong.repository.sub.mapping;

import com.hong.entity.sub.mapping.RecipeExtraOption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecipeExtraOptionRepo extends JpaRepository<RecipeExtraOption,Long> {
}
