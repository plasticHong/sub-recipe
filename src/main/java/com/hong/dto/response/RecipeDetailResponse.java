package com.hong.dto.response;

import com.hong.dto.response.data.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@ToString
public class RecipeDetailResponse {

    private Long id;
    private String title;

    private RecipeOwnerData ownerData;

    private SandwichBaseData sandwichBaseData;
    private BreadData breadData;
    private CheeseData cheeseData;

    private List<VeggieData> veggieDataList;
    private List<SauceData> sauceDataList;

    private Integer totalPrice;
    private Double totalKcal;
    private Double totalFat;
    private Double totalProtein;

    private Integer jmtPoint;
    private Integer respectPoint;

    private LocalDateTime createTime;

}
