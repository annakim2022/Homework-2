package com.example.homework2;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Objects;

public class ThirdActivity extends AppCompatActivity {

    private TextView textView_count;
    private RecyclerView recyclerView;
    private ArrayList<Beer> beers;
    private ArrayList<Beer> displayedBeers;
    private SearchView filter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);
        recyclerView = findViewById(R.id.recyclerView_beer);
        beers = new ArrayList<>();
        displayedBeers = new ArrayList<>();
        Intent intent = getIntent();

        ArrayList<String> names = intent.getStringArrayListExtra("names");
        ArrayList<String> descriptions = intent.getStringArrayListExtra("descriptions");
        ArrayList<String> imageUrls = intent.getStringArrayListExtra("imageUrls");

        Drawable fav;
        Drawable unFav;
        try {
            InputStream ims = getAssets().open("fav.png");
            Drawable d = Drawable.createFromStream(ims, null);
            ims.close();
            fav = d;
        }catch (IOException ex) {
            return;
        }
        try {
            InputStream ims = getAssets().open("unFav.png");
            Drawable d = Drawable.createFromStream(ims, null);
            ims.close();
            unFav = d;
        }catch (IOException ex) {
            return;
        }

        for (int i = 0; i < names.size(); i++) {

            Beer beer = new Beer(names.get(i),
                    descriptions.get(i),
                    imageUrls.get(i),
                    fav, unFav);

            beers.add(beer);
            displayedBeers.add(beer);
        }

        int count = descriptions.size();
        textView_count = findViewById(R.id.textView_count);
        textView_count.setText(count + " results found");

        // extra credit
        filter = findViewById(R.id.searchView_filter);
        filter.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                return true;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                if (!newText.isEmpty()){
                    // clear recycler view
                    newText = newText.toLowerCase();
                    displayedBeers.clear();
                    for (int i = 0; i < beers.size(); i++){
                        if (beers.get(i).getName().toLowerCase().contains(newText)){
                            // display in recycler view
                            displayedBeers.add(beers.get(i));
                        }
                        Objects.requireNonNull(recyclerView.getAdapter()).notifyDataSetChanged();
                    }
                    int count = displayedBeers.size();
                    textView_count = findViewById(R.id.textView_count);
                    textView_count.setText(count + " results found");
                }
                else {
                    // clear recycler view
                    // add all to recycler view
                    displayedBeers.clear();
                    displayedBeers.addAll(beers);
                    int count = displayedBeers.size();
                    textView_count = findViewById(R.id.textView_count);
                    textView_count.setText(count + " results found");
                    Objects.requireNonNull(recyclerView.getAdapter()).notifyDataSetChanged();
                }
                return true;
            }
        });




    BeerAdapter adapter = new BeerAdapter(displayedBeers);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));



    }
}
