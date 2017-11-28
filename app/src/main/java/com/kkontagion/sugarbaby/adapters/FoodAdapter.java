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

    public FoodAdapter(Context ctx) {
        this.ctx = ctx;
        this.food = new ArrayList<>();
        food.add(new Food(FoodType.MEAT, "Tofu bites", 51, 1.4));
        food.add(new Food(FoodType.VEGETABLE, "Steamed Carrots with Garlic-Ginger Butter", 69, 10.3));
        food.add(new Food(FoodType.BURGERS, "Black bean burger", 182, 15.6));
        food.add(new Food(FoodType.SWEETS, "Frozen yoghurt", 100, 18.6));
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
