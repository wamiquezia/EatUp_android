package com.aliabbas.eatit;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aliabbas.eatit.Model.Food;

import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;

public class FoodDetailAdapter extends RecyclerView.Adapter<FoodDetailAdapter.MyViewHolder> {

    private ArrayList<Food> transactionsList;
    private Context mContext;
    String typr="";



    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView foodname,foodid,fooddesc,foodprice;


        public MyViewHolder(View view) {
            super(view);

            foodname =  view.findViewById(R.id.foodname);


        }
    }

    public FoodDetailAdapter(ArrayList<Food> transactionsList, Context mContext, String typr) {
        this.transactionsList = transactionsList;
        this.mContext = mContext;
        this.typr = typr;
    }

    @Override
    public FoodDetailAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fooddetailadapter, parent, false);

        return new FoodDetailAdapter.MyViewHolder(itemView);


    }

    @Override
    public void onBindViewHolder(final FoodDetailAdapter.MyViewHolder holder, final int position) {
        final Food operator = transactionsList.get(position);

        holder.foodname.setText("" + operator.getProductName());




    }



    @Override
    public int getItemCount() {
        return transactionsList.size();
    }

}
