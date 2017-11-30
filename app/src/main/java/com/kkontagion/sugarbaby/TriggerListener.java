package com.kkontagion.sugarbaby;

import com.kkontagion.sugarbaby.objects.MedicineFake;

/**
 * Created by kkontagion on 001 1/12/2017.
 */

public interface TriggerListener {
    void triggerPlsMedicate(MedicineFake med);
    void triggerHowYouDoin();
    void triggerPlsSeeDoc(String[] details);
}
