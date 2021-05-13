package com.example.adminapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class Intro extends AppCompatActivity {

    private static int SPLASH = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        SessionManager session = new SessionManager(Intro.this);

        if(!session.checkLogin()){
            new Handler().postDelayed(()->{
                Intent mainIntent = new Intent(getApplicationContext(),Login.class);
                startActivity(mainIntent);
                finish();
            },SPLASH);
        }else{
            new Handler().postDelayed(()->{
                Intent mainIntent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(mainIntent);
                finish();
            },SPLASH);
        }


    }
}