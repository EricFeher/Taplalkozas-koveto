package com.example.taplalkozaskoveto;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    private static final String LOG_TAG = MainActivity.class.getName();
    private static final int SECRET_KEY = 66;
    private EditText email;
    private EditText password;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        email = findViewById(R.id.editTextEmail);
        password = findViewById(R.id.editTextPassword);

        mAuth=FirebaseAuth.getInstance();
    }

    @Override
    protected void onResume() {
        super.onResume();
        user = FirebaseAuth.getInstance().getCurrentUser();
        if(user!=null){
            FirebaseAuth.getInstance().signOut();
        }
    }

    public void login(View view) {
        String email=this.email.getText().toString();
        String password=this.password.getText().toString();

        if (TextUtils.isEmpty(email)){
            this.email.setError("It can't be empty!");
            return;
        }
        if (TextUtils.isEmpty(password)){
            this.password.setError("It can't be empty!");
            return;
        }
        Log.i(LOG_TAG,"Bejelentkezett: "+email+" Jelszo: "+password);
        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Log.d(LOG_TAG, "User login successfully");
                    startInsertSite();
                } else{
                    Log.d(LOG_TAG, "User login fail");
                    Toast.makeText(MainActivity.this, "User login fail: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    public void startInsertSite(){
        Intent intent=new Intent(this, InsertActivity.class);
        startActivity(intent);
    }

    public void register(View view) {
        Intent intent=new Intent(this,RegisterActivity.class);
        intent.putExtra("SECRET_KEY",SECRET_KEY);
        startActivity(intent);
    }
}