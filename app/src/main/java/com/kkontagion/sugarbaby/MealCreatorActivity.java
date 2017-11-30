package com.kkontagion.sugarbaby;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.DatePicker;
import android.widget.TimePicker;

import com.kkontagion.sugarbaby.adapters.AutocompleteAdapter;
import com.kkontagion.sugarbaby.adapters.FoodAdapter;
import com.kkontagion.sugarbaby.adapters.FoodTypeSpinnerAdapter;
import com.kkontagion.sugarbaby.objects.Food;
import com.kkontagion.sugarbaby.objects.FoodType;
import com.kkontagion.sugarbaby.objects.Meal;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class MealCreatorActivity extends AppCompatActivity {
    public static final String TAG = "MealCreatorActivity";
    public static final int CREATE_MEAL = 69;

    RecyclerView rv;
    TextView tvTime, tvTotal;
    ImageButton btEdit;
    Button btSubmit;
    AutoCompleteTextView tvComplete;

    FoodAdapter foodAdapter;
    AutocompleteAdapter completeAdapter;

    private double cals = 0, carbs = 0;
    private Calendar time;
    private ArrayList<Food> autoarray, foodarray;
    private static SimpleDateFormat df = new SimpleDateFormat("dd MMM yyyy hh:mmaa");
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private TimePickerDialog.OnTimeSetListener mTimeSetListener;
    Food f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal_creator);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        time = Calendar.getInstance();

        tvTime = findViewById(R.id.tv_time);
        tvTotal = findViewById(R.id.tv_total);
        tvComplete = findViewById(R.id.tv_auto);
        btEdit = findViewById(R.id.bt_edit);
        btSubmit = findViewById(R.id.bt_submit);
        rv = findViewById(R.id.rv);

        initArrays();
        foodAdapter = new FoodAdapter(this, foodarray);
        rv.setAdapter(foodAdapter);
        completeAdapter = new AutocompleteAdapter(this, R.layout.item_autocomplete, autoarray);
        Log.d(TAG, "onCreate: completeAdapter: " + completeAdapter.getCount());
        tvComplete.setAdapter(completeAdapter);
        tvComplete.setThreshold(1);

        tvTime.setText(df.format(time.getTime()));
        tvTotal.setText(getString(R.string.food_meal_totals, cals, carbs));

        setupActions();
    }

    private void initArrays() {
        autoarray = new ArrayList<>();
        autoarray.add(new Food(FoodType.VEGETABLE, "Steamed Carrots with Garlic-Ginger Butter", 69, 10.3));
        autoarray.add(new Food(FoodType.BURGERS, "Black bean burger", 182, 15.6));
        autoarray.add(new Food(FoodType.SWEETS, "Frozen yoghurt", 100, 18.6));

        foodarray = new ArrayList<>();
//        foodarray.add(new Food(FoodType.MEAT, "Tofu bites", 51, 1.4));
//        foodarray.add(new Food(FoodType.VEGETABLE, "Steamed Carrots with Garlic-Ginger Butter", 69, 10.3));
//        foodarray.add(new Food(FoodType.BURGERS, "Black bean burger", 182, 15.6));
//        foodarray.add(new Food(FoodType.SWEETS, "Frozen yoghurt", 100, 18.6));
    }

    private void setupActions() {
        tvTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO datepicker dialog, followed by time picker dialog.
                // On complete (both selected), update tvTime.setText(result) using the same format as `df` above.
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

           time.set(i, i1, i2);

           int hour = time.get(Calendar.HOUR_OF_DAY);
           int minutes = time.get(Calendar.MINUTE);

           TimePickerDialog dialog = new TimePickerDialog(MealCreatorActivity.this, R.style.TimePickerTheme, mTimeSetListener,hour,minutes,false);
           dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
           dialog.show();
          }
        };

       mTimeSetListener = new TimePickerDialog.OnTimeSetListener(){
         @Override
         public void onTimeSet(TimePicker timePicker, int i , int i1){
           time.set(Calendar.HOUR_OF_DAY,i);
           time.set(Calendar.MINUTE,i1);
           tvTime.setText(df.format(time.getTime()));
           }
        };

        tvComplete.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                f = autoarray.get(i);
                callEdit();
            }
        });

        btEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callEdit();
            }
        });

        btSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // generate meal and send back to fragment
                Meal meal = new Meal(time);
                meal.setFood(foodAdapter.getItems());
                Intent i = new Intent();
                i.putExtra("meal", meal);
                setResult(RESULT_OK, new Intent().putExtra(TAG, meal));
                finish();
            }
        });
    }

    private void callEdit() {
        if (tvComplete.getText().length() == 0)
            return;

        final View alertLayout = getLayoutInflater().inflate(R.layout.dialog_food_confirm, null);

        String complete = tvComplete.getText().toString();
        if (f == null || !completeAdapter.contains(complete)) // new food
            f = new Food(complete);

        ((TextView) alertLayout.findViewById(R.id.tv_desc)).setText(f.getDesc());
        ((EditText) alertLayout.findViewById(R.id.et_carbs)).setText(f.getCarbs() + "");
        ((EditText) alertLayout.findViewById(R.id.et_cals)).setText(f.getCalories() + "");
        Spinner sp = alertLayout.findViewById(R.id.spinner);
        final FoodTypeSpinnerAdapter ftAdapter = new FoodTypeSpinnerAdapter(this);
        sp.setAdapter(ftAdapter);
        sp.setSelection(f.getType().ordinal());
        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                adapterView.setTag(ftAdapter.getItem(i));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        // dialog to confirm carbs and cals and food type
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle(R.string.food_meal_h1).setView(alertLayout)
                .setPositiveButton(R.string.dialog_pos, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface di, int i) {
                        f.setCalories( Integer.parseInt(((TextView) alertLayout.findViewById(R.id.et_cals)).getText().toString()) );
                        f.setCarbs( Double.parseDouble(((TextView) alertLayout.findViewById(R.id.et_carbs)).getText().toString()) );
                        f.setType((FoodType) alertLayout.findViewById(R.id.spinner).getTag());
                        foodarray.add(0, f);
                        foodAdapter.notifyDataSetChanged();
                        cals += f.getCalories();
                        carbs += f.getCarbs();
                        tvTotal.setText(getString(R.string.food_meal_totals, carbs, cals));

                        // Add into "database"
                        completeAdapter.add(f);
                        tvComplete.setText("");
                    }
                });
        alert.create().show();
    }
}
