package com.example.adminapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;

public class MapShow extends AppCompatActivity implements OnMapReadyCallback {

    MapView map;
    LatLng target;
    double latitude;
    double longitude;
    private double LAT, LON;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_show);

        map = findViewById(R.id.map_view1);
        latitude = getIntent().getDoubleExtra("latitude1",0);
        longitude = getIntent().getDoubleExtra("longitude1",0);

        map.getMapAsync(this);
        map.onCreate(savedInstanceState);
    }

    public void GetLocation(View view) {
        String placename = getIntent().getStringExtra("nameP");
        String placedescription = getIntent().getStringExtra("DescriptionP");
        int come = 1;
        boolean cb0 = getIntent().getBooleanExtra("cb0",true);
        boolean cb1 = getIntent().getBooleanExtra("cb1",true);
        boolean cb2 = getIntent().getBooleanExtra("cb2",true);
        boolean cb3 = getIntent().getBooleanExtra("cb3",true);
        boolean cb4 = getIntent().getBooleanExtra("cb4",true);

        Intent intent = new Intent(getApplicationContext(), AddEvent.class);
        intent.putExtra("latitude", LAT);
        intent.putExtra("longitude", LON);
        intent.putExtra("nameP", placename);
        intent.putExtra("DescriptionP", placedescription);
        intent.putExtra("cb0",cb0);
        intent.putExtra("cb1",cb1);
        intent.putExtra("cb2",cb2);
        intent.putExtra("cb3",cb3);
        intent.putExtra("cb4",cb4);
        intent.putExtra("coming",come);
        startActivity(intent);
        Toast.makeText(this, "Location Added", Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        final LatLng YourLocation = new LatLng(latitude, longitude);
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(YourLocation, 18.0f));

        googleMap.setOnCameraMoveListener(() -> {
            target = googleMap.getCameraPosition().target;
            LAT = target.latitude;
            LON = target.longitude;
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        map.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        map.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        map.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        map.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        map.onDestroy();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState, @NonNull PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        map.onSaveInstanceState(outState);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        map.onLowMemory();
    }

    public void GoBack(View view) {
        String placename = getIntent().getStringExtra("nameP");
        String placedescription = getIntent().getStringExtra("DescriptionP");
        int come = 1;
        boolean cb0 = getIntent().getBooleanExtra("cb0",true);
        boolean cb1 = getIntent().getBooleanExtra("cb1",true);
        boolean cb2 = getIntent().getBooleanExtra("cb2",true);
        boolean cb3 = getIntent().getBooleanExtra("cb3",true);
        boolean cb4 = getIntent().getBooleanExtra("cb4",true);
        Intent intent = new Intent(getApplicationContext(), AddEvent.class);
        intent.putExtra("nameP", placename);
        intent.putExtra("DescriptionP", placedescription);
        intent.putExtra("cb0",cb0);
        intent.putExtra("cb1",cb1);
        intent.putExtra("cb2",cb2);
        intent.putExtra("cb3",cb3);
        intent.putExtra("cb4",cb4);
        intent.putExtra("coming",come);
        startActivity(intent);
        finish();
    }
}