package com.example.pokemonapp.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import com.example.pokemonapp.R;
import com.example.pokemonapp.databinding.ActivityFavBinding;

public class FavActivity extends AppCompatActivity {

    ActivityFavBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_fav);
    }
}