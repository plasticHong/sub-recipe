package com.hong.dto.response;

import com.hong.dto.response.data.SauceData;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class SauceResponse {
    private final List<SauceData> sauceDataList;
}
