package com.subway.repository;

import com.subway.entity.JmtPointRecord;
import com.subway.entity.RespectPointRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JmtPointRecordRepo extends JpaRepository<JmtPointRecord, Long> {

    Optional<JmtPointRecord> findByMemberIdAndRecipeId(Long memberId, Long recipeId);

    Optional<JmtPointRecord> findByRecipeIdAndIpAddr(Long recipeId, String ipAddr);
}
