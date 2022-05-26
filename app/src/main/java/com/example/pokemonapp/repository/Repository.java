package com.example.pokemonapp.repository;

import com.example.pokemonapp.model.PokemonResponse;
import com.example.pokemonapp.network.ApiService;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Observable;

public class Repository {
    private ApiService apiService;

    @Inject // to tell dagger if want data from repo get it
    public Repository(ApiService apiService) {
        this.apiService = apiService;
    }

    public Observable<PokemonResponse> getPokemons() {
        return apiService.getPokemon();
    }
}
