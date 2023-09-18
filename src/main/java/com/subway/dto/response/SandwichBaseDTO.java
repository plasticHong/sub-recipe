package com.subway.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SandwichBaseDTO {

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
