package com.example.taplalkozaskoveto;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class InsertActivity extends AppCompatActivity {
    private static final String LOG_TAG = InsertActivity.class.getName();
    private FirebaseUser user;
    private EditText foodName;
    private EditText calories;
    private EditText protein;
    private EditText fiber;
    private EditText fat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert);

        user = FirebaseAuth.getInstance().getCurrentUser();

        if(user!=null){
            Log.d(LOG_TAG,"Authenticated user");
        }
        else{
            Log.d(LOG_TAG,"Unauthenticated user");
            finish();
        }

        foodName=findViewById(R.id.editTextName);
        calories=findViewById(R.id.editTextCalorie);
        protein=findViewById(R.id.editTextProtein);
        fiber=findViewById(R.id.editTextFiber);
        fat=findViewById(R.id.editTextFat);

    }

    public void addFood(View view) {
        String foodName=this.foodName.getText().toString().isEmpty() ? "None" : this.calories.getText().toString();
        int calories=Integer.parseInt(this.calories.getText().toString().isEmpty() ? 0+"" : this.calories.getText().toString());
        int protein=Integer.parseInt(this.protein.getText().toString().isEmpty() ? 0+"" : this.protein.getText().toString());
        int fiber=Integer.parseInt(this.fiber.getText().toString().isEmpty() ? 0+"" : this.fiber.getText().toString());
        int fat=Integer.parseInt(this.fat.getText().toString().isEmpty() ? 0+"" : this.fat.getText().toString());
        Log.i(LOG_TAG,"WTF IS WRONG HERE");
        //TODO: LEMENTENI AZ ADATOKAT
    }
}