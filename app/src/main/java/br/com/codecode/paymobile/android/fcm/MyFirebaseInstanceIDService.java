/**
 * @author Felipe Rodrigues Michetti
 * @see http://portfolio-frmichetti.rhcloud.com
 * @see http://www.codecode.com.br
 * @see mailto:frmichetti@gmail.com
 */
package br.com.codecode.paymobile.android.fcm;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {

    private static String TAG = "Regist - TOKEN";

    @Override
    public void onTokenRefresh() {

        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();

        Log.i(TAG, "[Refreshed token] \n" + refreshedToken);

        // TODO: Implement this method to send any registration to your app's servers.
        Log.i(TAG + "[TOKEN] \n", refreshedToken);

        //Update where FIREBASE ID

    }
}
