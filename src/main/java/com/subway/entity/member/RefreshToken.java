package com.subway.entity.member;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Entity
@Getter
@Table(name = "REFRESH_TOKEN", schema = "sub-recipe")
@NoArgsConstructor
@AllArgsConstructor
public class RefreshToken {

    @Id
    @Column(name = "KEY_EMAIL", nullable = false)
    private String keyEmail;

    @Column(name = "REFRESH_TOKEN", nullable = false)
    private String refreshToken;
}
