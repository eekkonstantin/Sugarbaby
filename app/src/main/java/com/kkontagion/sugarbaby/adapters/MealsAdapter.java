package com.kkontagion.sugarbaby.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.kkontagion.sugarbaby.R;
import com.kkontagion.sugarbaby.objects.Meal;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;

/**
 * Created by kkontagion on 028 28/11/2017.
 */

public class MealsAdapter extends RecyclerView.Adapter<BasicCard> {
    public static final String TAG = "MealsAdapter";

    private Context ctx;
    private ArrayList<Meal> meals;

    public MealsAdapter(Context ctx) {
        this.ctx = ctx;
        meals = new ArrayList<>();
        Random rand = new Random();
        for (int i=20; i>17; i--) {
            if (rand.nextBoolean())
                meals.add(new Meal(i, 22 + rand.nextInt(24 - 22), rand.nextInt(60)));
            meals.add(new Meal(i, 18 + rand.nextInt(22 - 18), rand.nextInt(60)));
            if (rand.nextBoolean())
                meals.add(new Meal(i, 15 + rand.nextInt(17 - 15), rand.nextInt(60)));
            meals.add(new Meal(i, 12 + rand.nextInt(15 - 12), rand.nextInt(60)));
            meals.add(new Meal(i, 5 + rand.nextInt(12 - 5), rand.nextInt(60)));
        }
    }

    public void add(Meal m) {
        meals.add(0, m);
        notifyDataSetChanged();
    }

    /**
     * This new ViewHolder should be constructed with a new View that can represent the items
     * of the given type. You can either create a new View manually or inflate it from an XML
     * layout file.
     *
     * @param parent   The ViewGroup into which the new View will be added after it is bound to
     *                 an adapter position.
     * @param viewType The view type of the new View.
     * @return A new ViewHolder that holds a View of the given view type.
     */
    @Override
    public BasicCard onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_meal, parent, false);
        return new BasicCard(v);
    }

    /**
     * Called by RecyclerView to display the data at the specified position. This method should
     * update the contents to reflect the item at the given position.
     *
     * @param holder   The ViewHolder which should be updated to represent the contents of the
     *                 item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(BasicCard holder, int position) {
        Meal item = meals.get(position);
        holder.tvHeader.setText(item.getDate());
        if (item.getAte() == null) // IS TESTING
            holder.tvSubtext.setText(ctx.getString(R.string.food_meal_totals, 50.0, 3.0));
        else
            holder.tvSubtext.setText(ctx.getString(R.string.food_meal_totals, item.getCarbs(), item.getCalories()));

        int ico = R.mipmap.ic_moon;
        if (item.getCalendarDate().get(Calendar.HOUR_OF_DAY) < 18
                || item.getCalendarDate().get(Calendar.HOUR_OF_DAY) > 6)
            ico = R.mipmap.ic_sun;
        Glide.with(ctx).load(ico).into(holder.icon);
    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount() {
        return meals.size();
    }
}
