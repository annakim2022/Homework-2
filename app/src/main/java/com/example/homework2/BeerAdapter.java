package com.example.homework2;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.net.InterfaceAddress;
import java.util.ArrayList;
import java.util.List;

public class BeerAdapter extends RecyclerView.Adapter<BeerAdapter.ViewHolder> {

    private List<Beer> beers;
    private List<Beer> favBeers;

    public BeerAdapter (List<Beer>beers) {
        this.beers = beers;
        this.favBeers = new ArrayList<>();
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View beerView = inflater.inflate(R.layout.item_beer, parent, false);
        // return a new ViewHolder
        ViewHolder viewHolder = new ViewHolder(beerView);

        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Beer beer = beers.get(position);

        // set the view based on the data and the view names
        holder.textView_name.setText(beer.getName());
        holder.textView_description.setText(beer.getDescription());

        Picasso.get().load(beer.getImageUrl()).into(holder.imageView_beer);

        if (favBeers.contains(beer)){
            holder.imageView_fav.setImageDrawable(beer.getFav());
        }
        else {
            holder.imageView_fav.setImageDrawable(beer.getUnFav());
        }



    }

    @Override
    public int getItemCount() {
        return beers.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView textView_name;
        TextView textView_description;
        ImageView imageView_beer;
        ImageView imageView_fav;
        Context context;

        public ViewHolder (View itemView) {
            super(itemView);
            textView_name = itemView.findViewById(R.id.textView_name);
            textView_description = itemView.findViewById(R.id.textView_description);
            imageView_beer = itemView.findViewById(R.id.imageView_beer);
            imageView_fav = itemView.findViewById(R.id.imageView_fav);
            context = textView_name.getContext();

            imageView_fav.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    int selected = getAdapterPosition();
                    Beer selectedV = beers.get(selected);
                    if(favBeers.contains(selectedV)){
                        favBeers.remove(selectedV);
                    }else{
                        favBeers.add(selectedV);
                    }
                    notifyDataSetChanged();
                }
            });

            imageView_beer.setOnClickListener(this);
        }

                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, FourthActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("name", textView_name.getText().toString());
                    context.startActivity(intent);

                }

        }

    }



