package com.example.homework2;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class SecondActivity extends AppCompatActivity {

    private Button button_showResults;
    private static AsyncHttpClient client = new AsyncHttpClient();
    private EditText editText_beer;
    private EditText editText_brewed;
    private EditText editText_to;
    private SwitchCompat switch_highPoint;
    private ArrayList<String> names;
    private ArrayList<String> descriptions;
    private ArrayList<String> imageUrls;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        editText_beer = findViewById(R.id.editText_beer);
        editText_brewed = findViewById(R.id.editText_brewed);
        editText_to = findViewById(R.id.editText_to);
        switch_highPoint = findViewById(R.id.switch_highPoint);

        button_showResults = findViewById(R.id.button_showResults);
        button_showResults.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchNextActivity(v);
            }
        });


    }

    public void launchNextActivity(View v) {
        String beer = editText_beer.getText().toString();
        String brewed = editText_brewed.getText().toString();
        String to = editText_to.getText().toString();
        boolean highPoint = switch_highPoint.isChecked();
        String api_url;
        int toast = 0;


        if (highPoint) {
            api_url = "https://api.punkapi.com/v2/beers?abv_gt=3.9";

        } else {
            api_url = "https://api.punkapi.com/v2/beers?abv_lt=4";

        }
        if (!TextUtils.isEmpty(beer)) {
            api_url = api_url + "&beer_name=" + beer;
        }
        if (!TextUtils.isEmpty(brewed)) {
            api_url = api_url + "&brewed_after=" + brewed;
        }

        if (!TextUtils.isEmpty(to)) {
            api_url = api_url + "&brewed_before=" + to;
        }
        if (!TextUtils.isEmpty(brewed) && !TextUtils.isEmpty(to)) {
            if (brewed.charAt(2) == '/' && to.charAt(2) == '/' && brewed.length() == 7 && to.length() == 7) {
                try {
                    // check dates
                    String year1 = brewed.substring(3);
                    String year2 = to.substring(3);
                    int brewedYear = Integer.parseInt(year1);
                    int toYear = Integer.parseInt(year2);
                    String month1 = brewed.substring(0, 2);
                    String month2 = to.substring(0, 2);
                    int brewedMonth = Integer.parseInt(month1);
                    int toMonth = Integer.parseInt(month2);
                    if (brewedYear > toYear) {
                        toast++;
                    } else if (brewedYear == toYear) {
                        if (brewedMonth > toMonth) {
                            toast++;
                        }
                    }
                } catch (Exception e) {
                    toast++;
                }
            }
            else {
                toast++;
            }
        }



        int finalToast = toast;
        client.get(api_url, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    try {
                        JSONArray json = new JSONArray(new String(responseBody));
                        Intent intent = new Intent(SecondActivity.this, ThirdActivity.class);

                        descriptions = new ArrayList<>();
                        names = new ArrayList<>();
                        imageUrls = new ArrayList<>();


                        for (int i = 0; i < json.length(); i++) {
                            JSONObject object = json.getJSONObject(i);
                            imageUrls.add(object.getString("image_url"));
                            descriptions.add(object.getString("description"));
                            names.add(object.getString("name"));

                        }

                        if (finalToast > 0) {
                            toast();
                        }
                        else {
                            intent.putExtra("imageUrls", imageUrls);
                            intent.putExtra("descriptions", descriptions);
                            intent.putExtra("names", names);
                            startActivity(intent);
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                }
            });

    }

        public void toast () {
            Toast toast = Toast.makeText(this, R.string.toast_message, Toast.LENGTH_SHORT);
            toast.show();
        }


}
