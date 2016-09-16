/**
 * @author Felipe Rodrigues Michetti
 * @see http://portfolio-frmichetti.rhcloud.com
 * @see http://www.codecode.com.br
 * @see mailto:frmichetti@gmail.com
 */
package br.com.frmichetti.carhollics.android.view.activity.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

import java.io.Serializable;

import br.com.frmichetti.carhollics.android.R;
import br.com.frmichetti.carhollics.android.jobs.AsyncResponse;
import br.com.frmichetti.carhollics.android.jobs.TaskLoginFirebase;
import br.com.frmichetti.carhollics.android.model.compatibility.Customer;
import br.com.frmichetti.carhollics.android.view.activity.BaseActivity;
import br.com.frmichetti.carhollics.android.view.activity.MainActivity;

public class LoginActivity extends BaseActivity {

    private EditText editTextEmail, editTextPassword;

    private ProgressBar progressBar;

    private Button btnSignup, btnLogin, btnReset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        doCastComponents();

        doCreateListeners();

        setupToolBar();

    }

    @Override
    public void doCastComponents() {

        super.doCastComponents();

        editTextEmail = (EditText) findViewById(R.id.email);

        editTextPassword = (EditText) findViewById(R.id.password);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        btnSignup = (Button) findViewById(R.id.btn_signup);

        btnLogin = (Button) findViewById(R.id.btn_login);

        btnReset = (Button) findViewById(R.id.btn_reset_password);
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


                if (doCheckConnection(context)) {

                    progressBar.setVisibility(View.VISIBLE);

                    //authenticate firebaseUser
                    firebaseAuth.signInWithEmailAndPassword(email, password)
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

                                            Toast.makeText(context, getString(R.string.auth_failed),
                                                    Toast.LENGTH_LONG).show();

                                            Log.d("[LOGIN]", String.valueOf(R.string.auth_failed));
                                        }

                                    } else {

                                        TaskLoginFirebase taskLoginFirebase = new TaskLoginFirebase(context, new AsyncResponse<Customer>() {

                                            @Override
                                            public void processFinish(Customer output) {

                                                if (output != null) {

                                                    startActivity(new Intent(context, MainActivity.class)
                                                            .putExtra("customer", (Serializable) output));

                                                    finish();

                                                } else {

                                                    Toast.makeText(context, getString(R.string.could_not_authorize),
                                                            Toast.LENGTH_LONG).show();
                                                }
                                            }
                                        });

                                        taskLoginFirebase.execute(firebaseAuth.getCurrentUser().getUid());

                                    }
                                }
                            });
                } else {

                    doCheckConnection(context);

                }

            }

        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //return super.onCreateOptionsMenu(menu);
        return true;
    }

    @Override
    public void doConfigure() {

        super.doConfigure();

        //TODO FIXME REMOVEME

        editTextEmail.setText("frmichetti@gmail.com");

        editTextPassword.setText("123456");

    }

    @Override
    public void setupToolBar() {

        super.setupToolBar();

        actionBar.setSubtitle(R.string.action_sign_in);

        actionBar.setDisplayHomeAsUpEnabled(false);

    }


    /**
     * Callback will be triggered when there is change in
     * network connection
     */
    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {

        showSnack((CoordinatorLayout) findViewById(R.id.coordlayoutLogin), isConnected);
    }


}
