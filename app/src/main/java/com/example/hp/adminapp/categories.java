package com.example.hp.adminapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class categories extends AppCompatActivity {
    EditText add_categories;
    Button add_button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);
        add_categories = (EditText) findViewById(R.id.add_categories);
        add_button = (Button) findViewById(R.id.add_button);
        SharedPreferences sp = getSharedPreferences("user_info", MODE_PRIVATE);


        final String canteen_id = sp.getString("canteen_id", "");


        View.OnClickListener click = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String categories_input = add_categories.getText().toString();

                JSONObject obj = new JSONObject();
                try {

                    obj.put("categories_key", categories_input);
                    obj.put("canteen_id", canteen_id);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                System.out.println(obj);
                JsonObjectRequest jobjreq = new JsonObjectRequest("http://" + marji.ip + "/foodexpress/add_category.php", obj,

                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    if (response.getString("key").equals("done")) {


                                        Toast.makeText(categories.this , "done" , Toast.LENGTH_SHORT).show();
                                        finish();

                                    } else {
                                        Toast.makeText(categories.this, "error", Toast.LENGTH_SHORT).show();
                                    }
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

                jobjreq.setRetryPolicy(new DefaultRetryPolicy(20000, 2, 2));

                AppController app = new AppController(categories.this);
                app.addToRequestQueue(jobjreq);
            }

        };


        add_button.setOnClickListener(click);

    }

}
