package com.subway.entity.mapping;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "recipe_extra_option", schema = "sub-recipe")
@NoArgsConstructor
public class RecipeExtraOption {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "recipe_id")
    private Long recipeId;

    @Column(name = "extra_option_id")
    private Long extraOptionId;

    public RecipeExtraOption(Long recipeId, Long extraOptionId) {
        this.recipeId = recipeId;
        this.extraOptionId = extraOptionId;
    }

}
