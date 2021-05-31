package com.example.adminapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class PlacesData extends AppCompatActivity {

    TextView total,restaurant,gaming,monument,mosque,park,gym,icecream;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_places_data);

        total = findViewById(R.id.totalplacesdata);
        restaurant = findViewById(R.id.restaurantplaces);
        gaming = findViewById(R.id.gamingplaces);
        monument = findViewById(R.id.monumentplaces);
        mosque = findViewById(R.id.mosqueplaces);
        park = findViewById(R.id.parkplaces);
        gym = findViewById(R.id.gymplaces);
        icecream = findViewById(R.id.icecreamplaces);

        total.setText(getIntent().getStringExtra("total"));
        gaming.setText(getIntent().getStringExtra("gaming"));
        restaurant.setText(getIntent().getStringExtra("restaurant"));
        monument.setText(getIntent().getStringExtra("monument"));
        mosque.setText(getIntent().getStringExtra("mosque"));
        park.setText(getIntent().getStringExtra("park"));
        gym.setText(getIntent().getStringExtra("gym"));
        icecream.setText(getIntent().getStringExtra("icecream"));
    }

    public void GotoMain(View view) {
        startActivity(new Intent(getApplicationContext(),MainActivity.class));
        finish();
    }
}