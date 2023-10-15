package com.subway.api;

import com.subway.dto.Request.RecipeSearchRequest;
import com.subway.dto.Request.SaveRecipeRequest;
import com.subway.dto.response.RecipeDetailResponse;
import com.subway.dto.response.RecipeSearchResponse;
import com.subway.service.FavoriteRecipeService;
import com.subway.service.FindRecipeService;
import com.subway.service.MakeRecipeService;
import com.subway.service.RecipeEvaluateService;
import com.subway.utils.ResponseUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


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
    public ResponseEntity<?> makeRecipe(@RequestPart SaveRecipeRequest request, @RequestPart MultipartFile file) {

        Long recipeId = makeRecipeService.saveRecipe(request,file);

        return new ResponseEntity<>(ResponseUtils.makeJsonFormat("recipeId",recipeId),HttpStatus.OK);
    }

    @Operation(summary = "레시피 검색", description = """
            ## parameter
                pageNum(int) : 첫 번째 페이지 0 부터 시작
                pageSize(int) : 페이지 사이즈
                sortOption(String)(ignoreCase) : kcal, protein, price, respect, jmt, new
                sortDirection(String)(ignoreCase) : ASC, DESC
            """)
    @RequestMapping(method = RequestMethod.GET, value = "/search")
    public ResponseEntity<?> getRecipe(@RequestParam(required = false) String sortOption,
                                       @RequestParam(required = false) String sortDirection,
                                       @RequestParam(required = false) Integer pageNum,
                                       @RequestParam(required = false) Integer pageSize,
                                       @RequestParam(required = false) Long sandwichBaseId,
                                       @RequestParam(required = false) Boolean isWithOutCucumber,
                                       @RequestParam(required = false) Double maxKcal,
                                       @RequestParam(required = false) Double minKcal,
                                       @RequestParam(required = false) Integer maxPrice,
                                       @RequestParam(required = false) Integer minPrice,
                                       @RequestParam(required = false) Double maxFat,
                                       @RequestParam(required = false) Double minFat,
                                       @RequestParam(required = false) Double maxProtein,
                                       @RequestParam(required = false)  Double minProtein) {

        RecipeSearchRequest request = RecipeSearchRequest.builder()
                .sortDirection(sortDirection)
                .sortOption(sortOption)
                .pageNum(pageNum)
                .pageSize(pageSize)
                .sandwichBaseId(sandwichBaseId)
                .isWithOutCucumber(isWithOutCucumber)
                .maxKcal(maxKcal)
                .minKcal(minKcal)
                .maxPrice(maxPrice)
                .minPrice(minPrice)
                .maxFat(maxFat)
                .minFat(minFat)
                .maxProtein(maxProtein)
                .minProtein(minProtein)
                .build();

        System.out.println("request = " + request);

        RecipeSearchResponse response = findRecipeService.findRecipe(request);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "레시피 상세 조회", description = """
            ## parameter
               
            """)
    @RequestMapping(method = RequestMethod.GET, value = "/{recipeId}")
    public ResponseEntity<?> getRecipeDetail(@PathVariable Long recipeId) {

//        TODO write
        RecipeDetailResponse recipeDetails = findRecipeService.getRecipeDetails(recipeId);

        return new ResponseEntity<>(recipeDetails,HttpStatus.OK);
    }
    
    @Operation(summary = "자신의 즐겨찾기 레시피 가져오기", description = """
            ## parameter
                pageNum(int) : 첫 번째 페이지 0 부터 시작
               
            """)
    @RequestMapping(method = RequestMethod.GET, value = "/favorite")
    public ResponseEntity<?> getSavedRecipe(@RequestParam Integer pageNum) {

        RecipeSearchResponse savedRecipe = findRecipeService.findSavedRecipe(pageNum);

        return new ResponseEntity<>(savedRecipe,HttpStatus.OK);
    }

    @Operation(summary = "레시피 즐겨찾기", description = "")
    @RequestMapping(method = RequestMethod.POST, value = "/save/favorite/{recipeId}")
    public ResponseEntity<?> saveRecipe(@PathVariable("recipeId") Long recipeId) {

        Long favoriteRecipeId = favoriteRecipeService.saveFavoriteRecipe(recipeId);

        return new ResponseEntity<>(ResponseUtils.makeJsonFormat("savedFavoriteRecipeId",favoriteRecipeId),HttpStatus.OK);
    }

    @Operation(summary = "레시피 평가/jmt", description = "")
    @RequestMapping(method = RequestMethod.POST, value = "/evaluate/jmt/{recipeId}")
    public ResponseEntity<?> jmt(@PathVariable("recipeId") Long recipeId) {

        Long jmtRecordId = recipeEvaluateService.jmt(recipeId);

        if (jmtRecordId == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(ResponseUtils.makeJsonFormat("jmtRecordId",jmtRecordId),HttpStatus.OK);
    }

    @Operation(summary = "레시피 평가/respect", description = "")
    @RequestMapping(method = RequestMethod.POST, value = "/evaluate/respect/{recipeId}")
    public ResponseEntity<?> respect(@PathVariable("recipeId") Long recipeId) {

        Long respectRecordId = recipeEvaluateService.respect(recipeId);

        if (respectRecordId == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(ResponseUtils.makeJsonFormat("respectRecordId",respectRecordId),HttpStatus.OK);
    }

}
