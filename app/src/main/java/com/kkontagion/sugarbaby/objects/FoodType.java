package com.kkontagion.sugarbaby.objects;

import com.kkontagion.sugarbaby.R;

/**
 * Created by kkontagion on 028 28/11/2017.
 */

public enum FoodType {
    CARBS("carbs", R.mipmap.ic_carbs),
    DRINK("drink", R.mipmap.ic_drinks),
    BURGERS("burgers", R.mipmap.ic_fastfood),
    FISH("fish", R.mipmap.ic_fish),
    FRUIT("fruit", R.mipmap.ic_food),
    MEAT("meat", R.mipmap.ic_meat),
    SNACK("snack", R.mipmap.ic_snacks),
    SOUP("soup", R.mipmap.ic_soup),
    SWEETS("sweet treat", R.mipmap.ic_sweets),
    VEGETABLE("vegetable", R.mipmap.ic_veg);



    String name;
    int img;

    FoodType(String name, int img) {
        this.name = name;
        this.img = img;
    }

    @Override
    public String toString() {
        return name;
    }

    public int getImg() {
        return img;
    }
}
