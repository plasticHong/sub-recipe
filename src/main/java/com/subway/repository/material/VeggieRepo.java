package com.subway.repository.material;

import com.subway.entity.Veggie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VeggieRepo extends JpaRepository<Veggie,Long> {
}
