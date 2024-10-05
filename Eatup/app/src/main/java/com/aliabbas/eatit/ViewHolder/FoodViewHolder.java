package com.aliabbas.eatit.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.aliabbas.eatit.Interface.ItemClickListener;
import com.aliabbas.eatit.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class FoodViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView txtFoodName;
    public ImageView foodimage;
    private ItemClickListener itemClickListener;

    public FoodViewHolder(@NonNull View itemView) {
        super(itemView);

        txtFoodName = (TextView)itemView.findViewById(R.id.food_name);
        foodimage = (ImageView)itemView.findViewById(R.id.food_image);
        itemView.setOnClickListener(this);
    }
    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View view) {
        itemClickListener.onClick(view,getAbsoluteAdapterPosition(),false);

    }
}
