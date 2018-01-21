package br.com.frmichetti.paymobile.android.util;

import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;

/**
 * Created by felipe on 21/01/18.
 */

public class MyResources extends Resources {

    public MyResources(Resources original) {
        super(original.getAssets(), original.getDisplayMetrics(), original.getConfiguration());
    }

    @Override
    public int getColor(int id) throws NotFoundException {
        return getColor(id, null);
    }


    @Override
    public int getColor(int id, Theme theme) throws Resources.NotFoundException {
        switch (getResourceEntryName(id)) {
            case "your_special_color":
                // You can change the return value to an instance field that loads from SharedPreferences.
                return Color.RED; // used as an example. Change as needed.
            default:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    return super.getColor(id, theme);
                }
                return super.getColor(id);
        }
    }
}