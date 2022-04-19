package com.example.taplalkozaskoveto;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.DialogFragment;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;

public class InsertActivity extends AppCompatActivity {
    private static final String LOG_TAG = InsertActivity.class.getName();
    private FirebaseUser user;
    private EditText foodName;
    private EditText calories;
    private EditText protein;
    private EditText fiber;
    private EditText fat;
    private TextView caloriesAll;
    private TextView proteinAll;
    private TextView fatAll;
    private TextView fiberAll;
    private TextView sumAll;

    private FirebaseFirestore mFirestore;
    private CollectionReference mItems;
    private CollectionReference mPreferences;

    private NotificationHandler mNotificationHandler;
    @SuppressLint("StaticFieldLeak")
    private static Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert);
        mContext=this.getApplicationContext();
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

        caloriesAll=findViewById(R.id.caloriesAll);
        proteinAll=findViewById(R.id.proteinAll);
        fatAll=findViewById(R.id.fatAll);
        fiberAll=findViewById(R.id.fiberAll);
        sumAll=findViewById(R.id.sumAll);

        mFirestore = FirebaseFirestore.getInstance();
        mItems = mFirestore.collection("Items");
        mPreferences = mFirestore.collection("UserPreferences");
        mNotificationHandler=new NotificationHandler(this);
    }

    @SuppressLint("SetTextI18n")
    private void getInformations(){
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        String month = ((c.get(Calendar.MONTH)+1)+"").length()==1 ? "0"+(c.get(Calendar.MONTH)+1)+"" : (c.get(Calendar.MONTH)+1)+"";
        String day = (c.get(Calendar.DAY_OF_MONTH)+"").length()==1 ? "0"+c.get(Calendar.DAY_OF_MONTH)+"" : c.get(Calendar.DAY_OF_MONTH)+"";
        Log.d(LOG_TAG,"OnResume: "+year+"/"+month+"/"+day);
        mItems.whereEqualTo("uid",user.getUid()).whereEqualTo("date",year+"/"+month+"/"+day).get().addOnSuccessListener(queryDocumentSnapshots -> {
            int calories=0;
            int protein=0;
            int fat=0;
            int fiber=0;
            for(QueryDocumentSnapshot document: queryDocumentSnapshots){
                Food item = document.toObject(Food.class);
                calories+=item.getCalories();
                protein+=item.getProtein();
                fat+=item.getFat();
                fiber+=item.getFiber();
                Log.d(LOG_TAG,"Get data from database "+item.getName()+" "+item.getDate());
            }
            caloriesAll.setText(calories+"");
            proteinAll.setText(protein+"");
            fatAll.setText(fat+"");
            fiberAll.setText(fiber+"");
            mPreferences.whereEqualTo("uid",user.getUid()).get().addOnSuccessListener(queryDocumentSnapshots2 -> {
                Preferences item = queryDocumentSnapshots2.getDocuments().get(0).toObject(Preferences.class);
                if (item != null) {
                    this.caloriesAll.setText(caloriesAll.getText()+"/"+(int)item.getCalories() + "");
                    this.proteinAll.setText(proteinAll.getText()+"/"+(int)item.getProtein() + "");
                    this.fatAll.setText(fatAll.getText()+"/"+(int)item.getFat() + "");
                    this.fiberAll.setText(fiberAll.getText()+"/"+(int)item.getFiber() + "");
                }
            });
        });
        mItems.whereEqualTo("uid",user.getUid()).get().addOnSuccessListener(queryDocumentSnapshots -> {
            sumAll.setText(queryDocumentSnapshots.size()+"");
        });

    }

    @Override
    protected void onResume() {
        Log.d(LOG_TAG,"OnResume");
        getInformations();
        super.onResume();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void addFood(View view) {
        if (this.foodName.getText().toString().isEmpty()){
            this.foodName.setError("It can't be empty!");
            return;
        }
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
        String foodName=this.foodName.getText().toString();
        double calories=Double.parseDouble(this.calories.getText().toString());
        double protein=Double.parseDouble(this.protein.getText().toString());
        double fiber=Double.parseDouble(this.fiber.getText().toString());
        double fat=Double.parseDouble(this.fat.getText().toString());

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();

        mItems.add(new Food(user.getUid(),foodName,calories,protein,fiber,fat, dtf.format(now).toString()));

        getInformations();
        mNotificationHandler.send("You just added one record to the Nutrition Follower database");
        Log.i(LOG_TAG,"Upload Food: "+dtf.format(now));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.food_list_menu,menu);
        return true;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.log_out:
                Log.d(LOG_TAG, "Logout clicked!");
                FirebaseAuth.getInstance().signOut();
                finish();
                return true;
            case R.id.settings:
                Log.d(LOG_TAG, "Setting clicked!");
                startUpdateActivity();
                return true;
            case R.id.calendar:
                Log.d(LOG_TAG, "Calendar clicked!");
                showDatePickerDialog();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public static void startUpdateActivity(){
        Intent intent=new Intent(mContext, UpdateActivity.class);
        mContext.startActivity(intent);
    }

    public static void startFoodListActivity(int year, int month, int day){
        Intent intent=new Intent(mContext, FoodListActivity.class);
        intent.putExtra("YEAR",year);
        intent.putExtra("MONTH",month);
        intent.putExtra("DAY",day);
        mContext.startActivity(intent);
    }

    public void showDatePickerDialog() {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        @NonNull
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            Log.d(LOG_TAG,"Date picked");
            startFoodListActivity(year,month+1, day);
        }
    }
}

