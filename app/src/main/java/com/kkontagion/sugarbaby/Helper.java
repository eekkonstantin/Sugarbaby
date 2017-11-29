package com.kkontagion.sugarbaby;

import java.util.Arrays;

/**
 * Created by kkontagion on 029 29/11/2017.
 */

public class Helper {
    private static String nocaps = "a an and am or then with";

    public static String capitalize(String s) {
        s = s.toLowerCase();
        String out = "";
        for (String w : s.split(" ")) {
            // if is a barred word and not first word
            if (nocaps.contains(w) && s.indexOf(w) != 0)
                out += w + " ";
            else
                out += w.substring(0,1).toUpperCase() + w.substring(1).toLowerCase() + " ";
        }
        return out.substring(0, out.length() - 1);
    }
}
