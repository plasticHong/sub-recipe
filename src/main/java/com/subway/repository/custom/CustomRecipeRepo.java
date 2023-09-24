package com.subway.repository.custom;

import com.subway.entity.Recipe;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomRecipeRepo {

    public List<Recipe> findRecipe();

}
