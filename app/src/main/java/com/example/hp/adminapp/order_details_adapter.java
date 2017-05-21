package com.example.hp.adminapp;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by ghumman on 5/22/2017.
 */

public class order_details_adapter extends RecyclerView.Adapter<order_details_holder> {

    JSONArray jarr;
    Activity a;

    public  order_details_adapter(JSONArray jarr , Activity a)
    {
        this.jarr = jarr;
        this.a = a ;
    }


    @Override
    public order_details_holder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new order_details_holder(LayoutInflater.from(a).inflate(R.layout.order_details_cell,parent , false));
    }

    @Override
    public void onBindViewHolder(order_details_holder holder, int position) {

        try {
            JSONObject job = jarr.getJSONObject(position);

            holder.item.setText(job.getString("itemname"));
            holder.qty.setText(job.getString("qty"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return jarr.length();
    }
}
