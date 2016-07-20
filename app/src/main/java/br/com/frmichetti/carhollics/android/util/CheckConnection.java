package br.com.frmichetti.carhollics.android.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

/**
 * Created by Felipe on 19/07/2016.
 */
public class CheckConnection {
/*
    public static boolean isNetworkAvailableNew(Context context) {

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (cm == null) {

            return false;

        } else {

            //cm.getAllNetworks();


            return true;
        }


    }
*/
    public static boolean isNetworkAvailable(Context context) {

        try {

            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

            if (cm == null) {

                return false;

            } else {

                NetworkInfo[] infos = cm.getAllNetworkInfo();

                if (infos != null) {

                    for (int i = 0; i < infos.length; i++) {
                        if (infos[i].getState() == NetworkInfo.State.CONNECTED) {
                            Log.d("DEBUG-CHECK-CONN",infos[i].getDetailedState().toString());
                        }
                    }


                }
            }

        } catch (SecurityException e) {

            Log.d("DEBUG-CHECK-CONN",e.toString());

        }

        return true;

    }
}
