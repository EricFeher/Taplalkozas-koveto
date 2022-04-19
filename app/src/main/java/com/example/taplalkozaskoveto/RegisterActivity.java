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
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class RegisterActivity extends AppCompatActivity {
    private static final String LOG_TAG = RegisterActivity.class.getName();
    private static final int SECRET_KEY = 66;
    private EditText username;
    private EditText password;
    private EditText passwordConfirm;
    private EditText email;

    private FirebaseAuth mAuth;
    private FirebaseFirestore mFirestore;
    private CollectionReference mItems;
    private FirebaseUser user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Bundle bundle=getIntent().getExtras();
        int secret_key=bundle.getInt("SECRET_KEY");
        if(secret_key!=SECRET_KEY){
            finish();
        }
        mFirestore = FirebaseFirestore.getInstance();
        mItems = mFirestore.collection("UserPreferences");
        username = findViewById(R.id.editTextUsername);
        password = findViewById(R.id.editTextPassword);
        email = findViewById(R.id.editTextEmail);
        passwordConfirm = findViewById(R.id.editTextPasswordAgain);
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    protected void onResume() {
        super.onResume();
        user = FirebaseAuth.getInstance().getCurrentUser();
        if(user!=null){
            FirebaseAuth.getInstance().signOut();
        }
    }

    public void register(View view) {
        String username=this.username.getText().toString();
        String password=this.password.getText().toString();
        String passwordConfirm=this.passwordConfirm.getText().toString();
        String email=this.email.getText().toString();

        if (TextUtils.isEmpty(email)){
            this.email.setError("It can't be empty!");
            return;
        }
        if (TextUtils.isEmpty(password)){
            this.password.setError("It can't be empty!");
            return;
        }
        if (TextUtils.isEmpty(passwordConfirm)){
            this.passwordConfirm.setError("It can't be empty!");
            return;
        }
        if (TextUtils.isEmpty(username)){
            this.username.setError("It can't be empty!");
            return;
        }
        if(!password.equals(passwordConfirm)){
            Log.e(LOG_TAG,"A két jelszó nem egyezik");
            this.passwordConfirm.setError("Passwords have to be equal!");
            this.password.setError("Passwords have to be equal!");
            return;
        }

        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Log.d(LOG_TAG, "User created successfully");
                    mItems.add(new Preferences(2500,60,120,34,mAuth.getUid()));
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