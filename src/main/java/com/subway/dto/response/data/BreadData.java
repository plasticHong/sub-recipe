package com.subway.dto.response.data;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class BreadData extends RecipeResource{


    private Long id;

    private String name;

    private String image;

    private Double kcal;

    private Double protein;

    private Double fat;
}
