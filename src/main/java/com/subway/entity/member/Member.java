package com.subway.entity.member;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Table(name = "member", schema = "sub-recipe")
@AllArgsConstructor
@NoArgsConstructor
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "password")
    private String password;

    @Column(name = "nickname")
    private String nickName;

    @Column(name = "star_point")
    private Integer starPoint;

    @Column(name = "respect_point")
    private Integer respectPoint;

    @Builder
    public Member(String userId, String password, String nickName) {
        this.userId = userId;
        this.password = password;
        this.nickName = nickName;
    }

    @PrePersist
    public void onPrePersist() {
        this.starPoint = 0;
        this.respectPoint = 0;
    }

}
