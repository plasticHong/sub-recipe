package com.subway.repository;

import com.subway.entity.StarPointRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StarPointRecordRepo extends JpaRepository<StarPointRecord,Long> {
}
