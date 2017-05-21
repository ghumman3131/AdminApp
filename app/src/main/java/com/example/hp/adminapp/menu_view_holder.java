package com.example.hp.adminapp;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by HP on 4/25/2017.
 */

public class menu_view_holder extends RecyclerView.ViewHolder {

    public TextView menu_name , price_text;

    public ImageView menu_image ;

    public menu_view_holder(View itemView) {
        super(itemView);

        menu_name = (TextView) itemView.findViewById(R.id.menu_id);

        price_text = (TextView) itemView.findViewById(R.id.price_id);
        menu_image = (ImageView) itemView.findViewById(R.id.menu_image);
    }
}
