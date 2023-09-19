package com.subway.repository.material;

import com.subway.entity.Cheese;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CheeseRepo extends JpaRepository<Cheese,Long> {
}
