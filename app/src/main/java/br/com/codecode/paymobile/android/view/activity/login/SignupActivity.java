/**
 * @author Felipe Rodrigues Michetti
 * @see http://portfolio-frmichetti.rhcloud.com
 * @see http://www.codecode.com.br
 * @see mailto:frmichetti@gmail.com
 */
package br.com.codecode.paymobile.android.view.activity.login;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.iid.FirebaseInstanceId;

import org.json.JSONException;
import org.json.JSONObject;

import br.com.codecode.paymobile.android.R;
import br.com.codecode.paymobile.android.model.compatibility.Customer;
import br.com.codecode.paymobile.android.tasks.AsyncResponse;
import br.com.codecode.paymobile.android.tasks.TaskSaveCustomer;
import br.com.codecode.paymobile.android.util.ConnectivityReceiver;
import br.com.codecode.paymobile.android.view.activity.CustomerActivity;
import br.com.codecode.paymobile.android.view.activity.MyPattern;

import static br.com.codecode.paymobile.android.model.IntentKeys.CUSTOMER_BUNDLE_KEY;

public class SignupActivity extends AppCompatActivity implements MyPattern,
        ConnectivityReceiver.ConnectivityReceiverListener {
    private FirebaseAuth auth;
    private ActionBar actionBar;
    private Context context;
    private EditText editTextEmail, editTextPassword;
    private Button btnSignIn, btnSignUp, btnResetPassword;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        auth = FirebaseAuth.getInstance();

        setContentView(R.layout.activity_signup);

        doCastComponents();

        doCreateListeners();
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        doConfigure();

        doCheckConnection();
    }

    @Override
    protected void onResume() {
        super.onResume();

        progressBar.setVisibility(View.GONE);

        // register connection status listener
        this.setConnectivityListener(this);
    }

    @Override
    public void doCastComponents() {
        Toolbar toolbar = findViewById(R.id.toolbar_dark);

        setSupportActionBar(toolbar);

        btnSignIn = findViewById(R.id.sign_in_button);

        btnSignUp = findViewById(R.id.sign_up_button);

        editTextEmail = findViewById(R.id.edt_email);

        editTextPassword = findViewById(R.id.edt_password);

        progressBar = findViewById(R.id.progressBar);

        btnResetPassword = findViewById(R.id.btn_reset_password);
    }

    @Override
    public void doCreateListeners() {
        btnResetPassword.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, ResetPasswordActivity.class));
            }
        });

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                try {
                    InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                } catch (Exception e) {

                }

                String email = editTextEmail.getText().toString().trim();

                String password = editTextPassword.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {

                    Toast.makeText(context, getString(R.string.enter_email_address), Toast.LENGTH_SHORT).show();

                    return;
                }

                if (TextUtils.isEmpty(password)) {

                    Toast.makeText(context, getString(R.string.enter_password), Toast.LENGTH_SHORT).show();

                    return;
                }

                if (password.length() < 6) {

                    Toast.makeText(context, getString(R.string.minimum_password), Toast.LENGTH_SHORT).show();

                    return;
                }

                if (doCheckConnection()) {
                    progressBar.setVisibility(View.VISIBLE);

                    //create firebaseUser
                    auth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(SignupActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    progressBar.setVisibility(View.GONE);

                                    // If sign in fails, display a message to the firebaseUser. If sign in succeeds
                                    // the firebaseAuth state listener will be notified and logic to handle the
                                    // signed in firebaseUser can be handled in the listener.
                                    if (!task.isSuccessful()) {

                                        Toast.makeText(context, getString(R.string.auth_error) + task.getException(),
                                                Toast.LENGTH_LONG).show();

                                        Log.d("DEBUG-LOGIN", getString(R.string.auth_error) + task.getException().toString());

                                    } else {
                                        TaskSaveCustomer taskSaveCustomer = new TaskSaveCustomer(context, new AsyncResponse<Customer>() {
                                            @Override
                                            public void onSuccess(Customer output) {
                                                if (output != null){
                                                    startActivity(new Intent(context, CustomerActivity.class)
                                                            .putExtra(CUSTOMER_BUNDLE_KEY, output));

                                                    finish();
                                                }else{
                                                    Toast.makeText(context, "Authentication Failed", Toast.LENGTH_LONG).show();
                                                }

                                            }

                                            @Override
                                            public void onFails(Exception e) {
                                                Toast.makeText(context, e.toString(),Toast.LENGTH_LONG).show();
                                                Log.d("Error", e.toString());
                                            }
                                        });

                                        FirebaseUser firebaseUser = auth.getCurrentUser();
                                        JSONObject payload = new JSONObject();

                                        try {
                                            payload.put("fcm_id", firebaseUser.getUid());
                                            payload.put("email", firebaseUser.getEmail());
                                            payload.put("name", "fcm-new-user");
                                            payload.put("cpf", "cpf-new-user");
                                            payload.put("fcm_message_token", FirebaseInstanceId.getInstance().getToken());
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }

                                        taskSaveCustomer.execute(payload);
                                    }
                                }
                            }).addOnFailureListener(SignupActivity.this, new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(context, getString(R.string.auth_error) + e.toString(),
                                    Toast.LENGTH_LONG).show();

                            Log.d("DEBUG-LOGIN", getString(R.string.auth_error) + e.toString().toString());

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

        actionBar.setDisplayHomeAsUpEnabled(true);

        actionBar.setTitle(R.string.app_name);

        actionBar.setSubtitle(R.string.register);

    }

    @Override
    public void doChangeActivity(Context context, Class clazz) {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == android.R.id.home) {

            finish();

        }

        return super.onOptionsItemSelected(item);
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
                .make(findViewById(R.id.coordlayoutsignup), message, Snackbar.LENGTH_LONG);

        View sbView = snackbar.getView();

        TextView textView = sbView.findViewById(android.support.design.R.id.snackbar_text);

        textView.setTextColor(color);

        snackbar.show();

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
}
