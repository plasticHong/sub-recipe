package com.subway.service.lim;


import com.subway.entity.lim.Pokemon;
import com.subway.repository.lim.PokemonRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PokemonService {

    private final PokemonRepo pokemonRepo;


    public List<Pokemon> getAllPokemon(){

        List<Pokemon> all = pokemonRepo.findAll();

        return all;
    }


}
