/**
 * @author Felipe Rodrigues Michetti
 * @see http://portfolio-frmichetti.rhcloud.com
 * @see http://www.codecode.com.br
 * @see mailto:frmichetti@gmail.com
 */
package br.com.frmichetti.carhollics.android;

import android.support.multidex.MultiDexApplication;

import br.com.frmichetti.carhollics.android.util.ConnectivityReceiver;

public class MyApplication extends MultiDexApplication {

    private static MyApplication myApplication;

    public static synchronized MyApplication getInstance() {

        return myApplication;
    }

    @Override
    public void onCreate() {

        super.onCreate();

        myApplication = this;
    }

    public void setConnectivityListener(ConnectivityReceiver.ConnectivityReceiverListener listener) {

        ConnectivityReceiver.connectivityReceiverListener = listener;
    }

}
