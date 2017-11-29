package com.kkontagion.sugarbaby.fragments;


import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.bumptech.glide.Glide;
import com.kkontagion.sugarbaby.R;
import com.kkontagion.sugarbaby.adapters.BasicCard;
import com.kkontagion.sugarbaby.objects.MedicineFake;
import com.kkontagion.sugarbaby.views.MultiSpinner;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MedsHomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MedsHomeFragment extends Fragment {

    private TriggerListener mListener;

    RecyclerView rvTodo, rvPast;
    ArrayList<MedicineFake> meds;
    ArrayList<String[]> medsPast;


    FutureAdapter future;
    List<String> items;


    private static SimpleDateFormat df = new SimpleDateFormat("dd MMM yyyy hh:mmaa");


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
        setHasOptionsMenu(true);
        if (getArguments() != null) {
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof TriggerListener)
            mListener = (TriggerListener) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_meds_home, container, false);

        initArrays();
        rvPast = v.findViewById(R.id.rv_past);
        rvTodo = v.findViewById(R.id.rv_todo);

        rvPast.setAdapter(new PastAdapter(getContext(), medsPast));
        future = new FutureAdapter(getContext(), meds);
        rvTodo.setAdapter(future);

        // FOR DEMO
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("ugh", "onClick: poop");
                if (mListener != null)
                    mListener.triggerNotification(meds.get(0));
            }
        });

        return v;
    }

    private void initArrays() {
        items = Arrays.asList(getResources().getStringArray(R.array.hour_of_day));

        meds = new ArrayList<>();
        ArrayList<Integer> regs = new ArrayList<>(Arrays.asList(8,20));
        meds.add(new MedicineFake("Tolbutamide 500mg", 1, "tab", regs));
        regs = new ArrayList<>(Arrays.asList(20));
        meds.add(new MedicineFake("Metformin 500mg", 1, "tab", regs));
        regs = new ArrayList<>(Arrays.asList(8, 14, 20));
        meds.add(new MedicineFake("Acarbose 50mg", 1, "tab", regs));

        medsPast = new ArrayList<>();
        Calendar cal = Calendar.getInstance();
        for (int i=18; i<21; i++) {
            for (int h=0; h<24; h++) {
                cal.set(2017, 11, i, h, 0);
                for (MedicineFake med : meds) {
                    ArrayList<Integer> reg = med.getRegularity();
                    if (reg.contains(h))
                        medsPast.add(new String[] {med.toString(), df.format(cal.getTime())});
                }
            }
        }
        Collections.reverse(medsPast);
        Collections.sort(meds);
    }

    /**
     * Initialize the contents of the Fragment host's standard options menu.  You
     * should place your menu items in to <var>menu</var>.  For this method
     * to be called, you must have first called {@link #setHasOptionsMenu}.
     *
     * @param menu     The options menu in which you place your items.
     * @param inflater
     * @see #setHasOptionsMenu
     * @see #onPrepareOptionsMenu
     * @see #onOptionsItemSelected
     */
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        MenuItem add = menu.add(getString(R.string.meds_add));
        add.setIcon(R.mipmap.ic_add);
        add.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                final View alertLayout = getLayoutInflater().inflate(R.layout.dialog_meds_add, null);
                MultiSpinner ms = alertLayout.findViewById(R.id.spinner);

                final ArrayList<Integer> sel = new ArrayList<>();

                ms.setItems(items, items.get(0), new MultiSpinner.MultiSpinnerListener() {
                    @Override
                    public void onItemsSelected(boolean[] selected) {
                        for (int i=0; i<selected.length; i++) {
                            if (selected[i])
                                sel.add(i);
                        }
                    }
                });

                // dialog to confirm carbs and cals and food type
                AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
                alert.setTitle(R.string.meds_add).setView(alertLayout)
                        .setPositiveButton(R.string.dialog_pos, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface di, int i) {
                                EditText etName = alertLayout.findViewById(R.id.et_name);
                                EditText etUnit = alertLayout.findViewById(R.id.et_units);
                                EditText etDosage = alertLayout.findViewById(R.id.et_dosage);

                                MedicineFake med = new MedicineFake(
                                        etName.getText().toString(),
                                        Double.parseDouble(etDosage.getText().toString()),
                                        etUnit.getText().toString(),
                                        sel
                                );
                                meds.add(med);
                                Collections.sort(meds);
                                future.notifyDataSetChanged();

                                Log.d("fuck", "onClick: " + sel.get(0));
                            }
                        });
                alert.create().show();
                return false;
            }
        });
    }

    public class FutureAdapter extends RecyclerView.Adapter<BasicCard> {

        private Context ctx;
        private ArrayList<MedicineFake> data;

        public FutureAdapter(Context ctx, ArrayList<MedicineFake> data) {
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
            holder.tvSubtext.setText(df.format(item.getNext().getTime()));
            Glide.with(ctx).load(R.mipmap.ic_meds).into(holder.icon);
        }

        @Override
        public int getItemCount() {
            if (data.size() > 3)
                return 3;
            return data.size();
        }
    }


    public class PastAdapter extends RecyclerView.Adapter<BasicCard> {

        private Context ctx;
        private ArrayList<String[]> data;

        public PastAdapter(Context ctx, ArrayList<String[]> data) {
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
            String[] item = data.get(position);
            holder.tvHeader.setText(item[0]);
            holder.tvSubtext.setText(item[1]);
            if (position % 4 == 1) {
                holder.card.setBackgroundColor(getResources().getColor(R.color.warning));
                holder.tvSubtext.setTextColor(getResources().getColor(R.color.subtextDark));
            }
            Glide.with(ctx).load(R.mipmap.ic_meds).into(holder.icon);
        }

        @Override
        public int getItemCount() {
            return data.size();
        }
    }

    public interface TriggerListener {
        void triggerNotification(MedicineFake med);
    }
}
