package com.example.pokemonapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.pokemonapp.R;
import com.example.pokemonapp.model.Pokemon;

import java.util.ArrayList;

public class PokemonAdapter extends RecyclerView.Adapter<PokemonAdapter.ViewHolder> {

    ArrayList<Pokemon> mList = new ArrayList<>();
    private Context mContext;

    public PokemonAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.pokemon_items, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.pokemonText.setText(mList.get(position).getName());

        Glide.with(mContext).load(mList.get(position).getUrl()).into(holder.pokemonImage);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void setList(ArrayList<Pokemon> mList) {
        this.mList = mList;
        notifyDataSetChanged();
    }

    // this fun to get position of pokemon
    public Pokemon getPokemonPosition(int position) {
        return mList.get(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView pokemonImage;
        TextView pokemonText;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            pokemonImage = itemView.findViewById(R.id.pokemon_image);
            pokemonText = itemView.findViewById(R.id.pokemon_text);
        }
    }
}
