package com.subway.sevice;

import com.subway.dto.data.*;
import com.subway.dto.response.BreadResponse;
import com.subway.dto.response.CheeseResponse;
import com.subway.dto.response.ExtraOptionResponse;
import com.subway.dto.response.SandwichBaseResponse;
import com.subway.entity.Bread;
import com.subway.entity.Cheese;
import com.subway.entity.ExtraOption;
import com.subway.entity.SandwichBase;
import com.subway.repository.materials.BreadRepo;
import com.subway.repository.materials.CheeseRepo;
import com.subway.repository.materials.ExtraOptionRepo;
import com.subway.repository.materials.SandwichBaseRepo;
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

    public CheeseResponse getAllCheeses(String sortOption, String sortDirection) {

        Sort sort = sortUtils.getSort(sortOption, sortDirection);

        List<Cheese> cheeseList = findAllEntitiesByRepo(cheeseRepo, sort);
        List<CheeseData> cheeseDataList = mappingUtils.entityListToDtoList(cheeseList, CheeseData.class);

        return new CheeseResponse(cheeseDataList);
    }

    public ExtraOptionResponse getAllExtraOptions(String sortOption, String sortDirection) {

        Sort sort = sortUtils.getSort(sortOption, sortDirection);

        List<ExtraOption> extraOptionList = findAllEntitiesByRepo(extraOptionRepo, sort);
        List<ExtraOptionData> extraOptionDataList = mappingUtils.entityListToDtoList(extraOptionList, ExtraOptionData.class);

        return new ExtraOptionResponse(extraOptionDataList);
    }

    public SandwichBaseResponse getAllSandwichBases(String sortOption, String sortDirection) {

        Sort sort = sortUtils.getSort(sortOption, sortDirection);

        List<SandwichBase> sandwichBaseList = findAllEntitiesByRepo(sandwichBaseRepo, sort);
        List<SandwichBaseData> dtoList = mappingUtils.entityListToDtoList(sandwichBaseList, SandwichBaseData.class);

        return new SandwichBaseResponse(dtoList);
    }


    public BreadResponse getAllBreads(String sortOption, String sortDirection) {

        Sort sort = sortUtils.getSort(sortOption, sortDirection);

        List<Bread> breadList = findAllEntitiesByRepo(breadRepo, sort);
        List<BreadData> breadDataList = mappingUtils.entityListToDtoList(breadList, BreadData.class);

        return new BreadResponse(breadDataList);
    }

    public <T,ID> List<T> findAllEntitiesByRepo(JpaRepository<T,ID> repository, Sort sort){

        List<T> entityList;

        try {
            entityList = repository.findAll(sort);
        }catch (PropertyReferenceException e){
            entityList = repository.findAll();
        }

        return entityList;
    }


}
