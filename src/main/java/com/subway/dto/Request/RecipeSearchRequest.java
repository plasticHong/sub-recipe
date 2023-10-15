package com.subway.dto.Request;

import lombok.*;

@Getter
@ToString
@NoArgsConstructor
public class RecipeSearchRequest {

    private  String sortOption;
    private  String sortDirection;

    private  Integer pageNum;
    private  Integer pageSize;

    private  Long sandwichBaseId;
    private  Long memberId;
    private  Boolean isWithOutCucumber;

    private  Double maxKcal;
    private  Double minKcal;

    private  Integer maxPrice;
    private  Integer minPrice;

    private  Double maxFat;
    private  Double minFat;

    private  Double maxProtein;
    private  Double minProtein;


    @Builder
    public RecipeSearchRequest(String sortOption, String sortDirection, Integer pageNum, Integer pageSize, Long sandwichBaseId, Long memberId, Boolean isWithOutCucumber, Double maxKcal, Double minKcal, Integer maxPrice, Integer minPrice, Double maxFat, Double minFat, Double maxProtein, Double minProtein) {
        this.sortOption = sortOption;
        this.sortDirection = sortDirection;
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.sandwichBaseId = sandwichBaseId;
        this.memberId = memberId;
        this.isWithOutCucumber = isWithOutCucumber;
        this.maxKcal = maxKcal;
        this.minKcal = minKcal;
        this.maxPrice = maxPrice;
        this.minPrice = minPrice;
        this.maxFat = maxFat;
        this.minFat = minFat;
        this.maxProtein = maxProtein;
        this.minProtein = minProtein;
    }
}
