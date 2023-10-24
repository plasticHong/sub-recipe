package com.hong.dto.response;

import com.hong.dto.response.data.SandwichBaseData;
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
