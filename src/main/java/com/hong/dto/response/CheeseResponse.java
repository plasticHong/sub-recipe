package com.hong.dto.response;

import com.hong.dto.response.data.CheeseData;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class CheeseResponse {
    private final List<CheeseData> cheeseDataList;
}
