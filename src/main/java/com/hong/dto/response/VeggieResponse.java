package com.hong.dto.response;

import com.hong.dto.response.data.VeggieData;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class VeggieResponse {
    private final List<VeggieData> veggieDataList;
}
