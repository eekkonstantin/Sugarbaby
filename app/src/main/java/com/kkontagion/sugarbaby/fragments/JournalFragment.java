package com.kkontagion.sugarbaby.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.utils.EntryXComparator;
import com.kkontagion.sugarbaby.Helper;
import com.kkontagion.sugarbaby.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Random;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link JournalFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class JournalFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    TextView tvTitle;
    EditText etRate;
    LineChart gvFeels;

    private static Random rand = new Random();

    private static final int START_DATE = 21;
    private static final int END_DATE = 31;


    public JournalFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment JournalFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static JournalFragment newInstance() {
        JournalFragment fragment = new JournalFragment();
        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_journal, container, false);

        tvTitle = v.findViewById(R.id.tv_title);
        etRate = v.findViewById(R.id.et_rate);
        gvFeels = v.findViewById(R.id.gv_feels);

        setupGraph();
        setupGraphGraphics();

        return v;
    }

    private void setupGraph() {
        ArrayList<Entry> ratings = new ArrayList<>();
        for (int i=0; i<END_DATE - START_DATE; i++)
            ratings.add(new Entry(i + rand.nextFloat(), rand.nextInt(11) + rand.nextFloat()));
        Collections.sort(ratings, new EntryXComparator());

        LineDataSet ds = new LineDataSet(ratings, "Wellness Rating");
        ds.setColor(getResources().getColor(R.color.colorAccent));
        ds.setCircleColor(getResources().getColor(R.color.colorAccentDark));
        LineData data = new LineData();
        data.addDataSet(ds);
        gvFeels.setData(data);
    }

    private void setupGraphGraphics() {

        final ArrayList<Calendar> cals = new ArrayList<>();
        for (int i=START_DATE; i<END_DATE; i++) {
            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.DAY_OF_MONTH, i);
            cals.add(cal);
        }


        IAxisValueFormatter formatter = new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return Helper.dateOnly(cals.get((int) value).getTime());
            }
        };

        gvFeels.setDescription(null);
        XAxis xAxis = gvFeels.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1f); // minimum axis-step (interval) is 1
        xAxis.setValueFormatter(formatter);
        gvFeels.getAxisRight().setEnabled(false);
//        YAxis yAxis = gvFeels.getAxis(YAxis.AxisDependency.LEFT);
//        yAxis.setGranularity(1.5f);
    }

}
