package com.subway.dto.response.data;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class VeggieData extends RecipeResource{

    private Long id;
    private String name;
    private String image;
    private Double kcal;

}
