package com.example.adminapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.adminapp.HelpClasses.FeatureAdaptor;
import com.example.adminapp.HelpClasses.PlaceHelperClass;
import com.example.adminapp.HelpClasses.ReviewAdaptor;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class Featured extends AppCompatActivity {
    RecyclerView FeatureList;
    FeatureAdaptor features1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_featured);
        FeatureList = findViewById(R.id.list_view_feature);
        FeatureList.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<PlaceHelperClass> options =
                new FirebaseRecyclerOptions.Builder<PlaceHelperClass>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Places").orderByChild("name"),PlaceHelperClass.class)
                        .build();
        features1 = new FeatureAdaptor(options,this);
        FeatureList.setAdapter(features1);
    }

    public void Back(View view) {
        startActivity(new Intent(getApplicationContext(),MainActivity.class));
        finish();
    }
    @Override
    protected void onStart() {
        super.onStart();
        features1.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        features1.stopListening();
    }
}