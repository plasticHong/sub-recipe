package com.hong.dto.response;

import com.hong.dto.response.data.ExtraOptionData;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class ExtraOptionResponse {

    private final List<ExtraOptionData> extraOptionDataList;

}
