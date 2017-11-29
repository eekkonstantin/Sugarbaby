package com.kkontagion.sugarbaby;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.MultiAutoCompleteTextView;
import android.widget.TextView;
import android.widget.TimePicker;

import com.kkontagion.sugarbaby.adapters.AutocompleteAdapter;
import com.kkontagion.sugarbaby.adapters.FoodAdapter;
import com.kkontagion.sugarbaby.objects.Food;
import com.kkontagion.sugarbaby.objects.FoodType;
import com.kkontagion.sugarbaby.objects.Meal;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class MealCreatorActivity extends AppCompatActivity {

    RecyclerView rv;
    TextView tvTime, tvTotal;
    MultiAutoCompleteTextView tvComplete;

    FoodAdapter foodAdapter;
    AutocompleteAdapter completeAdapter;

    private Meal meal;
    private Calendar time;
    private ArrayList<Food> autoarray;
    private static SimpleDateFormat df = new SimpleDateFormat("dd MMM yyyy hh:mmaa");
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private TimePickerDialog.OnTimeSetListener mTimeSetListener;

    int day,month,year,hour,minute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal_creator);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        time = Calendar.getInstance();
        meal = new Meal(Calendar.getInstance());

        tvTime = findViewById(R.id.tv_time);
        tvTotal = findViewById(R.id.tv_total);
        tvComplete = findViewById(R.id.tv_auto);
        rv = findViewById(R.id.rv);

        foodAdapter = new FoodAdapter(this);
        rv.setAdapter(foodAdapter);
        initAuto();
        completeAdapter = new AutocompleteAdapter(this, R.layout.item_autocomplete, autoarray);
        tvComplete.setAdapter(completeAdapter);
        tvComplete.setThreshold(1);

        tvComplete.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());

        tvTime.setText(df.format(time.getTime()));
        tvTotal.setText(getString(R.string.food_meal_totals, meal.getCarbs(), meal.getCalories()));

        setupActions();
    }

    private void initAuto() {
        autoarray = new ArrayList<>();
        autoarray.add(new Food(FoodType.VEGETABLE, "Steamed Carrots with Garlic-Ginger Butter", 69, 10.3));
        autoarray.add(new Food(FoodType.BURGERS, "Black bean burger", 182, 15.6));
        autoarray.add(new Food(FoodType.SWEETS, "Frozen yoghurt", 100, 18.6));
    }

    private void setupActions() {
        tvTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO datepicker dialog, followed by time picker dialog.
                // On complete (both selected), update tvTime.setText(result) using the same format as `df` above.
                // Result should update Calendar time variable
                DatePickerDialog dialog = new DatePickerDialog(
                        MealCreatorActivity.this,
                        R.style.TimePickerTheme,
                        mDateSetListener,
                        time.get(Calendar.YEAR),time.get(Calendar.MONTH),time.get(Calendar.DAY_OF_MONTH));
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener(){
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1,int i2){
                year = i;
                month = i1;
                day = i2;

                time.set(i, i1, i2);

                hour = time.get(Calendar.HOUR_OF_DAY);
                minute = time.get(Calendar.MINUTE);

                TimePickerDialog dialog = new TimePickerDialog(MealCreatorActivity.this,mTimeSetListener,hour,minute,false);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }


        };

        mTimeSetListener = new TimePickerDialog.OnTimeSetListener(){
            @Override
            public void onTimeSet(TimePicker timePicker, int i , int i1){
                hour = i;
                minute = i1;
                time.set(Calendar.HOUR_OF_DAY,i);
                time.set(Calendar.MINUTE,i1);
//                Log.d("penis", "onTimeSet: " + time.toString());
                tvTime.setText(df.format(time.getTime()));
            }
        };



    }
}
