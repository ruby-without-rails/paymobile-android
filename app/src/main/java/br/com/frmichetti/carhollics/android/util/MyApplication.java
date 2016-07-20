package br.com.frmichetti.carhollics.android.util;

import android.app.Application;
import android.content.Context;

public class MyApplication extends Application {

    private static MyApplication mInstance;

    @Override
    public void onCreate() {

        super.onCreate();

        mInstance = this;
    }

    public static synchronized MyApplication getInstance() {

        return mInstance;
    }

    public void setConnectivityListener(ConnectivityReceiver.ConnectivityReceiverListener listener) {

        ConnectivityReceiver.connectivityReceiverListener = listener;
    }

}
