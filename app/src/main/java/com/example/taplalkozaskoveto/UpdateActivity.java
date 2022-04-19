package com.example.taplalkozaskoveto;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.HashMap;
import java.util.Map;

public class UpdateActivity extends AppCompatActivity {
    private static final String LOG_TAG = UpdateActivity.class.getName();
    private FirebaseUser user;
    private EditText calories;
    private EditText protein;
    private EditText fiber;
    private EditText fat;

    private FirebaseFirestore mFirestore;
    private CollectionReference mItems;
    private String id;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        user = FirebaseAuth.getInstance().getCurrentUser();

        if(user!=null){
            Log.d(LOG_TAG,"Authenticated user");
        }
        else{
            Log.d(LOG_TAG,"Unauthenticated user");
            finish();
        }

        calories=findViewById(R.id.editTextCalorie);
        protein=findViewById(R.id.editTextProtein);
        fiber=findViewById(R.id.editTextFiber);
        fat=findViewById(R.id.editTextFat);
        mFirestore = FirebaseFirestore.getInstance();
        mItems = mFirestore.collection("UserPreferences");
        mItems.whereEqualTo("uid",user.getUid()).get().addOnSuccessListener(queryDocumentSnapshots -> {
            Preferences item = queryDocumentSnapshots.getDocuments().get(0).toObject(Preferences.class);
            item.setId(queryDocumentSnapshots.getDocuments().get(0).getId());
            if (item != null) {
                this.calories.setText((int)item.getCalories() + "");
                this.protein.setText((int)item.getProtein() + "");
                this.fat.setText((int)item.getFat() + "");
                this.fiber.setText((int)item.getFiber() + "");
                id= item._getId();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.back_menu,menu);
        return true;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return true;
    }

    public void addPreference(View view) {
        if (this.calories.getText().toString().isEmpty()){
            this.calories.setError("It can't be empty!");
            return;
        }
        if (this.protein.getText().toString().isEmpty()){
            this.protein.setError("It can't be empty!");
            return;
        }
        if (this.fiber.getText().toString().isEmpty()){
            this.fiber.setError("It can't be empty!");
            return;
        }
        if (this.fat.getText().toString().isEmpty()){
            this.fat.setError("It can't be empty!");
            return;
        }

        Map<String,Object> hm=new HashMap<>();
        hm.put("calories",Double.parseDouble(this.calories.getText().toString()));
        hm.put("protein",Double.parseDouble(this.protein.getText().toString()));
        hm.put("fiber",Double.parseDouble(this.fiber.getText().toString()));
        hm.put("fat",Double.parseDouble(this.fat.getText().toString()));
        Log.d(LOG_TAG,"Update ID: "+id);
        mItems.document(id).update(hm)
                .addOnFailureListener(fail -> {
                    Toast.makeText(this, "Preferences " + id + " cannot be changed.", Toast.LENGTH_LONG).show();
                }).addOnSuccessListener(success ->{
                    Log.d(LOG_TAG,"Updated on the database");
                    finish();
        });

    }
}