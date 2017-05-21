package com.example.hp.adminapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class adminApp extends AppCompatActivity {
    EditText adminEt, passEt;
    Button LoginBt;
    Button ClickForReg;
    CheckBox Check_box;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_app);
        adminEt = (EditText) findViewById(R.id.adminEt);
        passEt = (EditText) findViewById(R.id.passEt);
        LoginBt = (Button) findViewById(R.id.loginBt);
        Check_box = (CheckBox) findViewById(R.id.checkbox_first);


        SharedPreferences sp = getSharedPreferences("user_info", MODE_PRIVATE);
        String saved = sp.getString("remember", "");

        if (saved.equals("yes")) {
            Intent i = new Intent(adminApp.this, Home_activity.class);
            startActivity(i);

            finish();
        }


        View.OnClickListener click =new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String user_input = adminEt.getText().toString();
                String password_input = passEt.getText().toString();

                if (user_input.equals("") && password_input.equals("")) {

                    Toast.makeText(adminApp.this, "incorrect input", Toast.LENGTH_SHORT).show();

                    return;
                }
                JSONObject obj = new JSONObject();
                try {
                    obj.put("Admin_key", user_input);
                    obj.put("pass_key", password_input);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                System.out.println(obj);
                JsonObjectRequest jobjreq = new JsonObjectRequest("http://"+marji.ip+"/foodexpress/loginAdmin.php", obj,

                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {

                                try {
                                    if (response.getString("key").equals("done")) {

                                        SharedPreferences.Editor sp = getSharedPreferences("user_info", MODE_PRIVATE).edit();
                                        sp.putString("canteen_id", response.getString("canteen_id"));
                                        sp.putString("admin_id", response.getString("admin_id"));
                                        sp.commit();
                                        Boolean Check_it = Check_box.isChecked();
                                        if (Check_it) {
                                            sp.putString("remember", "yes");
                                            sp.commit();

                                        }

                                        System.out.println(response.getString("canteen_id"));
                                        Intent i = new Intent(adminApp.this, Home_activity.class);
                                        startActivity(i);
                                        finish();

                                    } else {
                                        Toast.makeText(adminApp.this, "error", Toast.LENGTH_SHORT).show();
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

                AppController app = new AppController(adminApp.this);
                app.addToRequestQueue(jobjreq);
            }

        };



        LoginBt.setOnClickListener(click);


    }}




