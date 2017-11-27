package com.kkontagion.sugarbaby.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kkontagion.sugarbaby.R;
import com.kkontagion.sugarbaby.adapters.GoalsAdapter;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MonsterFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MonsterFragment extends Fragment {

    RecyclerView lv;

    GoalsAdapter adapter;


    public MonsterFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment MonsterFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MonsterFragment newInstance() {
        MonsterFragment fragment = new MonsterFragment();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_monster, container, false);

        lv = v.findViewById(R.id.lv);
        adapter = new GoalsAdapter(getContext());
        lv.setAdapter(adapter);

        return v;
    }

}