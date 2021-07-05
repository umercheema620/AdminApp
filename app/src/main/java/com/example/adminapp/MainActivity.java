package com.example.adminapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerlayout;
    LinearLayout contentView;
    NavigationView navView;
    ImageView menuIcon;
    String value = "0";
    DatabaseReference Places,Users,Review;
    TextView PlaceCount,UserCount,ReviewCount;
    String total,restaurant,gaming,monument,mosque,park,gym,icecream;
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

        Remember2 session = new Remember2(MainActivity.this);
        session.createRememberSession(value);
        Places.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    total = Long.toString(snapshot.getChildrenCount());
                    placecount = (int) snapshot.getChildrenCount();
                    PlaceCount.setText(Integer.toString(placecount));
                }else{
                    PlaceCount.setText(value);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        Places.orderByChild("category").equalTo("Gaming").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                gaming = Long.toString(snapshot.getChildrenCount());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        Places.orderByChild("category").equalTo("Restaurant").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                restaurant = Long.toString(snapshot.getChildrenCount());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        Places.orderByChild("category").equalTo("Gym").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                gym = Long.toString(snapshot.getChildrenCount());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        Places.orderByChild("category").equalTo("Monument").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                monument = Long.toString(snapshot.getChildrenCount());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        Places.orderByChild("category").equalTo("Mosque").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mosque = Long.toString(snapshot.getChildrenCount());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        Places.orderByChild("category").equalTo("Park").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                park = Long.toString(snapshot.getChildrenCount());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        Places.orderByChild("category").equalTo("Ice-cream Parlor").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                icecream = Long.toString(snapshot.getChildrenCount());
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
                    UserCount.setText(value);
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

            Review.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                    String user = FirebaseAuth.getInstance().getCurrentUser().getUid();

                    if(user.equals("5KWQp9j3pOhcqnftlg7UoXe9sfy1")){
                        notification();
                    }
                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                }

                @Override
                public void onChildRemoved(@NonNull DataSnapshot snapshot) {

                }

                @Override
                public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });


        navigationDrawer();

    }

    private void notification() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel("n","n", NotificationManager.IMPORTANCE_DEFAULT);

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this,"n")
                .setContentTitle("Review Place")
                .setSmallIcon(R.drawable.email)
                .setAutoCancel(true)
                .setContentText("New places is Added for Review");

        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(this);
        managerCompat.notify(999,builder.build());

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
        navView.setNavigationItemSelectedListener(this);
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

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        switch (menuItem.getItemId()) {

            case R.id.nav_add_event:
                startActivity(new Intent(getApplicationContext(),AddEvent.class));
                break;

            case R.id.nav_logout:
                SessionManager session = new SessionManager(MainActivity.this);
                session.LogOut();
                startActivity(new Intent(getApplicationContext(), Login.class));
                finish();
                break;
            case R.id.nav_place:
                Intent intent = new Intent(getApplicationContext(),PlacesData.class);
                intent.putExtra("total",total);
                intent.putExtra("gaming",gaming);
                intent.putExtra("restaurant",restaurant);
                intent.putExtra("monument",monument);
                intent.putExtra("mosque",mosque);
                intent.putExtra("park",park);
                intent.putExtra("icecream",icecream);
                intent.putExtra("gym",gym);
                startActivity(intent);
                break;
        }

        return true;
    }
}