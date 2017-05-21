package com.example.hp.adminapp;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by HP on 4/25/2017.
 */

public class menu_adapter extends RecyclerView.Adapter<menu_view_holder> {

    JSONArray jarr ;

    Activity a ;

    public menu_adapter(JSONArray jarr , Activity a)
    {
        this.jarr = jarr ;
        this.a = a;
    }

    @Override
    public menu_view_holder onCreateViewHolder(ViewGroup parent, int viewType) {

        // creating view_holder object and passing cell layout as parameter
        menu_view_holder view = new menu_view_holder(LayoutInflater.from(a).inflate(R.layout.menu_cell,parent,false));

        return view;
    }

    @Override
    public void onBindViewHolder(menu_view_holder holder, int position) {

        try {
            JSONObject jobj = jarr.getJSONObject(position);
            holder.menu_name.setText(jobj.getString("name"));

            holder.price_text.setText(jobj.getString("price"));

            holder.menu_image.setImageBitmap(StringToBitMap(jobj.getString("image")));

        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    @Override
    public int getItemCount() {
        return  jarr.length();
    }


    public Bitmap StringToBitMap(String encodedString){
        try {
            byte [] encodeByte= Base64.decode(encodedString,Base64.DEFAULT);
            Bitmap bitmap= BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch(Exception e) {
            e.getMessage();
            return null;
        }
    }
}
