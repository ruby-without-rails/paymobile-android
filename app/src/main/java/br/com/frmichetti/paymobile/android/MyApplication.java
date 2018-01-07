/**
 * @author Felipe Rodrigues Michetti
 * @see http://portfolio-frmichetti.rhcloud.com
 * @see http://www.codecode.com.br
 * @see mailto:frmichetti@gmail.com
 */
package br.com.frmichetti.paymobile.android;

import android.support.multidex.MultiDexApplication;

import br.com.frmichetti.paymobile.android.model.Token;

public class MyApplication extends MultiDexApplication {

    private static MyApplication myApplication;
    private static Token sessionToken;

    public static synchronized MyApplication getInstance() {
        return myApplication;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        myApplication = this;
    }

    public static synchronized Token getSessionToken() {
        return sessionToken;
    }

    public static synchronized void setSessionToken(Token sessionToken) {
        MyApplication.sessionToken = sessionToken;
    }
}
