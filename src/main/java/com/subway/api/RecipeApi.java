package com.subway.api;

import com.subway.dto.Request.RecipeSearchRequest;
import com.subway.dto.Request.SaveRecipeRequest;
import com.subway.dto.response.RecipeSearchResponse;
import com.subway.entity.Recipe;
import com.subway.sevice.FavoriteRecipeService;
import com.subway.sevice.FindRecipeService;
import com.subway.sevice.MakeRecipeService;
import com.subway.sevice.RecipeEvaluateService;
import com.subway.utils.ResponseUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/recipe")
@Tag(name = "recipe", description = "")
@RequiredArgsConstructor
public class RecipeApi {

    private final MakeRecipeService makeRecipeService;
    private final FindRecipeService findRecipeService;
    private final FavoriteRecipeService favoriteRecipeService;
    private final RecipeEvaluateService recipeEvaluateService;

    @Operation(summary = "레시피 생성", description = "")
    @RequestMapping(method = RequestMethod.POST, value = "/save")
    public ResponseEntity<?> makeRecipe(@RequestBody SaveRecipeRequest request) {

        Long recipeId = makeRecipeService.saveRecipe(request);

        return new ResponseEntity<>(ResponseUtils.makeJsonFormat("recipeId",recipeId),HttpStatus.OK);
    }

    @Operation(summary = "레시피 검색", description = "")
    @RequestMapping(method = RequestMethod.POST, value = "/search")
    public ResponseEntity<?> getRecipe(@RequestBody RecipeSearchRequest request) {

        RecipeSearchResponse response = findRecipeService.findRecipe(request);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "레시피 즐겨찾기", description = "")
    @RequestMapping(method = RequestMethod.POST, value = "/save/favorite")
    public ResponseEntity<?> saveRecipe(@RequestParam Long recipeId) {

        Long favoriteRecipeId = favoriteRecipeService.saveFavoriteRecipe(recipeId);

        return new ResponseEntity<>(ResponseUtils.makeJsonFormat("savedFavoriteRecipeId",favoriteRecipeId),HttpStatus.OK);
    }

    @Operation(summary = "레시피 평가/jmt", description = "")
    @RequestMapping(method = RequestMethod.POST, value = "/evaluate/jmt")
    public ResponseEntity<?> jmt(@RequestParam Long recipeId) {

        Long jmtRecordId = recipeEvaluateService.jmt(recipeId);

        if (jmtRecordId == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(ResponseUtils.makeJsonFormat("jmtRecordId",jmtRecordId),HttpStatus.OK);
    }

    @Operation(summary = "레시피 평가/respect", description = "")
    @RequestMapping(method = RequestMethod.POST, value = "/evaluate/respect")
    public ResponseEntity<?> respect(@RequestParam Long recipeId) {

        Long respectRecordId = recipeEvaluateService.respect(recipeId);

        if (respectRecordId == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(ResponseUtils.makeJsonFormat("respectRecordId",respectRecordId),HttpStatus.OK);
    }

}
