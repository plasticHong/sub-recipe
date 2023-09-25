package com.subway.sevice;

import com.subway.dto.Request.SaveRecipeRequest;
import com.subway.dto.Request.data.RecipeData;
import com.subway.entity.Recipe;
import com.subway.entity.Usable;
import com.subway.entity.mapping.RecipeExtraOption;
import com.subway.entity.mapping.RecipeIndividualMeat;
import com.subway.repository.RecipeRepo;
import com.subway.repository.mapping.RecipeExtraOptionRepo;
import com.subway.repository.mapping.RecipeIndividualMeatRepo;
import com.subway.repository.material.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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


    @Transactional
    public Long saveRecipe(SaveRecipeRequest request) {

        Recipe recipeEntity = makeRecipeEntity(request);

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

    public Recipe makeRecipeEntity(SaveRecipeRequest request) {

        RecipeData data = request.getRecipeData();
        Long ownerId = request.getOwnerId();

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
