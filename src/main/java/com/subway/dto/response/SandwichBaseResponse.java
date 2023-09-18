package com.subway.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class SandwichBaseResponse {

    private List<SandwichBaseDTO> sandwichBases;

}
