package com.kkontagion.sugarbaby.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kkontagion.sugarbaby.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MedsHomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MedsHomeFragment extends Fragment {


    public MedsHomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment MedsHomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MedsHomeFragment newInstance() {
        MedsHomeFragment fragment = new MedsHomeFragment();
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
        return inflater.inflate(R.layout.fragment_meds_home, container, false);
    }

}
