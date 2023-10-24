package com.hong.dto.response;

import com.hong.dto.response.data.IndividualMeatData;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class IndividualMeatResponse {

    private final List<IndividualMeatData> individualMeatDataList;
}
