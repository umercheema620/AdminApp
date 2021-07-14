package com.example.adminapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.StorageReference;

public class FeaturedPlaceData extends AppCompatActivity {
    TextView name,desc;
    ImageView image;
    String name1,desc1,image1;
    boolean featured;
    Button fea,nfea;
    DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Places");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_featured_place_data);

        name = findViewById(R.id.featurename);
        desc = findViewById(R.id.featuredesc);
        image = findViewById(R.id.featureimage);
        fea = findViewById(R.id.feature_btn);
        nfea = findViewById(R.id.notfeature_btn);

        name1 = getIntent().getStringExtra("name");
        desc1 = getIntent().getStringExtra("des");
        image1 = getIntent().getStringExtra("image");
        featured = getIntent().getBooleanExtra("featured",false);

        name.setText(name1);
        desc.setText(desc1);
        Glide.with(image.getContext()).load(image1).into(image);

        if(!featured){
            nfea.setVisibility(View.GONE);
        }else{
            fea.setVisibility(View.GONE);
        }

    }

    public void GotoFeatureList(View view) {
        startActivity(new Intent(getApplicationContext(),Featured.class));
        finish();
    }

    public void Feature(View view) {
        ref.child(name1).child("featured").setValue(true);
        startActivity(new Intent(getApplicationContext(),Featured.class));
        finish();
    }

    public void NotFeature(View view) {
        ref.child(name1).child("featured").setValue(false);
        startActivity(new Intent(getApplicationContext(),Featured.class));
        finish();
    }
}