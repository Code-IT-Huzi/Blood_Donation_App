package com.example.blood_donation_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.PatternMatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPassword extends AppCompatActivity {

    private EditText Email;
    private Button resetPass, backButton;
    private ProgressBar progressBar;

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        Email = findViewById(R.id.email);
        resetPass = findViewById(R.id.resetPass);
        progressBar = findViewById(R.id.progressBar);
        backButton = findViewById(R.id.backButton);

        auth = FirebaseAuth.getInstance();

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i2 = new Intent(ForgotPassword.this, LoginActivity.class);
                startActivity(i2);
            }
        });

        resetPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetPassword();
            }

            private void resetPassword() {

                String email = Email.getText().toString().trim();

                if(email.isEmpty()){
                    Email.setError("Email is Required");
                    Email.requestFocus();
                    return;
                }

                if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    Email.setError("Please provide a valid email");
                    Email.requestFocus();
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);
                auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(ForgotPassword.this, "Check your Email to reset your Password", Toast.LENGTH_SHORT).show();

                        }else {
                            Toast.makeText(ForgotPassword.this, "Try Again! Something wrong happened!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
}