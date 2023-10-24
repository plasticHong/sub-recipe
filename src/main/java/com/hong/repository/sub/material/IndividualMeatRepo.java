package com.hong.repository.sub.material;

import com.hong.entity.sub.IndividualMeat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IndividualMeatRepo extends JpaRepository<IndividualMeat,Long> {
}
