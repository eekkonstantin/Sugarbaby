package com.kkontagion.sugarbaby.objects;

/**
 * Created by kkontagion on 028 28/11/2017.
 */

public class Food {
    FoodType type;
    String desc;
    double calories, carbs;

    public Food(FoodType type, String desc, double calories, double carbs) {
        this.type = type;
        this.desc = desc;
        this.calories = calories;
        this.carbs = carbs;
    }

    public FoodType getType() {
        return type;
    }

    public String getDesc() {
        return desc;
    }

    public double getCalories() {
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
}
