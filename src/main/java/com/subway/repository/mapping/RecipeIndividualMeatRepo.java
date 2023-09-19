package com.subway.repository.mapping;

import com.subway.entity.mapping.RecipeIndividualMeat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecipeIndividualMeatRepo extends JpaRepository<RecipeIndividualMeat,Long> {
}
