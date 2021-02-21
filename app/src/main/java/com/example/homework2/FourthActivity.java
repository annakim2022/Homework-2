package com.example.homework2;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class FourthActivity extends AppCompatActivity {

    private TextView textView_name;
    private TextView textView_abv;
    private TextView textView_firstBrewed;
    private TextView textView_description;
    private TextView textView_foodPairings;
    private TextView textView_tips;
    private ImageView imageView_image;

    private static AsyncHttpClient client = new AsyncHttpClient();
    private ArrayList<String> foodPairings;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fourth);
        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        String api_url = "https://api.punkapi.com/v2/beers?beer_name=" + name;
        client.get(api_url, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {

                    JSONArray json = new JSONArray(new String(responseBody));
                    JSONObject object = json.getJSONObject(0);
                    String name = object.getString("name");
                    String abv = object.getString("abv");
                    String firstBrewed = object.getString("first_brewed");
                    String imageUrl = object.getString("image_url");
                    String description = object.getString("description");
                    JSONArray foodPairing = object.getJSONArray("food_pairing");
                    foodPairings = new ArrayList<>();
                    for (int i = 0; i < foodPairing.length(); i++){
                         foodPairings.add(foodPairing.getString(i));
                    }
                    String tips = object.getString("brewers_tips");

                    textView_name = findViewById(R.id.textView_name);
                    textView_name.setText(name);
                    textView_abv = findViewById(R.id.textView_abv);
                    textView_abv.setText("ABV: " + abv + "%");
                    textView_firstBrewed = findViewById(R.id.textView_firstBrewed);
                    textView_firstBrewed.setText("First brewed: " + firstBrewed);
                    textView_description = findViewById(R.id.textView_description);
                    textView_description.setText("Description: " + description);
                    textView_foodPairings = findViewById(R.id.textView_foodPairings);
                    String pairings = "";
                    for (int i =0; i < foodPairings.size(); i++){
                        pairings = pairings + foodPairings.get(i);
                    }
                    textView_foodPairings.setText("Food pairings: " + pairings);
                    textView_tips = findViewById(R.id.textView_tips);
                    textView_tips.setText("Brewer's tips: " + tips);
                    imageView_image = findViewById(R.id.imageView_image);
                    Picasso.get().load(imageUrl).into(imageView_image);



                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });



    }
}
