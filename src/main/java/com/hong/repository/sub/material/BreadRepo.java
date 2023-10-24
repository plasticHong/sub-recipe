package com.hong.repository.sub.material;

import com.hong.entity.sub.Bread;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BreadRepo extends JpaRepository<Bread,Long> {
}
