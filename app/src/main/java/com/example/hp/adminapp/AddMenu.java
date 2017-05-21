package com.example.hp.adminapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
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

import static com.example.hp.adminapp.R.id.adminEt;
import static com.example.hp.adminapp.R.id.passEt;

public class AddMenu extends AppCompatActivity {
    EditText item_name, item_price;
    Button Add;
    Spinner spinner_items;

    List<String> cat_ids ;

    RadioButton veg_rd , non_veg_rd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_menu);
        spinner_items = (Spinner) findViewById(R.id.spinnerItem);

        veg_rd = (RadioButton) findViewById(R.id.veg_rd);
        non_veg_rd = (RadioButton) findViewById(R.id.non_veg_rd);

        cat_ids = new ArrayList<>();
        SharedPreferences sp = getSharedPreferences("user_info", MODE_PRIVATE);
        final String canteen_id = sp.getString("canteen_id", "");

        item_name = (EditText) findViewById(R.id.name_editText);
        item_price = (EditText) findViewById(R.id.price_editText);
        Add = (Button) findViewById(R.id.add_button);
        View.OnClickListener click = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name_input = item_name.getText().toString();
                String price_input = item_price.getText().toString();
                JSONObject obj = new JSONObject();
                try {
                    obj.put("name_key", name_input);
                    obj.put("price_key", price_input);
                    obj.put("cat_id", cat_ids.get(spinner_items.getSelectedItemPosition()));

                    if(veg_rd.isChecked()) {
                        obj.put("flag_type", "veg");
                    }
                    else
                    {
                        obj.put("flag_type" , "non veg");
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                System.out.println(obj);
                JsonObjectRequest jobjreq = new JsonObjectRequest("http://" + marji.ip + "/foodexpress/menu_input.php", obj,

                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    if (response.getString("key").equals("done")) {

                                        Toast.makeText(AddMenu.this, "added", Toast.LENGTH_SHORT).show();


                                        finish();

                                    } else {
                                        Toast.makeText(AddMenu.this, "error", Toast.LENGTH_SHORT).show();
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

                AppController app = new AppController(AddMenu.this);
                app.addToRequestQueue(jobjreq);
            }

        };


        Add.setOnClickListener(click);

        get_data();
    }

    public void get_data() {
        JSONObject job = new JSONObject();

        SharedPreferences sp = getSharedPreferences("user_info", MODE_PRIVATE);
        String canteen_id = sp.getString("canteen_id", "");


        try {

            job.put("canteen_id", canteen_id);

        } catch (JSONException e) {
            e.printStackTrace();
        }


        System.out.println(job);
        JsonObjectRequest jobreq = new JsonObjectRequest("http://" + marji.ip + "/foodexpress/get_main_categories.php", job, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jarr = response.getJSONArray("result");

                    List<String> list = new ArrayList<>();
                    list.add("select category");
                    cat_ids.add(" ");

                    for (int i = 0; i < jarr.length(); i++) {
                        try {
                            JSONObject job = jarr.getJSONObject(i);
                            list.add(job.getString("name"));

                            cat_ids.add(job.getString("cat_id"));
                        } catch (JSONException e1) {
                            e1.printStackTrace();
                        }


                    }

                    ArrayAdapter<String> dataadapter = new ArrayAdapter<String>(AddMenu.this, R.layout.activity_spinner, list);
                    spinner_items.setAdapter(dataadapter);


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
        jobreq.setRetryPolicy(new DefaultRetryPolicy(20000, 3, 2));
        AppController app = new AppController(AddMenu.this);
        app.addToRequestQueue(jobreq);
    }
}


