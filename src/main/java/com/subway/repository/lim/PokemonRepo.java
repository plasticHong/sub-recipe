package com.subway.repository.lim;

import com.subway.entity.lim.Pokemon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PokemonRepo extends JpaRepository<Pokemon,Integer> {

}
