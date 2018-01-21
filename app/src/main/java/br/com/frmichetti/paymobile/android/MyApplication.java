/**
 * @author Felipe Rodrigues Michetti
 * @see http://portfolio-frmichetti.rhcloud.com
 * @see http://www.codecode.com.br
 * @see mailto:frmichetti@gmail.com
 */
package br.com.frmichetti.paymobile.android;

import android.support.multidex.MultiDexApplication;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import br.com.frmichetti.paymobile.android.model.Token;

public class MyApplication extends MultiDexApplication {

    private static MyApplication myApplication;
    private static Token sessionToken;
    private DatabaseReference firebaseDatabase;
    private FirebaseDatabase firebaseInstance;
    private ValueEventListener valueEventListener;

    public void setValueEventListener(ValueEventListener valueEventListener) {
        this.valueEventListener = valueEventListener;
    }

    public static synchronized MyApplication getInstance() {
        return myApplication;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        myApplication = this;

        firebaseInstance = FirebaseDatabase.getInstance();

        // get reference to 'users' node
        firebaseDatabase = firebaseInstance.getReference("white_label");

        // store app title to 'app_title' node
        // firebaseInstance.getReference("app_title").setValue("Pay Mobile");
        // firebaseInstance.getReference("primary_color").setValue("#673AB7");
        // firebaseInstance.getReference("primary_dark_color").setValue("#512DA8");
        // firebaseInstance.getReference("accent_color").setValue("#FF4081");

    }

    public void configureFirebase(ValueEventListener valueEventListener) {
        firebaseInstance.getReference("app_title").addValueEventListener(valueEventListener);
        firebaseInstance.getReference("primary_color").addValueEventListener(valueEventListener);
        firebaseInstance.getReference("primary_dark_color").addValueEventListener(valueEventListener);
        firebaseInstance.getReference("accent_color").addValueEventListener(valueEventListener);
    }

    public static synchronized Token getSessionToken() {
        return sessionToken;
    }

    public static synchronized void setSessionToken(Token sessionToken) {
        MyApplication.sessionToken = sessionToken;
    }
}
