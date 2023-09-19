package com.subway.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
@Table(name = "sauce", schema = "sub-recipe")
public class Sauce {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    /*
    * creamy, sweet, hot, other
    * */
    @Column(name = "taste")
    private String taste;

    @Column(name = "img")
    private String image;

    @Column(name = "kcal")
    private Double kcal;

    @Column(name = "usage_point")
    private Integer usagePoint;

}
