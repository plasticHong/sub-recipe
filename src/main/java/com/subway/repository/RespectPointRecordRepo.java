package com.subway.repository;

import com.subway.entity.RespectPointRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RespectPointRecordRepo extends JpaRepository<RespectPointRecord, Long> {

}
