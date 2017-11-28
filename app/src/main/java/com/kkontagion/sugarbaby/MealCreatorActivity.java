package com.kkontagion.sugarbaby;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.kkontagion.sugarbaby.adapters.FoodAdapter;

public class MealCreatorActivity extends AppCompatActivity {

    RecyclerView rv;

    FoodAdapter foodAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal_creator);

        rv = findViewById(R.id.rv);
        foodAdapter = new FoodAdapter(this);

        rv.setAdapter(foodAdapter);
    }
}
