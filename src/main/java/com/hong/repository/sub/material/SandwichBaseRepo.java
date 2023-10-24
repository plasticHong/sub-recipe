package com.hong.repository.sub.material;

import com.hong.entity.sub.SandwichBase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SandwichBaseRepo extends JpaRepository<SandwichBase,Long> {
}
