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
import com.example.pokemonapp.databinding.ActivityFavBinding;
import com.example.pokemonapp.model.Pokemon;
import com.example.pokemonapp.viewmodels.PokemonViewModel;

import java.util.ArrayList;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class FavActivity extends AppCompatActivity {

    ActivityFavBinding binding;
    PokemonAdapter adapter;
    PokemonViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_fav);

        adapter = new PokemonAdapter(this);
        binding.favRecycler.setAdapter(adapter);
        swipeItems();


        viewModel = new ViewModelProvider(this).get(PokemonViewModel.class);
        viewModel.getFavPokemon();
        viewModel.getPokeomnFav().observe(this, new Observer<List<Pokemon>>() {
            @Override
            public void onChanged(List<Pokemon> pokemons) {

                // to convert from list to array list
                ArrayList<Pokemon> list = new ArrayList<>();
                list.addAll(pokemons);
                adapter.setList(list);
            }
        });


        binding.goToHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(FavActivity.this, MainActivity.class));
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

                // thirt we will delete it from room database
                viewModel.deletePokemon(swipedPokemon.getName());
                adapter.notifyDataSetChanged();
                Toast.makeText(FavActivity.this, "deleted from database", Toast.LENGTH_SHORT).show();
            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
        // this to bind item touch helper with recycler
        itemTouchHelper.attachToRecyclerView(binding.favRecycler);
    }
}