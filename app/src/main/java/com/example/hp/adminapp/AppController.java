package com.example.hp.adminapp;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

/**
 * Created by HP on 4/19/2017.
 */

public class AppController {
    private RequestQueue mRequestQueue;

    private Context c;


    private static AppController mInstance;

    public AppController (Context c)
    {
        mInstance = this;
        this.c = c;

    }

    public static synchronized AppController getInstance()
    {
        return mInstance;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null)
        {
            mRequestQueue = Volley.newRequestQueue(c);
        }

        return mRequestQueue;
    }



    public  void addToRequestQueue(JsonObjectRequest req) {

        getRequestQueue().add(req);
    }
}
