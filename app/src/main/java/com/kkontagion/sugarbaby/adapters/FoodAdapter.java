package com.kkontagion.sugarbaby.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kkontagion.sugarbaby.R;
import com.kkontagion.sugarbaby.objects.Food;
import com.kkontagion.sugarbaby.objects.FoodType;

import java.util.ArrayList;

/**
 * Created by kkontagion on 028 28/11/2017.
 */

public class FoodAdapter extends RecyclerView.Adapter<BasicCard> {
    public static final String TAG = "FoodAdapter";

    private Context ctx;
    private ArrayList<Food> food;

    public FoodAdapter(Context ctx, ArrayList<Food> food) {
        this.ctx = ctx;
        this.food = food;
    }

    @Override
    public BasicCard onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_meal, parent, false);
        return new BasicCard(v);
    }

    @Override
    public void onBindViewHolder(BasicCard holder, int position) {
        Food item = food.get(position);
        holder.tvHeader.setText(item.getDesc());
        holder.tvSubtext.setText(item.getNutrition());
        holder.icon.setImageDrawable(ctx.getDrawable(item.getType().getImg()));
    }

    @Override
    public int getItemCount() {
        return food.size();
    }

    public ArrayList<Food> getItems() {
        return food;
    }
}
