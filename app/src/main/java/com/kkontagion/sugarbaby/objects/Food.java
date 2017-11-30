package com.kkontagion.sugarbaby.objects;

import android.text.TextUtils;

import com.kkontagion.sugarbaby.Helper;

import java.io.Serializable;

/**
 * Created by kkontagion on 028 28/11/2017.
 */

public class Food implements Serializable {
    FoodType type;
    String desc;
    int calories;
    double carbs;

    public Food(FoodType type, String desc, int calories, double carbs) {
        this.type = type;
        this.desc = Helper.capitalize(desc);
        this.calories = calories;
        this.carbs = carbs;
    }

    public Food(String desc) {
        this.desc = Helper.capitalize(desc);
        this.carbs = 0;
        this.calories = 0;
        this.type = FoodType.VEGETABLE;
    }

    public void setType(FoodType type) {
        this.type = type;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public void setCarbs(double carbs) {
        this.carbs = carbs;
    }

    public FoodType getType() {
        return type;
    }

    public String getDesc() {
        return desc;
    }

    public int getCalories() {
        return calories;
    }

    public double getCarbs() {
        return carbs;
    }

    public String getNutrition() {
        return carbs + "g carbohydrates, " + calories + "cal";
    }

    @Override
    public String toString() {
        return desc;
    }

    public boolean filterContains(String s) {
        String[] spl = desc.split(" ");
        for (String d : spl) {
            if (d.toLowerCase().startsWith(s.toLowerCase()))
                return true;
        }
        return false;
    }
}
