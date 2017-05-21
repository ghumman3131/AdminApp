package com.example.hp.adminapp;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;


/**
 * Created by ghumman on 5/21/2017.
 */

public class order_history_holder extends RecyclerView.ViewHolder {

    public TextView orderid , bill , view_items ;

    public order_history_holder(View itemView) {
        super(itemView);

        orderid = (TextView) itemView.findViewById(R.id.orderid);

        bill = (TextView) itemView.findViewById(R.id.bill);

        view_items = (TextView) itemView.findViewById(R.id.view_items);
    }
}
