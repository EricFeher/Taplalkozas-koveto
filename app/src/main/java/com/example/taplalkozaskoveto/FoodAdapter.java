package com.example.taplalkozaskoveto;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.ViewHolder> implements Filterable {
    private int db=0;
    private ArrayList<Food> mFoodItems;
    private ArrayList<Food> mFoodItemsAll;
    private Context mContext;
    private int lastPosition=-1;
    FoodAdapter(Context context, ArrayList<Food> items){
        this.mFoodItems=items;
        this.mFoodItemsAll=items;
        this.mContext=context;
    }

    @NonNull
    @Override
    public FoodAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull FoodAdapter.ViewHolder holder, int position) {
        Food currentItem = mFoodItems.get(position);
        holder.bindTo(currentItem);

        if(holder.getAdapterPosition() > lastPosition){
            if(holder.getAdapterPosition()%2==1){
                Animation animation = AnimationUtils.loadAnimation(mContext,R.anim.slide_in_row);
                holder.itemView.startAnimation(animation);
                lastPosition=holder.getAdapterPosition();
            }
            else{
                Animation animation = AnimationUtils.loadAnimation(mContext,R.anim.slide_in_row2);
                holder.itemView.startAnimation(animation);
                lastPosition=holder.getAdapterPosition();
            }
            Log.d("AdapterPosition","holder.getAdapterPosition(): "+holder.getAdapterPosition()+" lastPosition: "+lastPosition);

        }
    }

    @Override
    public int getItemCount() {
        return mFoodItemsAll.size();
    }

    @Override
    public Filter getFilter() {
        return foodFilter;
    }

    private Filter foodFilter=new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            ArrayList<Food> filteredList = new ArrayList<>();
            FilterResults results=new FilterResults();

            if(charSequence == null || charSequence.length()==0){
                results.count = mFoodItems.size();
                results.values = mFoodItemsAll;
            }
            else{
                String filterPattern = charSequence.toString().toLowerCase().trim();

                for(Food item: mFoodItemsAll){
                    if(item.getName().toLowerCase().contains(filterPattern)){
                        filteredList.add(item);
                    }
                    results.count = filteredList.size();
                    results.values = filteredList;
                }
            }
            return null;
        }

        @SuppressLint("NotifyDataSetChanged")
        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            mFoodItems = (ArrayList) filterResults.values;
            notifyDataSetChanged();
        }
    };

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView mName;
        private TextView mCalories;
        private TextView mFat;
        private TextView mProtein;
        private TextView mFiber;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mName=itemView.findViewById(R.id.name);
            mCalories=itemView.findViewById(R.id.calories);
            mFat=itemView.findViewById(R.id.fat);
            mProtein=itemView.findViewById(R.id.protein);
            mFiber=itemView.findViewById(R.id.fiber);
            Log.d("VIEWHOLDER","Lefutottam");
        }

        @SuppressLint("SetTextI18n")
        public void bindTo(Food currentItem) {
            mName.setText(currentItem.getName());
            mCalories.setText("Calories (kcal): "+currentItem.getCalories());
            mFat.setText("Fat (g): "+currentItem.getFat());
            mProtein.setText("Protein (g): "+currentItem.getProtein());
            mFiber.setText("Fiber (g): "+currentItem.getFiber());
            Log.d("bindTo: ",currentItem.getName());

            itemView.findViewById(R.id.delete).setOnClickListener(view -> {
                Log.d("Activity","Delete from database");
                ((FoodListActivity)mContext).deleteItem(currentItem);
            });
        }
    }
}


