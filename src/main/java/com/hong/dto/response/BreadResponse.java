package com.hong.dto.response;


import com.hong.dto.response.data.BreadData;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class BreadResponse {

    private final List<BreadData> breadDataList;

}
