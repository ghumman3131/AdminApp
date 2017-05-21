package com.example.hp.adminapp;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by ghumman on 5/22/2017.
 */

public class order_history_adapter extends RecyclerView.Adapter<order_history_holder> {


    JSONArray jarr ;
    Activity a;

    public order_history_adapter(JSONArray jarr , Activity a)
    {
        this.jarr = jarr;
        this.a = a;

    }


    @Override
    public order_history_holder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new order_history_holder(LayoutInflater.from(a).inflate(R.layout.order_history_cell,parent, false));
    }

    @Override
    public void onBindViewHolder(order_history_holder holder, int position) {

        try {
            final JSONObject job = jarr.getJSONObject(position);

            holder.orderid.setText(job.getString("id"));
            holder.bill.setText(job.getString("total"));

            holder.view_items.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(a , Order_details.class);
                    try {
                        i.putExtra("arr" , job.getString("order_items"));
                        a.startActivity(i);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return jarr.length();
    }
}
