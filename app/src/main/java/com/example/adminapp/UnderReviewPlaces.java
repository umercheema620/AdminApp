package com.example.adminapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.adminapp.HelpClasses.PlaceHelperClass;
import com.example.adminapp.HelpClasses.ReviewAdaptor;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class UnderReviewPlaces extends AppCompatActivity {

    RecyclerView ReviewList;
    ReviewAdaptor ReviewListAdaptor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_under_review_places);

        ReviewList = findViewById(R.id.list_view);
        ReviewList.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<PlaceHelperClass> options =
                new FirebaseRecyclerOptions.Builder<PlaceHelperClass>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Review").orderByChild("name"),PlaceHelperClass.class)
                        .build();
        ReviewListAdaptor = new ReviewAdaptor(options,this);
        ReviewList.setAdapter(ReviewListAdaptor);
    }

    public void Back(View view) {
        startActivity(new Intent(getApplicationContext(),MainActivity.class));
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();
        ReviewListAdaptor.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        ReviewListAdaptor.stopListening();
    }
}