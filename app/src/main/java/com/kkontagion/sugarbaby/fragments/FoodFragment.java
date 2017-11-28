package com.kkontagion.sugarbaby.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kkontagion.sugarbaby.MainActivity;
import com.kkontagion.sugarbaby.MealCreatorActivity;
import com.kkontagion.sugarbaby.R;
import com.kkontagion.sugarbaby.adapters.MealsAdapter;

import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FoodFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FoodFragment extends Fragment {

    TextView tvTitle;
    RecyclerView lv;
    CardView card;

    MealsAdapter mealsAdapter;

    private int h;
    private String meal;

    public FoodFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment FoodFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FoodFragment newInstance() {
        FoodFragment fragment = new FoodFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    /**
     * Called when the fragment is visible to the user and actively running.
     */
    @Override
    public void onResume() {
        super.onResume();
        getMeal();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_food, container, false);

        tvTitle = v.findViewById(R.id.tv_h1);
        lv = v.findViewById(R.id.lv);
        card = v.findViewById(R.id.card);

        mealsAdapter = new MealsAdapter(getContext());
        lv.setAdapter(mealsAdapter);

        card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(getActivity(), MealCreatorActivity.class), 0);
            }
        });

        getMeal();

        return v;
    }

    private void getMeal() {
        h = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        if (h < 5)
            meal = "supper";
        else if (h < 12)
            meal = "breakfast";
        else if (h < 15)
            meal = "lunch";
        else if (h < 18)
            meal = "tea";
        else if (h < 20)
            meal = "dinner";
        else
            meal = "supper";
        if (tvTitle != null)
            tvTitle.setText(getString(R.string.food_h1, meal));
    }
}
