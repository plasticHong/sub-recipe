package com.hong.entity.sub.mapping;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "recipe_individual_meat", schema = "sub-recipe")
@NoArgsConstructor
public class RecipeIndividualMeat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "recipe_id")
    private Long recipeId;

    @Column(name = "individual_meat_id")
    private Long individualMeatId;

    public RecipeIndividualMeat(Long recipeId, Long individualMeatId) {
        this.recipeId = recipeId;
        this.individualMeatId = individualMeatId;
    }
}
