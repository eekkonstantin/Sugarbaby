package com.kkontagion.sugarbaby.objects;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by kkontagion on 028 28/11/2017.
 */

public class Meal {
    private Calendar date;
    private int carbs, cholesterol, fats;

    SimpleDateFormat df = new SimpleDateFormat("dd MMM yyyy hh:mmaa");

    public Meal(int dayOfMonth, int hour, int min) {
        date = Calendar.getInstance();
        date.set(2017, Calendar.NOVEMBER, dayOfMonth, hour, min);
        date.set(Calendar.AM_PM, (date.get(Calendar.HOUR_OF_DAY) < 12) ? Calendar.AM : Calendar.PM);
    }

    public String getDate() {
        return df.format(date.getTime());
    }
}
