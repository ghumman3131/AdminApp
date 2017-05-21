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

public class menuInput extends AppCompatActivity {

    private RecyclerView menu_recycle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_input);

        menu_recycle = (RecyclerView) findViewById(R.id.recycler_id);

        get_menu_data();

    }


    public  void get_menu_data()
    {
        JSONObject jobj = new JSONObject();

        SharedPreferences sp = getSharedPreferences("user_info", MODE_PRIVATE);
        String canteen_id = sp.getString("canteen_id", "");

        try {
            jobj.put("canteen_id" , canteen_id);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jobreq = new JsonObjectRequest("http://" + marji.ip + "/foodexpress/get_menu.php", jobj, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    JSONArray jarr = response.getJSONArray("key");

                    menu_adapter ad = new menu_adapter(jarr , menuInput.this);

                    menu_recycle.setLayoutManager(new LinearLayoutManager(menuInput.this , LinearLayoutManager.VERTICAL , false));

                    menu_recycle.setAdapter(ad);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        jobreq.setRetryPolicy(new DefaultRetryPolicy(20000 , 2 , 2));

        AppController app = new AppController(menuInput.this);
        app.addToRequestQueue(jobreq);
    }

    public void add_menu(View view)

    {
        Intent i = new Intent(menuInput.this, AddMenu.class);
        startActivity(i);

    }
}
