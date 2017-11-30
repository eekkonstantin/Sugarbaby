package com.kkontagion.sugarbaby.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import com.kkontagion.sugarbaby.TriggerListener;

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

    private TriggerListener mListener;

    TextView tvTitle, tvTrigger;
    EditText etRate;
    LineChart gvFeels;
    Button btNext;
    CardView card;


    private static Random rand = new Random();
    final ArrayList<Calendar> cals = new ArrayList<>();

    private static final int START_DATE = 21;
    private static final int END_DATE = 33;


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

    /**
     * Called when a fragment is first attached to its context.
     * {@link #onCreate(Bundle)} will be called after this.
     *
     * @param context
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof TriggerListener)
            mListener = (TriggerListener) context;
    }

    /**
     * Called when the fragment is no longer attached to its activity.  This
     * is called after {@link #onDestroy()}.
     */
    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_journal, container, false);

        tvTitle = v.findViewById(R.id.tv_title);
        etRate = v.findViewById(R.id.et_rate);
        gvFeels = v.findViewById(R.id.gv_feels);
        btNext = v.findViewById(R.id.bt_next);

        tvTrigger = v.findViewById(R.id.textView8);
        card = v.findViewById(R.id.card);

        setupGraph();
        setupGraphGraphics();

        setupActions();

        return v;
    }

    private void setupActions() {
        btNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (etRate.getText().length() < 1)
                    return;
                float rating = Float.parseFloat(etRate.getText().toString());
                // TODO alertdialog? symptoms

                Calendar cal = Calendar.getInstance();
                float date = findKey(cal) + cal.get(Calendar.HOUR_OF_DAY) / 24
                        + (cal.get(Calendar.MINUTE) / 60 / 10);
                gvFeels.getLineData().getDataSetByIndex(0).addEntryOrdered(new Entry(date, rating));
                gvFeels.getLineData().notifyDataChanged();
                gvFeels.notifyDataSetChanged();

                gvFeels.invalidate();
            }
        });


        // TESTING
        tvTrigger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.triggerHowYouDoin();
            }
        });

        card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.triggerPlsSeeDoc(new String[] {
                        "Dr Lee Kong Shen",
                        "Gleneagles Hospital\nLevel 3, Clinic G Room 502",
                        tvTitle.getText().toString()
                });
            }
        });
    }

    private void setupGraph() {
        ArrayList<Entry> ratings = new ArrayList<>();
        for (int i=0; i<END_DATE - START_DATE - 3; i++)
            ratings.add(new Entry(i + rand.nextFloat(), 1 + rand.nextInt(9) + rand.nextFloat()));
        Collections.sort(ratings, new EntryXComparator());

        LineDataSet ds = new LineDataSet(ratings, "Wellness Rating");
        ds.setColor(getResources().getColor(R.color.colorAccent));
        ds.setCircleColor(getResources().getColor(R.color.colorAccentDark));
        LineData data = new LineData();
        data.addDataSet(ds);
        gvFeels.setData(data);
    }

    private int findKey(Calendar search) {
        for (int i=0; i<cals.size(); i++)
            if (cals.get(i).get(Calendar.DAY_OF_MONTH) == search.get(Calendar.DAY_OF_MONTH))
                return i;

        return -1;
    }

    private void setupGraphGraphics() {

        for (int i=START_DATE; i<END_DATE; i++) {
            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.MONTH, Calendar.NOVEMBER);
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
