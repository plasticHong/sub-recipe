package com.hong.entity.sub;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "member_saved_recipes", schema = "sub-recipe")
@NoArgsConstructor
public class MemberFavoriteRecipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "recipe_id")
    private Long recipeId;

    @Column(name = "member_id")
    private Long memberId;

    public MemberFavoriteRecipe(Long recipeId, Long memberId) {
        this.recipeId = recipeId;
        this.memberId = memberId;
    }
}
