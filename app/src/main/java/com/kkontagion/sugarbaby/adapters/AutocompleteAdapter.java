package com.kkontagion.sugarbaby.adapters;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
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
    private ArrayList<Food> suggestions;
    private int res;


    public AutocompleteAdapter(Context context, @LayoutRes int resource, ArrayList<Food> objects) {
        super(context, resource, objects);
        this.ctx = context;
        this.data = objects;
        this.res = resource;
        this.suggestions = new ArrayList<>();
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

    public boolean contains(String foodName) {
        for (Food f : data) {
            if (f.getDesc().equalsIgnoreCase(foodName))
                return true;
        }
        return false;
    }

    public void add(Food f) {
        data.add(f);
        notifyDataSetChanged();
    }

    @Override
    public Filter getFilter() {
        return nameFilter;
    }

    Filter nameFilter = new Filter() {
        @Override
        public String convertResultToString(Object resultValue) {
            String str = ((Food)(resultValue)).getDesc();
            return str;
        }
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            if(constraint != null) {
                suggestions.clear();
                for (Food f : data) {
                    if(f.filterContains(constraint.toString())){
                        suggestions.add(f);
                    }
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = suggestions;
                filterResults.count = suggestions.size();
                return filterResults;
            } else {
                return new FilterResults();
            }
        }
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            ArrayList<Food> filteredList = (ArrayList<Food>) results.values;
            if(results != null && results.count > 0) {
                clear();
                for (Food f : filteredList) {
                    add(f);
                }
                notifyDataSetChanged();
            }
        }
    };
}
