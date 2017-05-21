package com.example.hp.adminapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.util.Base64;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class admin_profile extends AppCompatActivity {
    EditText mobile, info,  name;
    ImageView profile;
    private String image_s = "";
    boolean clickCount = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_profile);
        mobile = (EditText) findViewById(R.id.mobile);

        info = (EditText) findViewById(R.id.info);


        name = (EditText) findViewById(R.id.name);
        profile = (ImageView) findViewById(R.id.profile_pic);


        get_data();
    }

    public void get_data() {

        JSONObject jobj = new JSONObject();

        SharedPreferences sp = getSharedPreferences("user_info", MODE_PRIVATE);
        String admin_id = sp.getString("admin_id", "");

        try {
            jobj.put("admin_id", admin_id);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jobreq = new JsonObjectRequest("http://" + marji.ip + "/foodexpress/getAdmin_profile.php", jobj, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                System.out.println(response);
                try {
                    JSONObject job = response.getJSONObject("key");
                    String names = job.getString("name");
                    name.setText(names);
                    info.setText(job.getString("info"));
                    mobile.setText(job.getString("mobile"));



                    String img = job.getString("image");

                    image_s = img;

                    profile.setImageBitmap(StringToBitMap(img));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        jobreq.setRetryPolicy(new DefaultRetryPolicy(20000, 2, 2));

        AppController app = new AppController(admin_profile.this);
        app.addToRequestQueue(jobreq);
    }

    public Bitmap StringToBitMap(String encodedString) {
        try {
            byte[] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }

    public void update() {

        String information = info.getText().toString();

        String Mobile = mobile.getText().toString();
        if (name.equals("")) {
            Toast.makeText(admin_profile.this, "enter  name", Toast.LENGTH_SHORT).show();
            return;
        }
        if (info.equals("")) {
            Toast.makeText(admin_profile.this, "enter info", Toast.LENGTH_SHORT).show();
            return;
        }
        if (mobile.equals("")) {
            Toast.makeText(admin_profile.this, "enter mobile", Toast.LENGTH_SHORT).show();
            return;
        }



        SharedPreferences sp = getSharedPreferences("user_info", MODE_PRIVATE);
        String admin_id = sp.getString("admin_id", "");

        JSONObject obj = new JSONObject();
        try {

            obj.put("information_key", information);
            obj.put("name_key", name.getText().toString());

            obj.put("mobile_key", Mobile);
            obj.put("admin_key", admin_id);
            obj.put("image_key", image_s);


        } catch (JSONException e) {
            e.printStackTrace();
        }

        System.out.println(obj);

        JsonObjectRequest jobreq = new JsonObjectRequest("http://" + marji.ip + "/foodexpress/updateAdmin_profile.php", obj, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {


                Toast.makeText(admin_profile.this, "profile updated", Toast.LENGTH_SHORT).show();
                System.out.println(response);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(error);
            }
        });

        jobreq.setRetryPolicy(new DefaultRetryPolicy(20000, 2, 2));

        AppController app = new AppController(admin_profile.this);
        app.addToRequestQueue(jobreq);
    }

    public void select_image(View view) {

        Intent i = new Intent();
        i.setAction(Intent.ACTION_GET_CONTENT);
        i.setType("image/*");

        //File file = new File(Environment.getExternalStorageDirectory(),
        //      counter+".jpg");
        //Uri photoPath = Uri.fromFile(file);
        // i.putExtra(MediaStore.EXTRA_OUTPUT, photoPath);
        startActivityForResult(i, 100);
    }


    // function to convert bitmap to string

    public String getStringImage(Bitmap bmp) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == 100 && data != null) {
            Uri filePath = data.getData();
            try {
                //Getting the Bitmap from Gallery
                Bitmap bitmap2 = decodeUri(admin_profile.this, filePath, 700);
                image_s = getStringImage(bitmap2);
                //Setting the Bitmap to ImageView
                profile.setImageBitmap(bitmap2);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    // function to scale down image
    public Bitmap decodeUri(Context c, Uri uri, final int requiredSize)
            throws FileNotFoundException {
        BitmapFactory.Options o = new BitmapFactory.Options();
        o.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(c.getContentResolver().openInputStream(uri), null, o);

        int width_tmp = o.outWidth, height_tmp = o.outHeight;
        int scale = 1;

        while (true) {
            if (width_tmp / 2 < requiredSize || height_tmp / 2 < requiredSize)
                break;
            width_tmp /= 2;
            height_tmp /= 2;
            scale *= 2;
        }

        BitmapFactory.Options o2 = new BitmapFactory.Options();
        o2.inSampleSize = scale;
        return BitmapFactory.decodeStream(c.getContentResolver().openInputStream(uri), null, o2);
    }

    public void popup(View view) {
        PopupMenu popup = new PopupMenu(admin_profile.this, view);
        popup.getMenuInflater().inflate(R.menu.menu, popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.edit_id) {

                    if (!clickCount) {
                        Toast.makeText(getApplicationContext(), "click0", Toast.LENGTH_SHORT).show();

                        mobile.setEnabled(true);
                        profile.setEnabled(true);
                        info.setEnabled(true);

                        name.setEnabled(true);


                        clickCount = true;
                    } else {
                        Toast.makeText(getApplicationContext(), "click1", Toast.LENGTH_SHORT).show();

                        info.setEnabled(false);
                        profile.setEnabled(false);
                        name.setEnabled(false);

                        mobile.setEnabled(false);

                        clickCount = false;//this line is optional
                    }


                }

                if (item.getItemId() == R.id.update_id) {
                    update();
                }
                return false;
            }
        });

        popup.show();

    }


}    // function to upload image to server





