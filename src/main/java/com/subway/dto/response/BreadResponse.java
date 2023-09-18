package com.subway.dto.response;


import com.subway.dto.data.BreadData;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class BreadResponse {

    private final List<BreadData> breadDataList;

}
