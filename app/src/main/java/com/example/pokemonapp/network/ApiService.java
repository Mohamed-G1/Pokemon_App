package com.example.pokemonapp.network;

import com.example.pokemonapp.model.PokemonResponse;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.GET;

public interface ApiService {

    @GET("pokemon")
    Observable<PokemonResponse> getPokemon();
}
