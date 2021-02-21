package com.example.homework2;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import java.io.IOException;
import java.io.InputStream;


public class MainActivity extends AppCompatActivity {

    private Button button_getStarted;
    private ImageView imageView_background;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView_background = findViewById(R.id.imageView_background);

        AssetManager assetManager = getAssets();
        try {
            InputStream ims = assetManager.open("squirrel.jpeg");
            Drawable d = Drawable.createFromStream(ims, null);
            imageView_background.setImageDrawable(d);
        } catch (IOException ex) {
            return;
        }

        button_getStarted = findViewById(R.id.button_getStarted);
        button_getStarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchNextActivity(v);
            }
        });

    }

    public void launchNextActivity(View v) {
        Intent intent = new Intent(this, SecondActivity.class);
        startActivity(intent);

    }
}