package com.example.taplalkozaskoveto;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    private static final String LOG_TAG = MainActivity.class.getName();
    private static final int SECRET_KEY = 66;
    EditText username;
    EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        username = findViewById(R.id.editTextUsername);
        password = findViewById(R.id.editTextPassword);
    }

    public void login(View view) {
        String username=this.username.getText().toString();
        String password=this.password.getText().toString();

        Log.i(LOG_TAG,"Bejelentkezett: "+username+" Jelszo: "+password);
    }

    public void register(View view) {
        Intent intent=new Intent(this,RegisterActivity.class);
        intent.putExtra("SECRET_KEY",SECRET_KEY);
        //TODO
        startActivity(intent);
    }
}