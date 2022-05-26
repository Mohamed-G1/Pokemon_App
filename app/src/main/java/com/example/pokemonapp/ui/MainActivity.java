package com.example.pokemonapp.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.pokemonapp.R;
import com.example.pokemonapp.adapter.PokemonAdapter;
import com.example.pokemonapp.databinding.ActivityMainBinding;
import com.example.pokemonapp.model.Pokemon;
import com.example.pokemonapp.viewmodels.PokemonViewModel;

import java.util.ArrayList;

import dagger.hilt.android.AndroidEntryPoint;


@AndroidEntryPoint // علشان dagger تعرف ان ال app هيبدأ من هنا
public class MainActivity extends AppCompatActivity {

    PokemonAdapter adapter;
    PokemonViewModel viewModel;
    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        adapter = new PokemonAdapter(this);
        binding.pokemonRecycler.setAdapter(adapter);
        swipeItems();

        viewModel = new ViewModelProvider(this).get(PokemonViewModel.class);
        viewModel.getPokemon();
        viewModel.getPokemonList().observe(this, new Observer<ArrayList<Pokemon>>() {
            @Override
            public void onChanged(ArrayList<Pokemon> pokemons) {
                adapter.setList(pokemons);
            }
        });


        binding.goToFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, FavActivity.class));
            }
        });
    }




    // swipe item in recycler view and save it in room database
    private void swipeItems() {
        ItemTouchHelper.SimpleCallback callback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

                // first we get swiped item position
                int swipedPokemonPosition = viewHolder.getAdapterPosition();

                // second we get pokemon that in this position
                Pokemon swipedPokemon = adapter.getPokemonPosition(swipedPokemonPosition);

                // thirt we will save it in room database
                viewModel.insertPokemon(swipedPokemon);
                adapter.notifyDataSetChanged();
                Toast.makeText(MainActivity.this, "added in database", Toast.LENGTH_SHORT).show();
            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
        // this to bind item touch helper with recycler
        itemTouchHelper.attachToRecyclerView(binding.pokemonRecycler);
    }
}