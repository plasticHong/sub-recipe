package com.subway.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "jmt_point_records", schema = "sub-recipe")
@NoArgsConstructor
public class JmtPointRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "member_id")
    private Long memberId;

    @Column(name = "recipe_id")
    private Long recipeId;

    @Column(name = "ip_addr")
    private String ipAddr;

    public JmtPointRecord(Long memberId, Long recipeId, String ipAddr) {
        this.memberId = memberId;
        this.recipeId = recipeId;
        this.ipAddr = ipAddr;
    }
}
