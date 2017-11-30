package com.kkontagion.sugarbaby.adapters;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.kkontagion.sugarbaby.R;

/**
 * Created by kkontagion on 028 28/11/2017.
 */

public class BasicCard extends RecyclerView.ViewHolder {
    public TextView tvHeader, tvSubtext;
    public ImageView icon;
    public CardView card;

    public BasicCard(View v) {
        super(v);
        tvHeader = v.findViewById(R.id.tv_title);
        tvSubtext = v.findViewById(R.id.tv_details);
        icon = v.findViewById(R.id.img_icon);
        card = v.findViewById(R.id.card);
    }
}