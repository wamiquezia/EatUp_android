package com.aliabbas.eatit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.aliabbas.eatit.Interface.ItemClickListener;
import com.aliabbas.eatit.Model.Food;
import com.aliabbas.eatit.ViewHolder.FoodViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mancj.materialsearchbar.MaterialSearchBar;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import javax.crypto.AEADBadTagException;

public class FoodList extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    FirebaseDatabase database;
    DatabaseReference foodList;

    String categoryId="";
    FirebaseRecyclerAdapter<Food, FoodViewHolder> adapter;
    FirebaseRecyclerAdapter<Food, FoodViewHolder> SearcAadapter;
    List<String> suggestList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_list);

        //Firebase
        database = FirebaseDatabase.getInstance();
        foodList=database.getReference("Foods");
        recyclerView = (RecyclerView)findViewById(R.id.recycle_food);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        if(getIntent() !=null){
            categoryId = getIntent().getStringExtra("CategoryId");
        }
        if(!categoryId.isEmpty() && categoryId != null){
            loadListFood(categoryId);
        }



    }

    private void startSearch(CharSequence text) {
        SearcAadapter = new FirebaseRecyclerAdapter<Food, FoodViewHolder>(
                Food.class,
                R.layout.food_item,
                FoodViewHolder.class,
                foodList.orderByChild("Name").startAt(text.toString())
        ) {
            @Override
            protected void populateViewHolder(FoodViewHolder foodViewHolder, Food food, int i) {

                foodViewHolder.txtFoodName.setText(food.getName());
                Picasso.with(getBaseContext()).load(food.getImage())
                        .into(foodViewHolder.foodimage);
                final Food local = food;
                foodViewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int adapterPosition, boolean b) {
                        Intent foodDetail = new Intent(FoodList.this,FoodDetails.class);
                        foodDetail.putExtra("FoodId",SearcAadapter.getRef(adapterPosition).getKey());
                        startActivity(foodDetail);
                    }
                });

            }
        };
        recyclerView.setAdapter(SearcAadapter);
    }

    private void loadListFood(String categoryId) {
        adapter = new FirebaseRecyclerAdapter<Food, FoodViewHolder>(Food.class,R.layout.food_item,FoodViewHolder.class,foodList.orderByChild("menuId").equalTo(categoryId)) {
            @Override
            protected void populateViewHolder(FoodViewHolder ViewHolder, Food model, int i) {
                ViewHolder.txtFoodName.setText(model.getName());
                Picasso.with(getBaseContext()).load(model.getImage())
                        .into(ViewHolder.foodimage);

                final Food local =model;
                ViewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int adapterPosition, boolean b) {
                        //Start new activity
                        Intent foodDetail = new Intent(FoodList.this, FoodDetails.class);
                        foodDetail.putExtra("FoodId",adapter.getRef(adapterPosition).getKey());
                        startActivity(foodDetail);
                    }
                });

                }
        };
        //set adapter
        recyclerView.setAdapter(adapter);

    }
}