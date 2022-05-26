package com.example.pokemonapp.repository;

import androidx.lifecycle.LiveData;

import com.example.pokemonapp.db.PokemonDao;
import com.example.pokemonapp.model.Pokemon;
import com.example.pokemonapp.model.PokemonResponse;
import com.example.pokemonapp.network.ApiService;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Observable;

public class Repository {
    private ApiService apiService;
    private PokemonDao pokemonDao;

    @Inject // to tell dagger if want data from repo get it
    public Repository(ApiService apiService, PokemonDao pokemonDao) {
        this.apiService = apiService;
        this.pokemonDao = pokemonDao;
    }

    public Observable<PokemonResponse> getPokemons() {
        return apiService.getPokemon();
    }

    // this 3 fun that in Dao
    public void insertPokemon(Pokemon pokemon) {
        pokemonDao.insertPokemon(pokemon);
    }

    public void deletePokemon(String pokemonName) {
        pokemonDao.deletePokemon(pokemonName);
    }

    public LiveData<List<Pokemon>> getAllPokemon() {
        return pokemonDao.getPokemons();
    }


}
