package com.hong.service.lim;


import com.hong.entity.lim.Pokemon;
import com.hong.repository.lim.PokemonRepo;
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
