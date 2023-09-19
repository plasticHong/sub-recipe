package com.subway.repository.material;

import com.subway.entity.Bread;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BreadRepo extends JpaRepository<Bread,Long> {
}
