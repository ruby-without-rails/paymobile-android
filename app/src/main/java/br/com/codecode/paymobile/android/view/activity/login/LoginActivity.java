/**
 * @author Felipe Rodrigues Michetti
 * @see http://portfolio-frmichetti.rhcloud.com
 * @see http://www.codecode.com.br
 * @see mailto:frmichetti@gmail.com
 */
package br.com.codecode.paymobile.android.view.activity.login;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import br.com.codecode.paymobile.android.MyApplication;
import br.com.codecode.paymobile.android.R;
import br.com.codecode.paymobile.android.tasks.AsyncResponse;
import br.com.codecode.paymobile.android.tasks.TaskLogin;
import br.com.codecode.paymobile.android.model.compatibility.Customer;
import br.com.codecode.paymobile.android.tasks.TaskRequestCustomerData;
import br.com.codecode.paymobile.android.util.ConnectivityReceiver;
import br.com.codecode.paymobile.android.util.MyResources;
import br.com.codecode.paymobile.android.view.activity.CustomerActivity;
import br.com.codecode.paymobile.android.view.activity.MainActivity;
import br.com.codecode.paymobile.android.view.activity.MyPattern;

import static br.com.codecode.paymobile.android.model.IntentKeys.CUSTOMER_BUNDLE_KEY;

