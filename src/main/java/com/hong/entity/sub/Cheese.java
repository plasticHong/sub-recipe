package com.hong.entity.sub;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
@Table(name = "cheese", schema = "sub-recipe")
public class Cheese implements Usable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "img")
    private String image;

    @Column(name = "kcal")
    private Double kcal;

    @Column(name = "usage_point")
    private Integer usagePoint;

    @Override
    public void usablePointIncrease() {
        this.usagePoint = usagePoint+1;
    }

}
