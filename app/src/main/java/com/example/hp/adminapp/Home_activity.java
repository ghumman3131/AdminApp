package com.example.hp.adminapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Home_activity extends AppCompatActivity {

    RecyclerView order_history_rec;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_activity);

        order_history_rec = (RecyclerView) findViewById(R.id.order_rec);
        order_history_rec.setLayoutManager(new LinearLayoutManager(Home_activity.this , LinearLayoutManager.VERTICAL,false));

        get_orders();
    }

    public void input_menu(View view) {

        Intent i = new Intent(Home_activity.this , menuInput.class);

        startActivity(i);
    }

    public void canteen_profile(View view) {
        Intent i = new Intent(Home_activity.this,admin_profile.class);
        startActivity(i);

    }

    public void view_orders(View view) {
        Intent i = new Intent(Home_activity.this,view_orders.class);
        startActivity(i);


    }

    public void categories(View view) {
        Intent i = new Intent(Home_activity.this,categories.class);
        startActivity(i);

    }

    public void get_orders()
    {
        SharedPreferences sp = getSharedPreferences("user_info" , MODE_PRIVATE);


        JSONObject job = new JSONObject();

        try {
            job.put("canteen_id" , sp.getString("canteen_id" ,""));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        System.out.println(job);

        JsonObjectRequest jobreq = new JsonObjectRequest("http://" +marji.ip + "/foodexpress/get_admin_orders.php", job, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    System.out.println(response);
                    JSONArray jarr = response.getJSONArray("result");

                    order_history_adapter ad = new order_history_adapter(jarr , Home_activity.this);

                    order_history_rec.setAdapter(ad);



                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(error);
            }
        });

        jobreq.setRetryPolicy(new DefaultRetryPolicy(20000 , 2 , 2));

        AppController app = new AppController(Home_activity.this);
        app.addToRequestQueue(jobreq);
    }
}
