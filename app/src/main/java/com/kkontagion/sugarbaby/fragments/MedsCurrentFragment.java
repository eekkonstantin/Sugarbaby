package com.kkontagion.sugarbaby.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.utils.EntryXComparator;
import com.kkontagion.sugarbaby.Helper;
import com.kkontagion.sugarbaby.R;
import com.kkontagion.sugarbaby.adapters.BasicCard;
import com.kkontagion.sugarbaby.objects.MedicineFake;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Random;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MedsCurrentFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MedsCurrentFragment extends Fragment {

    BarChart gvMissed;
    RecyclerView rv;

    ArrayList<MedicineFake> data;
    Random rand = new Random();

    public MedsCurrentFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment MedsCurrentFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MedsCurrentFragment newInstance() {
        MedsCurrentFragment fragment = new MedsCurrentFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }

        this.data = new ArrayList<>();
        ArrayList<Integer> regs = new ArrayList<>(Arrays.asList(8,20));
        data.add(new MedicineFake("Tolbutamide 500mg", 1, "tab", regs));
        regs = new ArrayList<>(Arrays.asList(20));
        data.add(new MedicineFake("Metformin 500mg", 1, "tab", regs));
        regs = new ArrayList<>(Arrays.asList(8, 14, 20));
        data.add(new MedicineFake("Acarbose 50mg", 1, "tab", regs));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_meds_current, container, false);

        gvMissed = v.findViewById(R.id.gv_missed);
        rv = v.findViewById(R.id.rv_meds);

        rv.setAdapter(new MedsAdapter(getContext(), data));
        setupGraph();
        setupGraphGraphics();

        return v;
    }

    private void setupGraph() {
        ArrayList<BarEntry> missed = new ArrayList<>();
        for (int i=0; i<data.size(); i++)
            missed.add(new BarEntry(i, rand.nextInt(8)));

        BarDataSet bs = new BarDataSet(missed, "Times Medication Missed");
        bs.setColor(getResources().getColor(R.color.colorAccent));
        BarData b = new BarData();
        b.setBarWidth(0.5f);
        b.addDataSet(bs);
        gvMissed.setData(b);
    }

    private void setupGraphGraphics() {




        IAxisValueFormatter formatter = new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return data.get((int) value).getName();
            }
        };

        IAxisValueFormatter yformatter = new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return ((int) value) + "";
            }
        };

        gvMissed.setDescription(null);
        XAxis xAxis = gvMissed.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1f); // minimum axis-step (interval) is 1
        xAxis.setValueFormatter(formatter);
        gvMissed.getAxisRight().setEnabled(false);
        YAxis yAxis = gvMissed.getAxis(YAxis.AxisDependency.LEFT);
        yAxis.setGranularity(1f);
        yAxis.setValueFormatter(yformatter);
    }

    public class MedsAdapter extends RecyclerView.Adapter<BasicCard> {

        private Context ctx;
        private ArrayList<MedicineFake> data;

        public MedsAdapter(Context ctx, ArrayList<MedicineFake> data) {
            this.ctx = ctx;
            this.data = data;
        }

        @Override
        public BasicCard onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_meal, parent, false);
            return new BasicCard(v);
        }

        @Override
        public void onBindViewHolder(BasicCard holder, int position) {
            MedicineFake item = data.get(position);
            holder.tvHeader.setText(item.toString());
            holder.tvSubtext.setVisibility(View.GONE);
            Glide.with(ctx).load(R.mipmap.ic_meds).into(holder.icon);
        }

        @Override
        public int getItemCount() {
            if (data.size() > 3)
                return 3;
            return data.size();
        }
    }

}
