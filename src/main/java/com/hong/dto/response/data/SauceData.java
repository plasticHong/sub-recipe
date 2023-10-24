package com.hong.dto.response.data;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class SauceData extends RecipeResource{

    private Long id;
    private String name;
    private String taste;
    private String image;
    private Double kcal;

}
