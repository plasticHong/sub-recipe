package com.hong.repository.sub.mapping;

import com.hong.entity.sub.mapping.RecipeIndividualMeat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecipeIndividualMeatRepo extends JpaRepository<RecipeIndividualMeat,Long> {
}
