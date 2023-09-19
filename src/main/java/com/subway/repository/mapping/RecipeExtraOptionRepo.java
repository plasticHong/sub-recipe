package com.subway.repository.mapping;

import com.subway.entity.mapping.RecipeExtraOption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecipeExtraOptionRepo extends JpaRepository<RecipeExtraOption,Long> {
}
