package com.hong.entity.lim;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
@Table(name = "monster", schema = "sub-recipe.pokemon")
public class Pokemon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "monster_num")
    private Integer monsterNum;

    @Column(name = "monster_name")
    private String monsterName;

    @Column(name = "monster_height")
    private Double monsterHeight;

    @Column(name = "monster_weight")
    private Double monsterWeight;

    @Column(name = "monster_image")
    private String monsterImage;

}
