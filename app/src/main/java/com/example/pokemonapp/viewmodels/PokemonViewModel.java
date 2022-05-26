package com.example.pokemonapp.viewmodels;

import android.annotation.SuppressLint;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pokemonapp.model.Pokemon;
import com.example.pokemonapp.model.PokemonResponse;
import com.example.pokemonapp.repository.Repository;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class PokemonViewModel extends ViewModel {
    private static final String TAG = "PokemonViewModel";

    private Repository repository;

    MutableLiveData<ArrayList<Pokemon>> pokemonList = new MutableLiveData<>();


    // this live data to shown list in fav screen
    LiveData<List<Pokemon>> pokeomnFav = null;

    public LiveData<List<Pokemon>> getPokeomnFav() {
        return pokeomnFav;
    }

    @ViewModelInject // this from dagger hilt
    public PokemonViewModel(Repository repository) {
        this.repository = repository;
    }

    public MutableLiveData<ArrayList<Pokemon>> getPokemonList() {
        return pokemonList;
    }

    @SuppressLint("CheckResult")
    public void getPokemon() {
        repository.getPokemons()
                .subscribeOn(Schedulers.io())

                // هنا هياخد response و يرحعلي arraylist>
                // واستخدمنا map علشان هعدل علي ال  url اول ما يوصل لان ل database فيها مشكلة

                .map(new Function<PokemonResponse, ArrayList<Pokemon>>() {
                    @Override
                    public ArrayList<Pokemon> apply(PokemonResponse pokemonResponse) throws Throwable {
                        // هقولة يرجع ال result اللي عبارة عن array list
                        ArrayList<Pokemon> list = pokemonResponse.getResults();

                        // و هعمل loop علشان تلف علي كل ال list وتعدل عليهم
                        for (Pokemon pokemon : list) {

                            // هفولة اول حاجة هات ال url القديم
                            String url = pokemon.getUrl();
                            // علشان اقسم بيها ال url القديم واجيب الرقم الاخير
                            String[] pokemonIndex = url.split("/");

                            // هنا علشان يضيف بس اخر حاجة موجودة في ال url اللي هي الرقم
                            pokemon.setUrl("https://pokeres.bastionbot.org/images/pokemon"
                                    + pokemonIndex[pokemonIndex.length - 1] + ".png");
                        }
                        return list;
                    }

                }).observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> pokemonList.setValue(result), error -> Log.d(TAG, "viewModel: " + error));

        {


        }
    }


    public void insertPokemon(Pokemon pokemon) {
        repository.insertPokemon(pokemon);
    }

    public void deletePokemon(String pokemonName) {
        repository.deletePokemon(pokemonName);
    }

    public void getFavPokemon() {
        pokeomnFav = repository.getAllPokemon();
    }


}
