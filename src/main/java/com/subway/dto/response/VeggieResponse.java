package com.subway.dto.response;

import com.subway.dto.response.data.VeggieData;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class VeggieResponse {
    private final List<VeggieData> veggieDataList;
}
