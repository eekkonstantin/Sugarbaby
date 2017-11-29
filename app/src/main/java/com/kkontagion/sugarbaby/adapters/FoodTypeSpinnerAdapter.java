package com.kkontagion.sugarbaby.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.kkontagion.sugarbaby.MealCreatorActivity;
import com.kkontagion.sugarbaby.R;
import com.kkontagion.sugarbaby.objects.FoodType;

/**
 * Created by kkontagion on 029 29/11/2017.
 */

public class FoodTypeSpinnerAdapter extends BaseAdapter {
    Context ctx;

    public FoodTypeSpinnerAdapter(Context ctx) {
        this.ctx = ctx;
    }

    @Override
    public int getCount() {
        return FoodType.values().length;
    }

    @Override
    public FoodType getItem(int i) {
        return FoodType.values()[i];
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = ((MealCreatorActivity) ctx).getLayoutInflater().inflate(R.layout.item_autocomplete, null);

        FoodType ft = getItem(i);
        TextView tvTitle = view.findViewById(R.id.tv_title);

        view.findViewById(R.id.tv_details).setVisibility(View.GONE);
        tvTitle.setText(ft.capitalized());
        Glide.with(ctx).load(ft.getImg()).into((ImageView) view.findViewById(R.id.img_icon));

        return view;
    }




}
