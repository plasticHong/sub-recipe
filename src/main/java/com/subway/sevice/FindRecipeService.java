package com.subway.sevice;

import com.subway.entity.Recipe;
import com.subway.repository.custom.CustomRecipeRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FindRecipeService {

    private final CustomRecipeRepo customRecipeRepo;


    public List<Recipe> findRecipe(){

        List<Recipe> recipe = customRecipeRepo.findRecipe();

        return recipe;
    }

}
