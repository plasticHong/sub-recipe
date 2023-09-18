package com.subway.dto.data;

import lombok.Getter;

@Getter
public class SandwichBaseData {

    private Long id;

    private String korName;

    private String enName;

    private Integer price;

    private String image;

    private Double kcal;

    private Double protein;

    private Double fat;

    private String defaultDescription;

}
