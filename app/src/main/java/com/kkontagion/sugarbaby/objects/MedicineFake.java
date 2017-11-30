package com.kkontagion.sugarbaby.objects;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by kkontagion on 029 29/11/2017.
 */

public class MedicineFake implements Comparable<MedicineFake> {
    private String name, unit;
    private double dosage;
    private ArrayList<Integer> regularity; // FOR REMINDERS e.g. every 6am, 9pm

    public MedicineFake(String name, double dosage, String unit, ArrayList<Integer> regularity) {
        this.name = name;
        this.unit = unit;
        this.dosage = dosage;
        this.regularity = regularity;
    }

    public MedicineFake(String name, String unit, double dosage) {
        this.name = name;
        this.unit = unit;
        this.dosage = dosage;
    }

    @Override
    public String toString() {
        if (dosage == Math.floor(dosage))
            return name + ": " + ((int) dosage) + unit;
        return name + ": " + dosage + unit;
    }

    public Calendar getNext() {
        Calendar now = Calendar.getInstance();
        Collections.sort(regularity);

        now.set(Calendar.MINUTE, 0);

        if (now.get(Calendar.HOUR_OF_DAY) >= regularity.get(regularity.size() - 1)) { // no more for today.
            now.set(Calendar.DAY_OF_MONTH, now.get(Calendar.DAY_OF_MONTH) + 1);
            now.set(Calendar.HOUR_OF_DAY, regularity.get(0));
            return now;
        }

        for (int h : regularity) {
            if (now.get(Calendar.HOUR_OF_DAY) < h) {
                now.set(Calendar.HOUR_OF_DAY, h);
                break;
            }
        }

        return now;
    }

    public ArrayList<Integer> getRegularity() {
        return regularity;
    }

    public void setRegularity(ArrayList<Integer> regularity) {
        this.regularity = regularity;
    }

    public String getName() {
        return name;
    }

    public String getUnit() {
        return unit;
    }

    public double getDosage() {
        return dosage;
    }

    @Override
    public int compareTo(MedicineFake other) {
        if (getNext().before(other.getNext()))
            return -1;
        if (getNext().after(other.getNext()))
            return 1;
        return 0;
    }
}
