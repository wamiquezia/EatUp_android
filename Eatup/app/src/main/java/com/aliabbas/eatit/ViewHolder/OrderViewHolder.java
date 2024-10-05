package com.aliabbas.eatit.ViewHolder;

import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.aliabbas.eatit.Interface.ItemClickListener;
import com.aliabbas.eatit.R;
import com.aliabbas.eatit.SignInActivity;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class OrderViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public Object txtOrderId;
    public TextView txtOrderid, txtOrderStatus, txtOrderPhone, txtOrderAddress,txtOrderPrice;
    public RecyclerView recyclerView;

    public OrderViewHolder(@NonNull View itemView) {
        super(itemView);

        txtOrderAddress = (TextView)itemView.findViewById(R.id.order_address);
        txtOrderid = (TextView)itemView.findViewById(R.id.order_id);
        txtOrderStatus = (TextView)itemView.findViewById(R.id.order_status);
        txtOrderPhone = (TextView)itemView.findViewById(R.id.order_phone);
        txtOrderPrice = (TextView)itemView.findViewById(R.id.order_price);
        recyclerView = itemView.findViewById(R.id.recyclerView);

    }
    @Override
    public void onClick(View view) {


    }
}
