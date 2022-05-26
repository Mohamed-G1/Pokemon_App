package com.example.pokemonapp.db;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.pokemonapp.model.Pokemon;

import java.util.List;

@Dao
public interface PokemonDao {

    // fun to insert pokeomn
    @Insert
    void insertPokemon(Pokemon pokemon);

    // fun to delete
    @Query("delete  from fav_table where name =:pokemonName")
    // this to delete specific items
    void deletePokemon(String pokemonName);

    // fun to getAll pokemon
    @Query("select * from fav_table")
   public LiveData<List<Pokemon>> getPokemons(); // this live data because we will return a live data
}
