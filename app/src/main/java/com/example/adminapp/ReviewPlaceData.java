package com.example.adminapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.adminapp.HelpClasses.PlaceHelperClass;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.net.URI;

public class ReviewPlaceData extends AppCompatActivity {

    TextView name,desc;
    ImageView image;
    String Pname,Pdesc,Pimage,Pcategory,Platitude,Plongitude;

    private DatabaseReference rootNode = FirebaseDatabase.getInstance().getReference("Places");
    private DatabaseReference rootNode2 = FirebaseDatabase.getInstance().getReference("Review");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_place_data);


        name = findViewById(R.id.reviewname);
        desc = findViewById(R.id.reviewdesc);
        image = findViewById(R.id.reviewimage);

        Pname = getIntent().getStringExtra("name");
        Pdesc = getIntent().getStringExtra("des");
        Pimage = getIntent().getStringExtra("image");
        Pcategory = getIntent().getStringExtra("category");
        Platitude = getIntent().getStringExtra("latitude");
        Plongitude = getIntent().getStringExtra("longitude");

        name.setText(Pname);
        desc.setText(Pdesc);
        Glide.with(image.getContext()).load(Pimage).into(image);
    }

    public void GotoReviewList(View view) {
        startActivity(new Intent(getApplicationContext(),UnderReviewPlaces.class));
        finish();
    }

    public void Approve(View view) {
        PlaceHelperClass AddnewPlace = new PlaceHelperClass(Pname,Pdesc,Pimage,Pcategory,Platitude,Plongitude);
        rootNode.child(Pname).setValue(AddnewPlace);

        rootNode2.child(Pname).removeValue();
        Toast.makeText(this, "Place Approved", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(getApplicationContext(),UnderReviewPlaces.class));
        finish();
    }

    public void Delete(View view) {
        rootNode2.child(Pname).removeValue();
        Toast.makeText(this, "Place Deleted", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(getApplicationContext(),UnderReviewPlaces.class));
        finish();
    }
}