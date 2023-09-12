package com.subway.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
@Table(name = "bread", schema = "sub-recipe")
public class Bread {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "img")
    private String image;

    @Column(name = "kcal")
    private Double kcal;

}
