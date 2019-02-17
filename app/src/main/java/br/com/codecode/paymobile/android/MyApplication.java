/**
 * @author Felipe Rodrigues Michetti
 * @see http://portfolio-frmichetti.rhcloud.com
 * @see http://www.codecode.com.br
 * @see mailto:frmichetti@gmail.com
 */
package br.com.codecode.paymobile.android;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.multidex.MultiDexApplication;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import br.com.codecode.paymobile.android.model.Token;
import br.com.codecode.paymobile.android.model.compatibility.Customer;
import br.com.codecode.paymobile.android.tasks.AsyncResponse;
import br.com.codecode.paymobile.android.tasks.TaskLogin;
import br.com.codecode.paymobile.android.tasks.TaskRequestCustomerData;
import br.com.codecode.paymobile.android.view.activity.MainActivity;
import br.com.codecode.paymobile.android.view.activity.login.LoginActivity;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;
import static br.com.codecode.paymobile.android.model.IntentKeys.CUSTOMER_BUNDLE_KEY;

public class MyApplication extends MultiDexApplication {

    private static MyApplication myApplication;
    private static Token sessionToken;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference firebaseDatabase;
    private FirebaseDatabase firebaseInstance;
    private ValueEventListener valueEventListener;
    private Context context;


    public static synchronized MyApplication getInstance() {
        return myApplication;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        myApplication = this;
        context = this;

        firebaseAuth = FirebaseAuth.getInstance();

        firebaseInstance = FirebaseDatabase.getInstance();

        // get reference to 'users' node
        firebaseDatabase = firebaseInstance.getReference("white_label");

        //sessionToken = new Token();

        // store app title to 'app_title' node
        // firebaseInstance.getReference("app_title").setValue("Pay Mobile");
        // firebaseInstance.getReference("primary_color").setValue("#673AB7");
        // firebaseInstance.getReference("primary_dark_color").setValue("#512DA8");
        // firebaseInstance.getReference("accent_color").setValue("#FF4081");
        // firebaseInstance.getReference("app_theme").setValue("#FF4081");

        if (firebaseAuth.getCurrentUser() != null) {
            new TaskLogin(context, new AsyncResponse<String>() {
                @Override
                public void onSuccess(String token) {
                    if (token != null) {
                        new TaskRequestCustomerData(context, false, new AsyncResponse<Customer>() {
                            @Override
                            public void onSuccess(Customer customer) {
                                if (customer != null) {
                                    startActivity(new Intent(context, MainActivity.class).setFlags(FLAG_ACTIVITY_NEW_TASK)
                                            .putExtra(CUSTOMER_BUNDLE_KEY, customer));
                                }
                            }

                            @Override
                            public void onFails(Exception e) {
                                Toast.makeText(context, e.toString(), Toast.LENGTH_LONG).show();
                                Log.d("Error", e.toString());
                                sessionToken = null;
                                startActivity(new Intent(context, LoginActivity.class));

                            }
                        }).execute(token);

                    } else {

                        Toast.makeText(context, getString(R.string.could_not_authorize), Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFails(Exception e) {
                    //user was created on firebase but not in paymobile
                    firebaseAuth.getCurrentUser().delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Log.d("Success", "user was deleted");
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d("Error", e.toString());
                        }
                    });
                    Toast.makeText(context, e.toString(), Toast.LENGTH_LONG).show();
                    Log.d("Error", e.toString());
                }
            }).execute(firebaseAuth.getCurrentUser().getUid());
        }

    }

    public void configureFirebase(ValueEventListener valueEventListener) {
        firebaseInstance.getReference("app_title").addValueEventListener(valueEventListener);
        firebaseInstance.getReference("primary_color").addValueEventListener(valueEventListener);
        firebaseInstance.getReference("primary_dark_color").addValueEventListener(valueEventListener);
        firebaseInstance.getReference("accent_color").addValueEventListener(valueEventListener);
        firebaseInstance.getReference("app_theme").addValueEventListener(valueEventListener);
    }

    public static synchronized Token getSessionToken() {
        return MyApplication.sessionToken;
    }

    public FirebaseAuth getFirebaseAuth() {
        return firebaseAuth;
    }

    public static synchronized void setSessionToken(Token sessionToken) {
        MyApplication.sessionToken = sessionToken;
    }

    public void setValueEventListener(ValueEventListener valueEventListener) {
        this.valueEventListener = valueEventListener;
    }

}
