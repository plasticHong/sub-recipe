package com.subway.dto.response;

import com.subway.dto.data.IndividualMeatData;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class IndividualMeatResponse {

    private final List<IndividualMeatData> individualMeatDataList;
}
