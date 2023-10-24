package com.hong.repository.sub.material;

import com.hong.entity.sub.Sauce;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SauceRepo extends JpaRepository<Sauce,Long> {
}
