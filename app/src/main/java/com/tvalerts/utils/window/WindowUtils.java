package com.tvalerts.utils.window;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

/**
 * Utils class for Window related operations.
 */
public class WindowUtils {

    /**
     * Returns the density pixels for a given metric, passed as a parameter.
     * @param context the context of the activity calling, to obtain the WindowManager.
     * @param metric the metric to calculate.
     * @return the density pixels for that given metric, or 0 if there is any problem.
     */
    public static float getElementMetricInDp(Context context, int metric) {
        WindowManager windowManager = (WindowManager) context.getApplicationContext()
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        if (windowManager != null) {
            windowManager.getDefaultDisplay().getMetrics(displayMetrics);
            return  metric / displayMetrics.density;
        } else {
            return 0;
        }
    }
}
