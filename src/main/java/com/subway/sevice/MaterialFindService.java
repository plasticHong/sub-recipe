package com.subway.sevice;

import com.subway.dto.response.SandwichBaseDTO;
import com.subway.dto.response.SandwichBaseResponse;
import com.subway.repository.materials.SandwichBaseRepo;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MaterialFindService {

    private final SandwichBaseRepo sandwichBaseRepo;
    private final ModelMapper modelMapper;

    public SandwichBaseResponse getAllSandwichBase() {
//        entity -> dto
        List<SandwichBaseDTO> SandwichBases = sandwichBaseRepo.findAll().stream()
                .map(entity -> {
                    return modelMapper.map(entity, SandwichBaseDTO.class);
                })
                .toList();

        return new SandwichBaseResponse(SandwichBases);
    }

}
