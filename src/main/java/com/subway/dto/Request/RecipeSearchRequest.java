package com.subway.dto.Request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@RequiredArgsConstructor
@ToString
public class RecipeSearchRequest {

    private final String sortOption;
    private final String sortDirection;

    private final Long sandwichBaseId;
    private final Boolean isWithOutCucumber;

    private final Double maxKcal;
    private final Double minKcal;

    private final Integer maxPrice;
    private final Integer minPrice;

    private final Double maxFat;
    private final Double minFat;

    private final Double maxProtein;
    private final Double minProtein;

}
