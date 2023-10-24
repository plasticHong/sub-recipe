package com.hong.repository.sub.material;

import com.hong.entity.sub.Veggie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VeggieRepo extends JpaRepository<Veggie,Long> {
}
