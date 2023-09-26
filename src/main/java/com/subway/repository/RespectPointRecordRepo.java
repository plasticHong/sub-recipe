package com.subway.repository;

import com.subway.entity.RespectPointRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RespectPointRecordRepo extends JpaRepository<RespectPointRecord, Long> {

    Optional<RespectPointRecord> findByMemberIdAndRecipeId(Long memberId, Long recipeId);
    Optional<RespectPointRecord> findByRecipeIdAndIpAddr(Long recipeId,String ipAddr);


}