public class LoginActivity extends AppCompatActivity implements MyPattern,
        ConnectivityReceiver.ConnectivityReceiverListener, ValueEventListener {
    private Context context;
    private ActionBar actionBar;
    private EditText editTextEmail, editTextPassword;
    private ProgressBar progressBar;
    private Button btnSignup, btnLogin, btnReset;
    private String TAG = "FireBase";
    private MyResources resources;
    private String oldPrimaryColor, oldPrimaryDarkColor, oldAccentColor, primaryColor, primaryDarkColor, accentColor, theme = "defaul";
    private FirebaseAuth auth;
    private ScrollView scrollView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        reconfigureTheme(theme);

        setContentView(R.layout.activity_login);

        doCastComponents();

        doCreateListeners();

         MyApplication.getInstance().setValueEventListener(this);

         MyApplication.getInstance().configureFirebase(this);

        auth = MyApplication.getInstance().getFirebaseAuth();

    }


    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        doConfigure();

        doCheckConnection();
    }

    @Override
    public void doCastComponents() {
        Toolbar toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        editTextEmail = findViewById(R.id.edt_email);

        editTextPassword = findViewById(R.id.edt_password);

        progressBar = findViewById(R.id.progressBar);

        btnSignup = findViewById(R.id.btn_signup);

        btnLogin = findViewById(R.id.btn_login);

        btnReset = findViewById(R.id.btn_reset_password);

        if(MyApplication.getInstance().getFirebaseAuth().getCurrentUser() != null){
            progressBar = findViewById(R.id.progressBar);
            scrollView = findViewById(R.id.login_scroll);
            scrollView.setVisibility(View.VISIBLE);
            scrollView.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void doCreateListeners() {
        btnSignup.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                startActivity(new Intent(context, SignupActivity.class));
            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                startActivity(new Intent(context, ResetPasswordActivity.class));
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                } catch (Exception e) {

                }

                String email = editTextEmail.getText().toString();

                final String password = editTextPassword.getText().toString();

                if (TextUtils.isEmpty(email)) {

                    Toast.makeText(context, getString(R.string.enter_email_address), Toast.LENGTH_SHORT).show();

                    return;
                }

                if (TextUtils.isEmpty(password)) {

                    Toast.makeText(context, getString(R.string.enter_password), Toast.LENGTH_SHORT).show();

                    return;
                }


                if (doCheckConnection()) {

                    progressBar.setVisibility(View.VISIBLE);

                    //authenticate firebaseUser
                    auth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {

                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {

                                    // If sign in fails, display a message to the firebaseUser. If sign in succeeds
                                    // the firebaseAuth state listener will be notified and logic to handle the
                                    // signed in firebaseUser can be handled in the listener.
                                    progressBar.setVisibility(View.GONE);

                                    if (!task.isSuccessful()) {

                                        // there was an error
                                        if (password.length() < 6) {

                                            editTextPassword.setError(getString(R.string.minimum_password));

                                        } else {

                                            Toast.makeText(context, getString(R.string.auth_failed), Toast.LENGTH_LONG).show();

                                            Log.d("DEBUG-LOGIN", String.valueOf(R.string.auth_failed));
                                        }

                                    } else {
                                        //Login Firebase OK
                                        new TaskLogin(context, new AsyncResponse<String>() {
                                            @Override
                                            public void onSuccess(String token) {
                                                if (token != null) {
                                                    new TaskRequestCustomerData(context, false, new AsyncResponse<Customer>() {
                                                        @Override
                                                        public void onSuccess(Customer customer) {
                                                            if (customer != null) {
                                                                startActivity(new Intent(context, MainActivity.class)
                                                                        .putExtra(CUSTOMER_BUNDLE_KEY, customer));

                                                                finish();
                                                            }else{
                                                                startActivity(new Intent(context, CustomerActivity.class));
                                                                finish();
                                                            }
                                                        }

                                                        @Override
                                                        public void onFails(Exception e) {
                                                            Toast.makeText(context, e.toString(), Toast.LENGTH_LONG).show();
                                                            Log.d("Error", e.toString());
                                                        }
                                                    }).execute(token);

                                                } else {

                                                    Toast.makeText(context, getString(R.string.token_expired), Toast.LENGTH_LONG).show();
                                                }
                                            }

                                            @Override
                                            public void onFails(Exception e) {
                                                // user logged in firebase but without customer in paymobile
                                                startActivity(new Intent(context, CustomerActivity.class));
                                                finish();
                                            }
                                        }).execute(auth.getCurrentUser().getUid());
                                    }
                                }
                            }).addOnFailureListener(LoginActivity.this, new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(context, getString(R.string.user_or_password_incorrect), Toast.LENGTH_LONG).show();
                        }

                    });
                } else {
                    showSnack(doCheckConnection());
                }
            }
        });
    }

    @Override
    public void setupToolBar() {

    }

    @Override
    public void doConfigure() {
        context = this;

        actionBar = getSupportActionBar();

        actionBar.setTitle(R.string.app_name);

        actionBar.setSubtitle(R.string.action_sign_in);

        actionBar.setDisplayHomeAsUpEnabled(false);

    }

    @Override
    public void doChangeActivity(Context context, Class clazz) {

    }

    // Method to manually check connection status
    private boolean doCheckConnection() {

        return ConnectivityReceiver.isConnected(context);
    }

    // Showing the status in Snackbar
    private void showSnack(boolean isConnected) {
        String message;

        int color;

        if (isConnected) {

            message = getString(R.string.connected_on_internet);

            color = Color.GREEN;

        } else {

            message = getString(R.string.not_connected_on_internet);

            color = Color.RED;

        }

        Snackbar snackbar = Snackbar
                .make(findViewById(R.id.coordinator_layout_login), message, Snackbar.LENGTH_LONG);

        View sbView = snackbar.getView();

        TextView textView = sbView.findViewById(android.support.design.R.id.snackbar_text);

        textView.setTextColor(color);

        snackbar.show();

    }

    @Override
    protected void onResume() {
        super.onResume();

        // register connection status listener
        this.setConnectivityListener(this);
    }

    /**
     * Callback will be triggered when there is change in
     * network connection
     */
    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        showSnack(isConnected);
    }


    public void setConnectivityListener(ConnectivityReceiver.ConnectivityReceiverListener listener) {
        ConnectivityReceiver.connectivityReceiverListener = listener;
    }

    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
        Log.e(TAG, "App title updated");
        Class<String> stringClass = String.class;

        // Snackbar.make(findViewById(R.id.coordinator_layout_login), "DB values changed", Snackbar.LENGTH_SHORT).show();

        switch (dataSnapshot.getKey()) {
            case "app_title": {
                String appTitle = dataSnapshot.getValue(stringClass);
                getSupportActionBar().setTitle(appTitle);
                break;
            }

            case "primary_color": {
                primaryColor = dataSnapshot.getValue(stringClass);
                break;
            }

            case "primary_dark_color": {
                primaryDarkColor = dataSnapshot.getValue(stringClass);
                break;
            }

            case "accent_color": {
                accentColor = dataSnapshot.getValue(stringClass);
                break;
            }

            case "app_theme": {
                theme = dataSnapshot.getValue(stringClass);
                break;
            }
        }
    }

    // @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void reconfigureTheme(String styleColor) {
        if (styleColor != null) {
            if (styleColor.equals("green")) {
                setTheme(R.style.GreenTheme);
            } else if (styleColor.equals("default")) {
                setTheme(R.style.MyMaterialTheme);
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                getWindow().setNavigationBarColor(getResources().getColor(R.color.color_primary));
                getWindow().setStatusBarColor(getResources().getColor(R.color.color_primary_dark));
            }
        }
    }

    @Override
    public void onCancelled(DatabaseError databaseError) {
        Log.e(TAG, "Failed to read app title value.", databaseError.toException());
    }

    @Override
    public Resources getResources() {
        if (resources == null) {
            resources = new MyResources(super.getResources());
        }
        return resources;
    }
}
