package com.subway.repository;

import com.subway.entity.Sauce;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SauceRepo extends JpaRepository<Sauce,Long> {

}