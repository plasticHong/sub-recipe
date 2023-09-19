package com.subway.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
@Table(name = "individual_meat", schema = "sub-recipe")
public class IndividualMeat implements Usable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "price")
    private Integer price;

    @Column(name = "img")
    private String image;

    @Column(name = "kcal")
    private Double kcal;

    @Column(name = "protein")
    private Double protein;

    @Column(name = "fat")
    private Double fat;

    @Column(name = "usage_point")
    private Integer usagePoint;

    @Override
    public void usablePointIncrease() {
        this.usagePoint = usagePoint+1;
    }

}
