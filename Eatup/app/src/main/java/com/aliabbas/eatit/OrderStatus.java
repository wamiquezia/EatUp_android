package com.aliabbas.eatit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.aliabbas.eatit.Common.Common;
import com.aliabbas.eatit.Interface.ItemClickListener;
import com.aliabbas.eatit.Model.Food;
import com.aliabbas.eatit.Model.Order;
import com.aliabbas.eatit.Model.Requestf;
import com.aliabbas.eatit.ViewHolder.MenuViewHolder;
import com.aliabbas.eatit.ViewHolder.OrderViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class OrderStatus extends AppCompatActivity {

    public RecyclerView recyclerView;
    Requestf transactions = new Requestf();
    public RecyclerView.LayoutManager layoutManager;
    Food food;

    FirebaseRecyclerAdapter<Requestf, OrderViewHolder> adapter;
    FoodDetailAdapter mAdapter;
    ArrayList<Food> transactionsObjects = new ArrayList<>();
    FirebaseDatabase database;
    DatabaseReference requests;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_status);

        database = FirebaseDatabase.getInstance();
        requests = database.getReference("Requests");

        recyclerView = (RecyclerView)findViewById(R.id.listOrders);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        loadOrders(Common.currentUser.getPhone());

    }

    private void loadOrders(String phone) {

        adapter = new FirebaseRecyclerAdapter<Requestf, OrderViewHolder>(
                Requestf.class,
                R.layout.order_layout,
                OrderViewHolder.class,
                requests.orderByChild("phone").equalTo(phone)
        ) {
            @Override
            protected void populateViewHolder(OrderViewHolder orderViewHolder, Requestf request, int i) {
                orderViewHolder.txtOrderid.setText(adapter.getRef(i).getKey());
                orderViewHolder.txtOrderStatus.setText(convertCodeToStatus(request.getStatus()));
                orderViewHolder.txtOrderAddress.setText(request.getAddress());
                orderViewHolder.txtOrderPhone.setText(request.getPhone());
                orderViewHolder.txtOrderPrice.setText(request.getTotal());


                String response = ""+new Gson().toJson(request).toString();
                Gson gson = new Gson();
                transactions = gson.fromJson(response, Requestf.class);
                transactionsObjects = transactions.getFoods();
                Log.e("transactionsObjects","   transactionsObjects  :    "+ transactionsObjects.size() );
                if (transactionsObjects.size() > 0) {

                    mAdapter = new FoodDetailAdapter(transactionsObjects, OrderStatus.this,"sub");
                    layoutManager = new LinearLayoutManager(OrderStatus.this);
                    orderViewHolder.recyclerView.setLayoutManager(layoutManager);
                    orderViewHolder.recyclerView.setItemAnimator(new DefaultItemAnimator());
                    orderViewHolder.recyclerView.setAdapter(mAdapter);
                    orderViewHolder.recyclerView.setVisibility(View.VISIBLE);

                } else {
                    orderViewHolder.recyclerView.setVisibility(View.GONE);
                }

            }
        };
        recyclerView.setAdapter(adapter);

    }

    private String convertCodeToStatus(String status) {
        if (status.equals("0"))
            return "Placed";
        else if(status.equals("1"))
            return "On My Way";
        else
            return "Shipped";
    }


}