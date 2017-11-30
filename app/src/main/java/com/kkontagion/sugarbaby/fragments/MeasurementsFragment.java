package com.kkontagion.sugarbaby.fragments;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

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
import com.github.mikephil.charting.utils.EntryXComparator;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.kkontagion.sugarbaby.Helper;
import com.kkontagion.sugarbaby.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
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

    View alertLayout;
    TextView tvTime, tvUnit;
    EditText etMeas;
    Calendar time;

    Random rand;
    AlertDialog.Builder alert;

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
        rand = new Random();
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


        setupDialog();
        setupGraphs();
        setupGraphGraphics();
        setupActions();

        return v;
    }

    private void setupDialog() {
        alertLayout = getLayoutInflater().inflate(R.layout.dialog_meas_add, null);
        tvTime = alertLayout.findViewById(R.id.tv_time);
        tvUnit = alertLayout.findViewById(R.id.tv_unit);
        etMeas = alertLayout.findViewById(R.id.et_meas);

        alert = new AlertDialog.Builder(getContext());
        alert.setTitle(getString(R.string.meas_h1, "???")).setView(alertLayout)
                .setPositiveButton(R.string.dialog_pos, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (etMeas.getText().length() < 1)
                            return;

                        String meas = etMeas.getHint().toString().trim();
                        float data = Float.parseFloat(etMeas.getText().toString());
                        float div = time.get(Calendar.DAY_OF_MONTH) / time.get(Calendar.MONTH);
                        float date = time.get(Calendar.MONTH) + (
                                div < 1 ? div : 0f
                        );

                        if (meas.equalsIgnoreCase("a1c"))
                            gvA1C.getLineData().getDataSetByIndex(0).addEntryOrdered(new Entry(
                                    date,
                                    data
                            ));
                        else if (meas.contains("Blood"))
                            gvGlucose.getCombinedData().getLineData().getDataSetByIndex(0).addEntryOrdered(new Entry(
                                    time.get(Calendar.HOUR_OF_DAY) / 2f + time.get(Calendar.MINUTE) / 120f,
                                    data
                            ));
                        else if (meas.contains("Weight"))
                            gvWeight.getLineData().getDataSetByIndex(0).addEntryOrdered(new Entry(
                                    date,
                                    data
                            ));


                        notifyGraphs();
                    }
                });


        final TimePickerDialog.OnTimeSetListener mTimeSetListener = new TimePickerDialog.OnTimeSetListener(){
            @Override
            public void onTimeSet(TimePicker timePicker, int i , int i1){
                time.set(Calendar.HOUR_OF_DAY,i);
                time.set(Calendar.MINUTE,i1);
                tvTime.setText(Helper.date(time.getTime()));
            }
        };

        final DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener(){
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1,int i2){

                time.set(i, i1, i2);

                int hour = time.get(Calendar.HOUR_OF_DAY);
                int minutes = time.get(Calendar.MINUTE);

                TimePickerDialog dialog = new TimePickerDialog(
                        getContext(),
                        R.style.TimePickerTheme,
                        mTimeSetListener,
                        hour, minutes,
                        false);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        };



        tvTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog dialog = new DatePickerDialog(
                        getContext(),
                        R.style.TimePickerTheme,
                        mDateSetListener,
                        time.get(Calendar.YEAR),
                        time.get(Calendar.MONTH),
                        time.get(Calendar.DAY_OF_MONTH)
                );
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });
    }

    private void setupActions() {
        View.OnClickListener triggerDialog = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String meas = ((TextView) view).getText().toString().split("Level")[0].trim();
                setupDialog();
                time = Calendar.getInstance();
                tvTime.setText(Helper.date(time.getTime()));
                etMeas.setHint(meas);
                if (meas.equalsIgnoreCase("A1C"))
                    tvUnit.setText("%");
                else if (meas.contains("Blood"))
                    tvUnit.setText("mg/dL");
                else if (meas.contains("Weight"))
                    tvUnit.setText("kg");
                triggerDialog(meas);
            }
        };
        tvA1C.setOnClickListener(triggerDialog);
        tvGlucose.setOnClickListener(triggerDialog);
        tvWeight.setOnClickListener(triggerDialog);
    }

    private void triggerDialog(String meas) {
        alert.setTitle(getString(R.string.meas_h1, meas));
        alert.create().show();
    }




    private void setupGraphs() {
        CombinedData glucose = new CombinedData();

        ArrayList<Entry> glucose1d = new ArrayList<>();
        ArrayList<BarEntry> carbs1d = new ArrayList<>();
        ArrayList<BarEntry> cals1d = new ArrayList<>();

        glucose1d.add(new Entry(3 + rand.nextFloat(), 90 + rand.nextInt(50)));
        glucose1d.add(new Entry(5 + rand.nextFloat(), 90 + rand.nextInt(50)));
        glucose1d.add(new Entry(9 + rand.nextFloat(), 90 + rand.nextInt(50)));
        glucose1d.add(new Entry(11 + rand.nextFloat(), 100 + rand.nextInt(70)));

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

        Collections.sort(glucose1d, new EntryXComparator());
        LineDataSet gGlucose1d = new LineDataSet(glucose1d, "Blood Sugar Level");
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

        gvGlucose.setData(glucose);


        ArrayList<Entry> a1C1d = new ArrayList<>();
        for (int i=0; i<8; i+=3)
            a1C1d.add(new Entry(i + rand.nextInt(3) + rand.nextFloat(), 6.3f + rand.nextInt(2) + rand.nextFloat()));
        Collections.sort(a1C1d, new EntryXComparator());
        LineDataSet gA1C1d = new LineDataSet(a1C1d, "A1C Measurements");
        gA1C1d.setColor(getResources().getColor(R.color.colorAccent));
        gA1C1d.setCircleColor(getResources().getColor(R.color.colorAccentDark));
        LineData a = new LineData();
        a.addDataSet(gA1C1d);
        gvA1C.setData(a);

        ArrayList<Entry> wt1d = new ArrayList<>();
        for (int i=0; i<10; i+=2)
            wt1d.add(new Entry(i + rand.nextInt(3) + rand.nextFloat(), 80f + rand.nextInt(6) + rand.nextFloat()));
        Collections.sort(wt1d, new EntryXComparator());
        LineDataSet gWt1d = new LineDataSet(wt1d, "Weight");
        gWt1d.setColor(getResources().getColor(R.color.colorAccent));
        gWt1d.setCircleColor(getResources().getColor(R.color.colorAccentDark));
        LineData w = new LineData();
        w.addDataSet(gWt1d);
        gvWeight.setData(w);
    }

    private void notifyGraphs() {
        gvGlucose.getCombinedData().getLineData().notifyDataChanged();
        gvGlucose.getCombinedData().getBarData().notifyDataChanged();
        gvGlucose.getCombinedData().notifyDataChanged();
        gvGlucose.notifyDataSetChanged();
        gvGlucose.invalidate();

        gvA1C.getLineData().notifyDataChanged();
        gvA1C.notifyDataSetChanged();
        gvA1C.invalidate();
    }

    private void setupGraphGraphics() {
        final String[] timeLabels = new String[] {
                "12AM", "2AM", "4AM", "6AM", "8AM", "10AM",
                "12PM", "2PM", "4PM", "6PM", "8PM", "10PM",
                "12AM"
        };

        final String[] monthLabels = new String[] {
                "Jan", "Feb", "Mar", "Apr", "May", "Jun",
                "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"
        };

//        final String[] monthLabels = shuffleStart(months);

        IAxisValueFormatter formatter = new IAxisValueFormatter() {

            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return timeLabels[(int) value];
            }

        };

        XAxis xAxis = gvGlucose.getXAxis();
        xAxis.setGranularity(1f); // minimum axis-step (interval) is 1
        xAxis.setValueFormatter(formatter);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setAxisMinimum(xAxis.getAxisMinimum() - 1);
        xAxis.setAxisMaximum(xAxis.getAxisMaximum() + 1);
        gvGlucose.getAxisRight().setEnabled(false);
        YAxis yAxis = gvGlucose.getAxis(YAxis.AxisDependency.LEFT);
//        yAxis.setDrawGridLines(false);
        yAxis.setGranularity(3f);
        gvGlucose.setDescription(null);

        formatter = new IAxisValueFormatter() {

            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return monthLabels[(int) value];
            }

        };

        gvA1C.setDescription(null);
        xAxis = gvA1C.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1f); // minimum axis-step (interval) is 1
        xAxis.setValueFormatter(formatter);
        gvA1C.getAxisRight().setEnabled(false);
        yAxis = gvA1C.getAxis(YAxis.AxisDependency.LEFT);
        yAxis.setGranularity(1f);

        gvWeight.setDescription(null);
        xAxis = gvWeight.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1.5f); // minimum axis-step (interval) is 1
        xAxis.setValueFormatter(formatter);
        gvWeight.getAxisRight().setEnabled(false);
        yAxis = gvWeight.getAxis(YAxis.AxisDependency.LEFT);
        yAxis.setGranularity(1f);
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

    private String[] shuffleStart(String[] start) {
        String[] out = new String[start.length];
        int s = rand.nextInt(start.length);
        out[0] = start[s];

        for (int i=1; i<start.length; i++) {
            if (++s == start.length)
                s = 0;
            out[i] = start[s];
        }
        return out;
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
