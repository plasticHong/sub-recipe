package com.subway.dto.Request.data;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.List;

@Getter
@RequiredArgsConstructor
@ToString
public class RecipeData {

    private final String title;

    private final Long sandwichBaseId;

    private final String description;

    /*
     * sandwichBase 기본 wheat(위트)
     * */
    private final Long breadId;

    private final Long cheeseId;

    private final boolean isBaked;


    /*
     * 제외 야채
     * */
    private final List<Long> veggieIds;


    /*
     * 소스
     * */
    private final List<Long> sauceIds;


    private final List<Long> extraOptionIds;

    private final List<Long> individualMeatIds;

    private final Integer totalPrice;

    private final Double totalKcal;

    private final Double totalProtein;

    private final Double totalFat;

}
