package com.subway.api;

import com.subway.entity.lim.Pokemon;
import com.subway.service.lim.PokemonService;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/pokemon")
@Tag(name = "pokemon", description = "")
@Hidden
@RequiredArgsConstructor
public class PokemonApi {

    private final PokemonService pokemonService;

    @Operation(summary = "", description = "")
    @RequestMapping(method = RequestMethod.GET, value = "/all")
    public ResponseEntity<?> getAllPokemon() {

        List<Pokemon> allPokemon = pokemonService.getAllPokemon();

        return new ResponseEntity<>(allPokemon,HttpStatus.OK);
    }

}
