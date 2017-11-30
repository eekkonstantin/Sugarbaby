package com.kkontagion.sugarbaby.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.CombinedData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.ScatterData;
import com.github.mikephil.charting.data.ScatterDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.kkontagion.sugarbaby.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Random;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link MeasurementsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MeasurementsFragment extends Fragment {

//    private OnFragmentInteractionListener mListener;

    LineChart gvWeight, gvA1C;
    CombinedChart gvGlucose;
    TextView tvGlucose, tvA1C, tvWeight;

    public MeasurementsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment MeasurementsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MeasurementsFragment newInstance() {
        MeasurementsFragment fragment = new MeasurementsFragment();
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
        View v = inflater.inflate(R.layout.fragment_shots, container, false);

        tvA1C = v.findViewById(R.id.tv2);
        tvGlucose = v.findViewById(R.id.tv1);
        tvWeight = v.findViewById(R.id.tv3);

        gvA1C = v.findViewById(R.id.graph_a1c);
        gvGlucose = v.findViewById(R.id.graph_glucose);
        gvWeight = v.findViewById(R.id.graph_weight);

        setupGraphs();
        setupGraphGraphics();

        return v;
    }

    private void setupGraphs() {
        CombinedData glucose = new CombinedData();

        Entry[] glucose1d = new Entry[4];
        ArrayList<BarEntry> carbs1d = new ArrayList<>();
        ArrayList<BarEntry> cals1d = new ArrayList<>();

        Random rand = new Random();
        glucose1d[0] = new Entry(3 + rand.nextFloat(), 90 + rand.nextInt(50));
        glucose1d[1] = new Entry(5 + rand.nextFloat(), 90 + rand.nextInt(50));
        glucose1d[2] = new Entry(9 + rand.nextFloat(), 90 + rand.nextInt(50));
        glucose1d[3] = new Entry(11 + rand.nextFloat(), 100 + rand.nextInt(70));

        ArrayList<Float> mealtimes = new ArrayList<>();
        mealtimes.add(4 + rand.nextFloat());
        mealtimes.add(6 + rand.nextFloat());
        mealtimes.add(9 + rand.nextFloat());
        if (rand.nextBoolean())
            mealtimes.add(rand.nextInt(3) + rand.nextFloat());
        if (rand.nextBoolean())
            mealtimes.add(7 + rand.nextInt(2) + rand.nextFloat());
        for (Float f : mealtimes) {
            carbs1d.add(new BarEntry(f, 15 + rand.nextInt(10) + rand.nextFloat()));
            cals1d.add(new BarEntry(f, 50 + rand.nextInt(150)));
        }

        LineDataSet gGlucose1d = new LineDataSet(Arrays.asList(glucose1d), "Blood Sugar Level");
        gGlucose1d.setColor(getResources().getColor(R.color.colorAccent));
        gGlucose1d.setCircleColor(getResources().getColor(R.color.colorAccentDark));
        LineData d = new LineData();
        d.addDataSet(gGlucose1d);
        glucose.setData(d);

        BarDataSet gCarbs1d = new BarDataSet(carbs1d, "Carbohydrate Intake");
        gCarbs1d.setColor(getResources().getColor(R.color.warning));
        BarData b = new BarData();
//        b.setBarWidth(0.6f);

        BarDataSet gCals1d = new BarDataSet(cals1d, "Calorie Intake");
        gCals1d.setColor(getResources().getColor(R.color.background));
//        BarData c = new BarData();
        b.addDataSet(gCals1d);
        b.addDataSet(gCarbs1d);
        b.setBarWidth(0.3f);
        glucose.setData(b);

//        ScatterDataSet gCals1d = new ScatterDataSet(cals1d, "Calorie Intake");
//        gCals1d.setColor(getResources().getColor(R.color.warning));
//        ScatterData s = new ScatterData();
//        s.addDataSet(gCals1d);
//        s.setDrawValues(true);
//        glucose.setData(s);

        gvGlucose.setData(glucose);




    }

    private void setupGraphGraphics() {
        final String[] labels = new String[] {
                "12AM", "2AM", "4AM", "6AM", "8AM", "10AM",
                "12PM", "2PM", "4PM", "6PM", "8PM", "10PM",
                "12AM"
        };

        IAxisValueFormatter formatter = new IAxisValueFormatter() {

            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return labels[(int) value];
            }

        };

        XAxis xAxis = gvGlucose.getXAxis();
        xAxis.setGranularity(1f); // minimum axis-step (interval) is 1
        xAxis.setValueFormatter(formatter);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setAxisMinimum(xAxis.getAxisMinimum() - 1);
        xAxis.setAxisMaximum(xAxis.getAxisMaximum() + 1);
        gvGlucose.getAxisRight().setEnabled(false);
        gvGlucose.getAxisLeft().setEnabled(false);
        gvGlucose.getAxis(YAxis.AxisDependency.LEFT).setDrawGridLines(false);
        gvGlucose.getAxis(YAxis.AxisDependency.LEFT).setGranularity(3f);
        gvGlucose.setDescription(null);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
//        if (mListener != null) {
//            mListener.onFragmentInteraction(uri);
//        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
//        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
//    public interface OnFragmentInteractionListener {
//        // TODO: Update argument type and name
//        void onFragmentInteraction(Uri uri);
//    }
}
