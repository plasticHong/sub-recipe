package com.hong.repository.lim;

import com.hong.entity.lim.Pokemon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PokemonRepo extends JpaRepository<Pokemon,Integer> {

}
