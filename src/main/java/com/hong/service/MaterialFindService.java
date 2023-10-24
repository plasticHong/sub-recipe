package com.hong.service;

import com.hong.dto.response.*;
import com.hong.dto.response.data.*;
import com.hong.entity.sub.*;
import com.hong.repository.sub.material.*;
import com.hong.utils.SortUtils;
import com.hong.wrapper.CustomMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MaterialFindService {

    private final CustomMapper mapper;

    private final SandwichBaseRepo sandwichBaseRepo;
    private final BreadRepo breadRepo;
    private final ExtraOptionRepo extraOptionRepo;
    private final CheeseRepo cheeseRepo;
    private final IndividualMeatRepo individualMeatRepo;
    private final VeggieRepo veggieRepo;
    private final SauceRepo sauceRepo;

    public <T> List<T> findAllEntitiesWithSort(JpaRepository<T,?> repository, String sortOption, String sortDirection){

        Sort sort = SortUtils.getSort(sortOption, sortDirection);
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
        List<SauceData> sauceDataList = mapper.entityListToDtoList(sauceList, SauceData.class);

        return new SauceResponse(sauceDataList);
    }

    public VeggieResponse getAllVeggies(String sortOption, String sortDirection) {

        List<Veggie> veggieList = findAllEntitiesWithSort(veggieRepo, sortOption, sortDirection);
        List<VeggieData> veggieDataList = mapper.entityListToDtoList(veggieList, VeggieData.class);

        return new VeggieResponse(veggieDataList);
    }

    public IndividualMeatResponse getAllIndividualMeats(String sortOption, String sortDirection) {

        List<IndividualMeat> IndividualMeatList = findAllEntitiesWithSort(individualMeatRepo, sortOption, sortDirection);
        List<IndividualMeatData> IndividualMeatDataList = mapper.entityListToDtoList(IndividualMeatList, IndividualMeatData.class);

        return new IndividualMeatResponse(IndividualMeatDataList);
    }

    public CheeseResponse getAllCheeses(String sortOption, String sortDirection) {

        List<Cheese> cheeseList = findAllEntitiesWithSort(cheeseRepo, sortOption, sortDirection);
        List<CheeseData> cheeseDataList = mapper.entityListToDtoList(cheeseList, CheeseData.class);

        return new CheeseResponse(cheeseDataList);
    }

    public ExtraOptionResponse getAllExtraOptions(String sortOption, String sortDirection) {

        List<ExtraOption> extraOptionList = findAllEntitiesWithSort(extraOptionRepo, sortOption, sortDirection);
        List<ExtraOptionData> extraOptionDataList = mapper.entityListToDtoList(extraOptionList, ExtraOptionData.class);

        return new ExtraOptionResponse(extraOptionDataList);
    }

    public SandwichBaseResponse getAllSandwichBases(String sortOption, String sortDirection) {

        List<SandwichBase> sandwichBaseList = findAllEntitiesWithSort(sandwichBaseRepo, sortOption, sortDirection);
        List<SandwichBaseData> dtoList = mapper.entityListToDtoList(sandwichBaseList, SandwichBaseData.class);

        return new SandwichBaseResponse(dtoList);
    }


    public BreadResponse getAllBreads(String sortOption, String sortDirection) {

        List<Bread> breadList = findAllEntitiesWithSort(breadRepo, sortOption, sortDirection);
        List<BreadData> breadDataList = mapper.entityListToDtoList(breadList, BreadData.class);

        return new BreadResponse(breadDataList);
    }


}
