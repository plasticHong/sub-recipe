package com.hong.service;

import com.hong.dto.Request.SaveRecipeRequest;
import com.hong.dto.Request.data.RecipeData;
import com.hong.entity.sub.Recipe;
import com.hong.entity.sub.Usable;
import com.hong.entity.sub.mapping.RecipeExtraOption;
import com.hong.entity.sub.mapping.RecipeIndividualMeat;
import com.hong.eum.S3Location;
import com.hong.repository.sub.RecipeRepo;
import com.hong.repository.sub.mapping.RecipeExtraOptionRepo;
import com.hong.repository.sub.mapping.RecipeIndividualMeatRepo;
import com.hong.repository.sub.material.*;
import com.hong.utils.AuthenticationUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class MakeRecipeService {

    private final RecipeRepo recipeRepo;

    private final SandwichBaseRepo sandwichBaseRepo;
    private final BreadRepo breadRepo;
    private final ExtraOptionRepo extraOptionRepo;
    private final CheeseRepo cheeseRepo;
    private final IndividualMeatRepo individualMeatRepo;
    private final VeggieRepo veggieRepo;
    private final SauceRepo sauceRepo;

    private final RecipeExtraOptionRepo recipeExtraOptionRepo;
    private final RecipeIndividualMeatRepo recipeIndividualMeatRepo;
    private final ImageStoreService imageStoreService;

    @Transactional
    public Long saveRecipe(SaveRecipeRequest request, MultipartFile img) {

        Recipe recipeEntity = makeRecipeEntityFromRequest(request);

        String imgPath = imageStoreService.saveImgToS3(img, S3Location.RECIPE);
        recipeEntity.setImg(imgPath);

        Long savedRecipeId = recipeRepo.save(recipeEntity).getId();

        recipeMappingEntityInsert(request, savedRecipeId);
        updateMaterialUsagePoint(request.getRecipeData());

        return savedRecipeId;
    }

    @Transactional
    public void recipeMappingEntityInsert(SaveRecipeRequest request, Long recipeId) {

        List<Long> extraOptionIds = request.getRecipeData().getExtraOptionIds();
        List<Long> individualMeatIds = request.getRecipeData().getIndividualMeatIds();

        if (extraOptionIds != null) {

            extraOptionIds.forEach(id -> {
                RecipeExtraOption entity = new RecipeExtraOption(recipeId, id);
                recipeExtraOptionRepo.save(entity);
            });

        }

        if (individualMeatIds != null){

            individualMeatIds.forEach(id -> {
                RecipeIndividualMeat entity = new RecipeIndividualMeat(recipeId, id);
                recipeIndividualMeatRepo.save(entity);
            });
        }

    }

    public Recipe makeRecipeEntityFromRequest(SaveRecipeRequest request) {

        RecipeData data = request.getRecipeData();
        Long ownerId = AuthenticationUtils.getCurrentMemberId();

        return Recipe.builder()
                .memberId(ownerId)
                .title(data.getTitle())
                .sandwichBaseId(data.getSandwichBaseId())
                .breadId(data.getBreadId())
                .cheeseId(data.getCheeseId())
                .veggieIds(data.getVeggieIds())
                .sauceIds(data.getSauceIds())
                .description(data.getDescription())
                .totalPrice(data.getTotalPrice())
                .totalFat(data.getTotalFat())
                .totalKcal(data.getTotalKcal())
                .totalProtein(data.getTotalProtein())
                .build();
    }

    @Transactional
    public void updateMaterialUsagePoint(RecipeData data) {

        updateUsablePoint(sandwichBaseRepo, data.getSandwichBaseId());
        updateUsablePoint(breadRepo, data.getBreadId());
        updateUsablePoint(cheeseRepo, data.getCheeseId());

        updateUsablePoint(extraOptionRepo, data.getExtraOptionIds());
        updateUsablePoint(individualMeatRepo, data.getIndividualMeatIds());
        updateUsablePoint(veggieRepo, data.getVeggieIds());
        updateUsablePoint(sauceRepo, data.getSauceIds());

    }

    @Transactional
    public <T> void updateUsablePoint(JpaRepository<T, Long> repo, Long id) {

        if(id==null){
            return;
        }

        T entity = repo.findById(id).orElseThrow(NoSuchElementException::new);

        if (entity instanceof Usable usableEntity) {
            usableEntity.usablePointIncrease();
        }

    }

    @Transactional
    public <T> void updateUsablePoint(JpaRepository<T, Long> repo, List<Long> ids) {

        if(ids==null){
            return;
        }

        ids.forEach(id -> {
            T entity = repo.findById(id).orElseThrow(NoSuchElementException::new);

            if (entity instanceof Usable usableEntity) {
                usableEntity.usablePointIncrease();
            }

        });

    }

}
