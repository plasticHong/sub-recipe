package com.subway.sevice;

import com.subway.dto.data.*;
import com.subway.dto.response.*;
import com.subway.entity.*;
import com.subway.repository.SauceRepo;
import com.subway.repository.material.*;
import com.subway.utils.ObjectMappingUtils;
import com.subway.utils.SortUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MaterialFindService {

    private final ObjectMappingUtils mappingUtils;
    private final SortUtils sortUtils;

    private final SandwichBaseRepo sandwichBaseRepo;
    private final BreadRepo breadRepo;
    private final ExtraOptionRepo extraOptionRepo;
    private final CheeseRepo cheeseRepo;
    private final IndividualMeatRepo individualMeatRepo;
    private final VeggieRepo veggieRepo;
    private final SauceRepo sauceRepo;

    public SauceResponse getAllSauces(String sortOption, String sortDirection) {

        Sort sort = sortUtils.getSort(sortOption, sortDirection);

        List<Sauce> sauceList = findAllEntitiesWithSort(sauceRepo, sort);
        List<SauceData> sauceDataList = mappingUtils.entityListToDtoList(sauceList, SauceData.class);

        return new SauceResponse(sauceDataList);
    }

    public VeggieResponse getAllVeggies(String sortOption, String sortDirection) {

        Sort sort = sortUtils.getSort(sortOption, sortDirection);

        List<Veggie> veggieList = findAllEntitiesWithSort(veggieRepo, sort);
        List<VeggieData> veggieDataList = mappingUtils.entityListToDtoList(veggieList, VeggieData.class);

        return new VeggieResponse(veggieDataList);
    }

    public IndividualMeatResponse getAllIndividualMeats(String sortOption, String sortDirection) {

        Sort sort = sortUtils.getSort(sortOption, sortDirection);

        List<IndividualMeat> IndividualMeatList = findAllEntitiesWithSort(individualMeatRepo, sort);
        List<IndividualMeatData> IndividualMeatDataList = mappingUtils.entityListToDtoList(IndividualMeatList, IndividualMeatData.class);

        return new IndividualMeatResponse(IndividualMeatDataList);
    }

    public CheeseResponse getAllCheeses(String sortOption, String sortDirection) {

        Sort sort = sortUtils.getSort(sortOption, sortDirection);

        List<Cheese> cheeseList = findAllEntitiesWithSort(cheeseRepo, sort);
        List<CheeseData> cheeseDataList = mappingUtils.entityListToDtoList(cheeseList, CheeseData.class);

        return new CheeseResponse(cheeseDataList);
    }

    public ExtraOptionResponse getAllExtraOptions(String sortOption, String sortDirection) {

        Sort sort = sortUtils.getSort(sortOption, sortDirection);

        List<ExtraOption> extraOptionList = findAllEntitiesWithSort(extraOptionRepo, sort);
        List<ExtraOptionData> extraOptionDataList = mappingUtils.entityListToDtoList(extraOptionList, ExtraOptionData.class);

        return new ExtraOptionResponse(extraOptionDataList);
    }

    public SandwichBaseResponse getAllSandwichBases(String sortOption, String sortDirection) {

        Sort sort = sortUtils.getSort(sortOption, sortDirection);

        List<SandwichBase> sandwichBaseList = findAllEntitiesWithSort(sandwichBaseRepo, sort);
        List<SandwichBaseData> dtoList = mappingUtils.entityListToDtoList(sandwichBaseList, SandwichBaseData.class);

        return new SandwichBaseResponse(dtoList);
    }


    public BreadResponse getAllBreads(String sortOption, String sortDirection) {

        Sort sort = sortUtils.getSort(sortOption, sortDirection);

        List<Bread> breadList = findAllEntitiesWithSort(breadRepo, sort);
        List<BreadData> breadDataList = mappingUtils.entityListToDtoList(breadList, BreadData.class);

        return new BreadResponse(breadDataList);
    }

    public <T,ID> List<T> findAllEntitiesWithSort(JpaRepository<T,ID> repository, Sort sort){

        List<T> entityList;

        if(sort == null){
            return repository.findAll();
        }

        try {
            entityList = repository.findAll(sort);
        }catch (PropertyReferenceException e){
            entityList = repository.findAll();
        }

        return entityList;
    }


}
