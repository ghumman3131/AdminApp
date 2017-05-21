package com.example.hp.adminapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;


import org.json.JSONArray;
import org.json.JSONException;

public class Order_details extends AppCompatActivity {

    RecyclerView order_details_rec;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);

        order_details_rec = (RecyclerView) findViewById(R.id.details_rec);

        order_details_rec.setLayoutManager(new LinearLayoutManager(Order_details.this , LinearLayoutManager.VERTICAL , false));


        try {
            order_details_adapter ad = new order_details_adapter(new JSONArray(getIntent().getStringExtra("arr")) , Order_details.this);

            order_details_rec.setAdapter(ad);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
