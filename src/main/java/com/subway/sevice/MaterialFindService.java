package com.subway.sevice;

import com.subway.dto.response.SandwichBaseDTO;
import com.subway.dto.response.SandwichBaseResponse;
import com.subway.entity.SandwichBase;
import com.subway.repository.materials.BreadRepo;
import com.subway.repository.materials.SandwichBaseRepo;
import com.subway.utils.ObjectMappingUtils;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MaterialFindService {

    private final ObjectMappingUtils mappingUtils;
    private final SandwichBaseRepo sandwichBaseRepo;
    private final BreadRepo breadRepo;

    public SandwichBaseResponse getAllSandwichBase() {

        List<SandwichBase> sandwichBaseList = sandwichBaseRepo.findAll();
        List<SandwichBaseDTO> dtoList = mappingUtils.entityListToDtoList(sandwichBaseList, SandwichBaseDTO.class);

        return new SandwichBaseResponse(dtoList);
    }

}
