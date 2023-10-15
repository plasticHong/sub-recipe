package com.subway.dto.response.data;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class IndividualMeatData {

    private Long id;
    private String name;
    private Integer price;
    private String image;
    private Double kcal;
    private Double protein;
    private Double fat;

}
