package com.example.adminapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.adminapp.HelpClasses.Approveldata;
import com.example.adminapp.HelpClasses.PlaceHelperClass;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.net.URI;

public class ReviewPlaceData extends AppCompatActivity {

    TextView name,desc,app,dis,vote;
    ImageView image;
    String Pname,Pdesc,Pimage,Pcategory,Platitude,Plongitude,Puser;
    int year,month,day,approve,disapprove,voted;

    private DatabaseReference rootNode = FirebaseDatabase.getInstance().getReference("Places");
    private DatabaseReference rootNode2 = FirebaseDatabase.getInstance().getReference("Review");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_place_data);


        name = findViewById(R.id.reviewname);
        desc = findViewById(R.id.reviewdesc);
        image = findViewById(R.id.reviewimage);
        app= findViewById(R.id.reviewapproved);
        dis = findViewById(R.id.reviewnotappreoved);
        vote = findViewById(R.id.reviewvoted);

        Pname = getIntent().getStringExtra("name");
        Pdesc = getIntent().getStringExtra("des");
        Pimage = getIntent().getStringExtra("image");
        Pcategory = getIntent().getStringExtra("category");
        Platitude = getIntent().getStringExtra("latitude");
        Plongitude = getIntent().getStringExtra("longitude");
        Puser = getIntent().getStringExtra("userid");
        year = getIntent().getIntExtra("year",2021);
        month = getIntent().getIntExtra("month",5);
        day = getIntent().getIntExtra("day",27);

        rootNode2.child(Pname).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Approveldata data = snapshot.getValue(Approveldata.class);
                System.out.println(data);
                if(data != null){
                    approve = data.approve;
                    disapprove = data.disapprove;
                    voted = data.voted;
                    approve = (approve/voted)*100;
                    disapprove = (disapprove/voted)*100;
                    app.setText(Integer.toString(approve));
                    dis.setText(Integer.toString(disapprove));
                    vote.setText(Integer.toString(voted));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("error", error.getMessage());
            }
        });

        name.setText(Pname);
        desc.setText(Pdesc);
        Glide.with(image.getContext()).load(Pimage).into(image);
    }

    public void GotoReviewList(View view) {
        startActivity(new Intent(getApplicationContext(),UnderReviewPlaces.class));
        finish();
    }

    public void Approve(View view) {
        PlaceHelperClass AddnewPlace = new PlaceHelperClass(Pname,Pdesc,Pimage,Pcategory,Platitude,Plongitude,Puser,year,month,day);
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