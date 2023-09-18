package com.subway.dto.response;

import com.subway.dto.data.SandwichBaseData;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
public class SandwichBaseResponse {

    private final List<SandwichBaseData> sandwichBases;

}
