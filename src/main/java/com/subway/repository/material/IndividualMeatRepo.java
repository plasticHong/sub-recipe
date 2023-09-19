package com.subway.repository.material;

import com.subway.entity.IndividualMeat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IndividualMeatRepo extends JpaRepository<IndividualMeat,Long> {
}
