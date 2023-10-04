package com.subway.dto.response.data;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class RecipeData {

    private Long id;

    private String title;


    private Long memberId;
    private String ownerNickname;

    private Long sandwichBaseId;
    private String sandwichBaseName;

    private String sandwichBaseImagePath;

    private Integer totalPrice;
    private Double totalKcal;
    private Double totalProtein;
    private Double totalFat;
    private Integer jmtPoint;
    private Integer respectPoint;
    private LocalDateTime createTime;

}
