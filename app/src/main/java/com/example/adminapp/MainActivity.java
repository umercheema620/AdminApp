package com.example.adminapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    DrawerLayout drawerlayout;
    LinearLayout contentView;
    NavigationView navView;
    ImageView menuIcon;
    String value = "0";
    DatabaseReference Places,Users,Review;
    TextView PlaceCount,UserCount,ReviewCount;
    int placecount,usercount,reviewcount;
    static final float END_SCALE = 0.7f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        contentView = findViewById(R.id.content);
        drawerlayout = findViewById(R.id.drawer_layout);
        menuIcon = findViewById(R.id.menu_icon);
        navView = findViewById(R.id.navigation_view);
        Places = FirebaseDatabase.getInstance().getReference("Places");
        Users = FirebaseDatabase.getInstance().getReference("Users");
        Review = FirebaseDatabase.getInstance().getReference("Review");
        PlaceCount = findViewById(R.id.placescount);
        UserCount = findViewById(R.id.usercount);
        ReviewCount = findViewById(R.id.review);


        Places.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    placecount = (int) snapshot.getChildrenCount();
                    PlaceCount.setText(Integer.toString(placecount));
                }else{
                    PlaceCount.setText(0);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        Users.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    usercount = (int) snapshot.getChildrenCount();
                    UserCount.setText(Integer.toString(usercount));
                }else{
                    UserCount.setText(0);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

            Review.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        reviewcount = (int) snapshot.getChildrenCount();
                        ReviewCount.setText(Integer.toString(reviewcount));
                    } else {
                        ReviewCount.setText(value);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });


        navigationDrawer();

    }

    public void Logout(View view) {
        SessionManager session = new SessionManager(MainActivity.this);
        session.LogOut();
        startActivity(new Intent(getApplicationContext(), Login.class));
        finish();
    }

    private void navigationDrawer() {
        //Navigation
        navView.bringToFront();
        navView.setCheckedItem(R.id.nav_user);

        menuIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (drawerlayout.isDrawerVisible(GravityCompat.START))
                    drawerlayout.closeDrawer(GravityCompat.START);
                else drawerlayout.openDrawer(GravityCompat.START);
            }
        });

        animateNavigationDrawer();
    }

    private void animateNavigationDrawer() {

        drawerlayout.addDrawerListener(new DrawerLayout.SimpleDrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {

                final float diffScaledOffset = slideOffset * (1 - END_SCALE);
                final float offsetScale = 1 - diffScaledOffset;
                contentView.setScaleX(offsetScale);
                contentView.setScaleY(offsetScale);

                final float xOffset = drawerView.getWidth() * slideOffset;
                final float xOffsetDiff = contentView.getWidth() * diffScaledOffset / 2;
                final float xTranslation = xOffset - xOffsetDiff;
                contentView.setTranslationX(xTranslation);
            }
        });

    }

    public void CheckReviewList(View view) {
        startActivity(new Intent(getApplicationContext(),UnderReviewPlaces.class));
    }
}