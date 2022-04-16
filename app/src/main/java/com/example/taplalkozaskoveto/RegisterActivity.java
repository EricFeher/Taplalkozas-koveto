package com.example.taplalkozaskoveto;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class RegisterActivity extends AppCompatActivity {
    private static final String LOG_TAG = MainActivity.class.getName();
    private static final int SECRET_KEY = 66;
    EditText username;
    EditText password;
    EditText passwordConfirm;
    EditText email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Bundle bundle=getIntent().getExtras();
        int secret_key=bundle.getInt("SECRET_KEY");
        if(secret_key!=66){
            finish();
        }
        username = findViewById(R.id.editTextUsername);
        password = findViewById(R.id.editTextPassword);
        email = findViewById(R.id.editTextEmail);
        passwordConfirm = findViewById(R.id.editTextPasswordAgain);
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

        //TODO: Register
    }

    public void cancel(View view) {
        finish();
    }
}