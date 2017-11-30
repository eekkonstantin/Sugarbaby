package com.kkontagion.sugarbaby.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.kkontagion.sugarbaby.R;
import com.kkontagion.sugarbaby.adapters.GoalsAdapter;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MonsterFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MonsterFragment extends Fragment {

    RecyclerView lv;
    ImageView img;
    TextView tv;

    GoalsAdapter adapter;

    private static int toggle = GoalsAdapter.NONE;


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

        img = v.findViewById(R.id.img_monster);
        Glide.with(getContext()).load(R.raw.angryjumpybear).into(img);

        tv = v.findViewById(R.id.tv_h1);

        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggle++;
                if (toggle == 3)
                    toggle = 0;
                adapter.setDone(toggle);
                lv.setAdapter(adapter);
                setImg();
            }
        });

        return v;
    }

    public void setImg() {
        switch (toggle) {
            case GoalsAdapter.NONE:
                Glide.with(getContext()).load(R.raw.angryjumpybear).into(img);
                tv.setText(R.string.monster_h1_fight);
                break;
            case GoalsAdapter.SOME:
                Glide.with(getContext()).load(R.raw.roamingknight).into(img);
                tv.setText(R.string.monster_h1_help);
                break;
            case GoalsAdapter.ALL:
                Glide.with(getContext()).load(R.raw.runningnoob).into(img);
                tv.setText(R.string.monster_h1_ded);
                break;
        }
    }

}
