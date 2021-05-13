package com.example.adminapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {

    TextInputLayout email, password;
    Button loginButton;
    ProgressBar progressBar;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = findViewById(R.id.admin_email);
        password = findViewById(R.id.admin_password);
        loginButton = findViewById(R.id.admin_login);
        progressBar = findViewById(R.id.admin_progressBar);
        mAuth = FirebaseAuth.getInstance();

    }

    public void LoginAdmin(View view) {

        Internet object = new Internet();

        if(!object.Connected(this)){
            showCustomDialog();
        }

        if (!ValidateFields()) {
            return;
        }

        String _email = email.getEditText().getText().toString().trim();
        String _password = password.getEditText().getText().toString().trim();

        progressBar.setVisibility(View.VISIBLE);
        loginButton.setVisibility(View.INVISIBLE);

        mAuth.signInWithEmailAndPassword(_email,_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    SessionManager session = new SessionManager(Login.this);
                    session.createLoginSession(FirebaseAuth.getInstance().getCurrentUser().getUid(),_email);
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    finish();

                }else{
                    progressBar.setVisibility(View.GONE);
                    loginButton.setVisibility(View.VISIBLE);
                    Toast.makeText(Login.this, "Data does not exist!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private boolean ValidateFields() {

        String email1 = email.getEditText().getText().toString().trim();
        String password1 = password.getEditText().getText().toString().trim();

        if (email1.isEmpty()) {
            email.setError("phone number cannot be empty");
            return false;
        } else if (password1.isEmpty()) {
            password.setError("Password cannot be empty");
            return false;
        } else {
            email.setError(null);
            email.setErrorEnabled(false);
            password.setError(null);
            password.setErrorEnabled(false);
            return true;
        }
    }

    public void showCustomDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage("Please connect to the internet")
                .setCancelable(false)
                .setPositiveButton("Connect", (dialogInterface, i) -> startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS)))
                .setNegativeButton("Cancel", (dialogInterface, i) -> {
                    startActivity(new Intent(getApplicationContext(), Login.class));
                    finish();
                });
        builder.show();
    }
}