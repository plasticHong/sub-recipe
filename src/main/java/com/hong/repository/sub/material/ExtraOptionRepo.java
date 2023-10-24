package com.hong.repository.sub.material;

import com.hong.entity.sub.ExtraOption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExtraOptionRepo extends JpaRepository<ExtraOption, Long> {
}
