package com.tvalerts.utils.glide;

import android.content.Context;

/**
 * Utils class for Window related operations.
 */
public class GlideUtils {

    /**
     * Converts from Density Independent Pixels to Pixels.
     * @param context the context of the activity calling the method.
     * @param dp the density pixel metric to convert.
     * @return the calculated pixels from the given density pixels.
     */
    public static int dip2px(Context context, float dp) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }
}

