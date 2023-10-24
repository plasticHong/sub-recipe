package com.hong.dto.response.data;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class SandwichBaseData extends RecipeResource{

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
