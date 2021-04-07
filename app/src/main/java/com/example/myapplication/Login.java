package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {
    public Button mLogin;
    private EditText mEmail,mPassword;
    public ProgressBar mLoading;

    //Step 05
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthStateListener;

    //Step 06
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Step 07
        firebaseAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                //Step 08
                if (user != null){
                    Intent intent = new Intent(getApplication(), MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                    return;
                }
            }
        };

        //Step 02
        mEmail = findViewById(R.id.etUsername);
        mPassword = findViewById(R.id.etPassword);
        mLogin = findViewById(R.id.btnLogin);
        mLoading = findViewById(R.id.LogLoading);
        //Step 11
        mAuth = FirebaseAuth.getInstance();

        //Step 09
        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLoading.setVisibility(View.VISIBLE);
                //Step 10
                final String email = mEmail.getText().toString();
                final  String password = mPassword.getText().toString();
                //Step 12
                mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        //If isn't Successful Login
                        if (!task.isSuccessful()){
                            mLoading.setVisibility(View.GONE);
                            Toast.makeText(Login.this,"Sign in Error",Toast.LENGTH_SHORT).show();
                        }else
                        {
                            Toast.makeText(Login.this,"Sign in Complete",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }

    //Step 03
    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(firebaseAuthStateListener);
    }
    //Step 04
    @Override
    protected void onStop() {
        super.onStop();
        mAuth.removeAuthStateListener(firebaseAuthStateListener);
    }
}