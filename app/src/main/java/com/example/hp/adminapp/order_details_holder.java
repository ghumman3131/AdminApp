package com.example.hp.adminapp;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

/**
 * Created by ghumman on 5/22/2017.
 */

public class order_details_holder extends RecyclerView.ViewHolder {


    public TextView item  , qty;
    public order_details_holder(View itemView) {
        super(itemView);

        item = (TextView) itemView.findViewById(R.id.item_name);
        qty = (TextView) itemView.findViewById(R.id.qty);
    }
}
