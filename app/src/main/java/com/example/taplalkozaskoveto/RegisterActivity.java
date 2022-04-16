package com.example.taplalkozaskoveto;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {
    private static final String LOG_TAG = RegisterActivity.class.getName();
    private static final int SECRET_KEY = 66;
    private EditText username;
    private EditText password;
    private EditText passwordConfirm;
    private EditText email;

    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Bundle bundle=getIntent().getExtras();
        int secret_key=bundle.getInt("SECRET_KEY");
        if(secret_key!=SECRET_KEY){
            finish();
        }
        username = findViewById(R.id.editTextUsername);
        password = findViewById(R.id.editTextPassword);
        email = findViewById(R.id.editTextEmail);
        passwordConfirm = findViewById(R.id.editTextPasswordAgain);
        mAuth = FirebaseAuth.getInstance();
    }

    public void register(View view) {
        String username=this.username.getText().toString();
        String password=this.password.getText().toString();
        String passwordConfirm=this.passwordConfirm.getText().toString();
        String email=this.email.getText().toString();

        if(!password.equals(passwordConfirm)){
            Log.e(LOG_TAG,"A két jelszó nem egyezik");
            return;
        }

        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Log.d(LOG_TAG, "User created successfully");
                    startInsertSite();
                } else {
                    Log.d(LOG_TAG, "User wasn't created successfully");
                    Toast.makeText(RegisterActivity.this, "User wasn't created successfully: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
        Intent intent=new Intent(this, InsertActivity.class);
        intent.putExtra("SECRET_KEY",SECRET_KEY);
        startActivity(intent);
    }

    public void startInsertSite(){
        Intent intent=new Intent(this, InsertActivity.class);
        startActivity(intent);
    }

    public void cancel(View view) {
        finish();
    }
}