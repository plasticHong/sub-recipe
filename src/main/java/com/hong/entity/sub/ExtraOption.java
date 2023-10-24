package com.hong.entity.sub;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
@Table(name = "extra_option", schema = "sub-recipe")
public class ExtraOption implements Usable{

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
