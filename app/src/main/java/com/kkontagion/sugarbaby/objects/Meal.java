package com.kkontagion.sugarbaby.objects;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by kkontagion on 028 28/11/2017.
 */

public class Meal implements Serializable {
    private Calendar date;

    private ArrayList<Food> ate;
    private double carbs = 0;
    private int cals = 0;

    private static SimpleDateFormat df = new SimpleDateFormat("dd MMM yyyy hh:mmaa");

    // TESTING ONLY - random generation - will not have food data
    public Meal(int dayOfMonth, int hour, int min) {
        date = Calendar.getInstance();
        date.set(2017, Calendar.NOVEMBER, dayOfMonth, hour, min);
        date.set(Calendar.AM_PM, (date.get(Calendar.HOUR_OF_DAY) < 12) ? Calendar.AM : Calendar.PM);
    }

    public Meal(Calendar date) {
        this.date = date;

        init();
    }

    private void init() {
        ate = new ArrayList<>();
    }


    public String getDate() {
        return df.format(date.getTime());
    }

    public Calendar getCalendarDate() {
        return date;
    }

    public ArrayList<Food> getAte() {
        return ate;
    }

    public int getCalories() {
        cals = 0;
        for (Food f : ate)
            cals += f.getCalories();
        return cals;
    }

    public double getCarbs() {
        carbs = 0;
        for (Food f : ate)
            carbs += f.getCarbs();
        return carbs;
    }

    public String getNutrition() {
        return getCarbs() + "g carbohydrates, " + getCalories() + "cal";
    }

    public void setFood(ArrayList<Food> food) {
        this.ate = food;
    }
}
