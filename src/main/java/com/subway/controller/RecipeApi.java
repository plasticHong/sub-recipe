package com.subway.controller;

import com.subway.dto.Request.SaveRecipeRequest;
import com.subway.sevice.MakeRecipeService;
import com.subway.utils.ResponseUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/recipe")
@Tag(name = "recipe", description = "")
@RequiredArgsConstructor
public class RecipeApi {

    private final MakeRecipeService recipeService;

    @Operation(summary = "레시피 생성", description = "")
    @RequestMapping(method = RequestMethod.POST, value = "/recipe")
    public ResponseEntity<?> makeRecipe(@RequestBody SaveRecipeRequest request) {

        Long recipeId = recipeService.saveRecipe(request);

        return new ResponseEntity<>(ResponseUtils.makeJsonFormat("recipeId",recipeId),HttpStatus.OK);
    }


}
