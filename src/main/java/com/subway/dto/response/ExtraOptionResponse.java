package com.subway.dto.response;

import com.subway.dto.data.ExtraOptionData;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class ExtraOptionResponse {

    private final List<ExtraOptionData> extraOptionDataList;

}
