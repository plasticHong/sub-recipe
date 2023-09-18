package com.subway.entity;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;


@Getter
@Entity
@Table(name = "recipe", schema = "sub-recipe")
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "sandwich_base_id")
    private Long sandwichBaseId;

    @Column(name = "description")
    private String description;

    /*
    * sandwichBase 기본 wheat(위트)
    * */
    @Column(name = "bread_id")
    private Long breadId;

    @Column(name = "cheese_id")
    private Long cheeseId;

    /*
    * 제외 야채
    * */
    @Column(name = "exception_veggie_1")
    private Long exceptionVeggie1;

    @Column(name = "exception_veggie_2")
    private Long exceptionVeggie2;

    @Column(name = "exception_veggie_3")
    private Long exceptionVeggie3;

    @Column(name = "exception_veggie_4")
    private Long exceptionVeggie4;

    @Column(name = "exception_veggie_5")
    private Long exceptionVeggie5;

    @Column(name = "exception_veggie_6")
    private Long exceptionVeggie6;

    @Column(name = "exception_veggie_7")
    private Long exceptionVeggie7;

    @Column(name = "exception_veggie_8")
    private Long exceptionVeggie8;

    /*
    * source
    * maximum 3
    * */
    @Column(name = "sauce_1")
    private Long sauceId1;

    @Column(name = "sauce_2")
    private Long sauceId2;

    @Column(name = "sauce_3")
    private Long sauceId3;

    /*
    * 추가 토핑
    * */
    @Column(name = "extra_option_ids")
    private String extraOptionIds;

    /*
     * individual meat(double up or extra meat)
     * 더블업/미트 추가
     * */
    @Column(name = "individual_meat_ids")
    private String individualMeatId;

    @Column(name = "total_price")
    private Integer total_price;

    @Column(name = "total_kcal")
    private Double totalKcal;

    @Column(name = "total_protein")
    private Double totalProtein;

    @Column(name = "total_fat")
    private Double totalFat;

    @Column(name = "star_point")
    private Integer starPoint;

    @Column(name = "respect_point")
    private Integer respectPoint;

    @Column(name = "create_time")
    private LocalDateTime createTime;

    @PrePersist
    public void onPrePersist() {
        this.createTime = LocalDateTime.now();
    }
}
