package com.subway.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
@Table(name = "sandwich_base", schema = "sub-recipe")
public class SandwichBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "k_name")
    private String KorName;

    @Column(name = "e_name")
    private String EnName;

    @Column(name = "price")
    private Integer price;

    @Column(name = "img")
    private String image;

    /*
    * 기본 빵 + 야채 213.2 kcal
    */
    @Column(name = "kcal")
    private Double kcal;

    @Column(name = "protein")
    private Double protein;

    @Column(name = "fat")
    private Double fat;

    @Column(name = "default_description")
    private String defaultDescription;

}
