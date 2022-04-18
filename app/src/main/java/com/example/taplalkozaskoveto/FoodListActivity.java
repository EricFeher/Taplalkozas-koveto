package com.example.taplalkozaskoveto;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.Calendar;

public class FoodListActivity extends AppCompatActivity {
    private static final String LOG_TAG = FoodListActivity.class.getName();
    private FirebaseUser user;

    private RecyclerView mRecycleView;
    private ArrayList<Food> itemList;
    private FoodAdapter mAdapter;

    private FirebaseFirestore mFirestore;
    private CollectionReference mItems;

    private String year;
    private String month;
    private String day;

    public FoodListActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_list);
        user = FirebaseAuth.getInstance().getCurrentUser();

        if(user!=null){
            Log.d(LOG_TAG,"Authenticated user");
        }
        else{
            Log.d(LOG_TAG,"Unauthenticated user");
            finish();
        }

        year= getIntent().getExtras().get("YEAR").toString();
        month = getIntent().getExtras().get("MONTH").toString().length()==1 ?
                "0"+getIntent().getExtras().get("MONTH").toString() :
                getIntent().getExtras().get("MONTH").toString();
        day = getIntent().getExtras().get("DAY").toString().length()==1 ?
                "0"+getIntent().getExtras().get("DAY").toString() :
                getIntent().getExtras().get("DAY").toString();
        mRecycleView=findViewById(R.id.recyclerView);
        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            mRecycleView.setLayoutManager(new GridLayoutManager(this,2));
        } else {
            mRecycleView.setLayoutManager(new GridLayoutManager(this,1));
        }

        itemList=new ArrayList<>();

        mAdapter=new FoodAdapter(this,itemList);
        mRecycleView.setAdapter(mAdapter);


        mFirestore = FirebaseFirestore.getInstance();
        mItems = mFirestore.collection("Items");
        initializeData();
    }

    @SuppressLint("NotifyDataSetChanged")
    private void initializeData() {
        Log.d(LOG_TAG,"UserID: "+user.getUid()+" Date: "+year+"/"+month+"/"+day);
        mItems.whereEqualTo("uid",user.getUid()).whereEqualTo("date",year+"/"+month+"/"+day).get().addOnSuccessListener(queryDocumentSnapshots -> {
            for(QueryDocumentSnapshot document: queryDocumentSnapshots){
                Food item = document.toObject(Food.class);
                itemList.add(item);
                Log.d(LOG_TAG,"Get data from database "+item.getName()+" "+item.getDate());
            }
            if(itemList.size()==0){
                finish();
            }
            mAdapter.notifyDataSetChanged();
        });
    }
}