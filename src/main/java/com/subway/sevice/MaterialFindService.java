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

import java.lang.reflect.InvocationTargetException;
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

    public <T> List<T> findAllEntitiesWithSort(JpaRepository<T,?> repository, String sortOption, String sortDirection){

        Sort sort = sortUtils.getSort(sortOption, sortDirection);
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

    public SauceResponse getAllSauces(String sortOption, String sortDirection) {

        List<Sauce> sauceList = findAllEntitiesWithSort(sauceRepo, sortOption, sortDirection);
        List<SauceData> sauceDataList = mappingUtils.entityListToDtoList(sauceList, SauceData.class);

        return new SauceResponse(sauceDataList);
    }

    public VeggieResponse getAllVeggies(String sortOption, String sortDirection) {

        List<Veggie> veggieList = findAllEntitiesWithSort(veggieRepo, sortOption, sortDirection);
        List<VeggieData> veggieDataList = mappingUtils.entityListToDtoList(veggieList, VeggieData.class);

        return new VeggieResponse(veggieDataList);
    }

    public IndividualMeatResponse getAllIndividualMeats(String sortOption, String sortDirection) {

        List<IndividualMeat> IndividualMeatList = findAllEntitiesWithSort(individualMeatRepo, sortOption, sortDirection);
        List<IndividualMeatData> IndividualMeatDataList = mappingUtils.entityListToDtoList(IndividualMeatList, IndividualMeatData.class);

        return new IndividualMeatResponse(IndividualMeatDataList);
    }

    public CheeseResponse getAllCheeses(String sortOption, String sortDirection) {

        List<Cheese> cheeseList = findAllEntitiesWithSort(cheeseRepo, sortOption, sortDirection);
        List<CheeseData> cheeseDataList = mappingUtils.entityListToDtoList(cheeseList, CheeseData.class);

        return new CheeseResponse(cheeseDataList);
    }

    public ExtraOptionResponse getAllExtraOptions(String sortOption, String sortDirection) {

        List<ExtraOption> extraOptionList = findAllEntitiesWithSort(extraOptionRepo, sortOption, sortDirection);
        List<ExtraOptionData> extraOptionDataList = mappingUtils.entityListToDtoList(extraOptionList, ExtraOptionData.class);

        return new ExtraOptionResponse(extraOptionDataList);
    }

    public SandwichBaseResponse getAllSandwichBases(String sortOption, String sortDirection) {

        List<SandwichBase> sandwichBaseList = findAllEntitiesWithSort(sandwichBaseRepo, sortOption, sortDirection);
        List<SandwichBaseData> dtoList = mappingUtils.entityListToDtoList(sandwichBaseList, SandwichBaseData.class);

        return new SandwichBaseResponse(dtoList);
    }


    public BreadResponse getAllBreads(String sortOption, String sortDirection) {

        List<Bread> breadList = findAllEntitiesWithSort(breadRepo, sortOption, sortDirection);
        List<BreadData> breadDataList = mappingUtils.entityListToDtoList(breadList, BreadData.class);

        return new BreadResponse(breadDataList);
    }


}
