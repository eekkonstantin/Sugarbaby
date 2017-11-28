package com.kkontagion.sugarbaby.adapters;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.kkontagion.sugarbaby.MainActivity;
import com.kkontagion.sugarbaby.MealCreatorActivity;
import com.kkontagion.sugarbaby.R;
import com.kkontagion.sugarbaby.objects.Food;
import com.kkontagion.sugarbaby.objects.Meal;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kkontagion on 028 28/11/2017.
 */

public class AutocompleteAdapter extends ArrayAdapter<Food> {
    private Context ctx;
    private ArrayList<Food> data;
    private int res;


    public AutocompleteAdapter(Context context, @LayoutRes int resource, ArrayList<Food> objects) {
        super(context, resource, objects);
        this.ctx = context;
        this.data = objects;
        this.res = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null)
            convertView = ((MealCreatorActivity) ctx).getLayoutInflater().inflate(res, parent, false);

        Food item = data.get(position);
        Glide.with(ctx).load(item.getType().getImg()).into((ImageView) convertView.findViewById(R.id.img_icon));
        ((TextView) convertView.findViewById(R.id.tv_title)).setText(item.getDesc());
        ((TextView) convertView.findViewById(R.id.tv_details)).setText(item.getNutrition());

        return convertView;
    }
}
