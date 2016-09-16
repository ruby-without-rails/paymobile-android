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
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.iid.FirebaseInstanceId;

import java.io.Serializable;

import br.com.frmichetti.carhollics.android.R;
import br.com.frmichetti.carhollics.android.jobs.AsyncResponse;
import br.com.frmichetti.carhollics.android.jobs.TaskCreateUser;
import br.com.frmichetti.carhollics.android.model.compatibility.User;
import br.com.frmichetti.carhollics.android.view.activity.BaseActivity;
import br.com.frmichetti.carhollics.android.view.activity.CustomerActivity;


public class SignupActivity extends BaseActivity {

    private EditText editTextEmail, editTextPassword;

    private Button btnSignIn, btnSignUp, btnResetPassword;

    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_signup);

        doCastComponents();

        doCreateListeners();

        setupToolBar();


    }

    @Override
    protected void onResume() {

        super.onResume();

        progressBar.setVisibility(View.GONE);

    }

    @Override
    public void doCastComponents() {

        super.doCastComponents();

        btnSignIn = (Button) findViewById(R.id.sign_in_button);

        btnSignUp = (Button) findViewById(R.id.sign_up_button);

        editTextEmail = (EditText) findViewById(R.id.email);

        editTextPassword = (EditText) findViewById(R.id.password);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        btnResetPassword = (Button) findViewById(R.id.btn_reset_password);
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

                if (doCheckConnection(context)) {

                    progressBar.setVisibility(View.VISIBLE);

                    //create firebaseUser
                    firebaseAuth.createUserWithEmailAndPassword(email, password)
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

                                        TaskCreateUser taskCreateUsuario = new TaskCreateUser(context, new AsyncResponse<User>() {

                                            @Override
                                            public void processFinish(User output) {

                                                //TODO Implementar Logica do Login

                                                startActivity(new Intent(context, CustomerActivity.class)
                                                        .putExtra("user", (Serializable) output));

                                                finish();
                                            }
                                        });

                                        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

                                        User usuario = new User();

                                        usuario.setFirebaseUUID(firebaseUser.getUid());

                                        usuario.setEmail(firebaseUser.getEmail());

                                        usuario.setFirebaseMessageToken(FirebaseInstanceId.getInstance().getToken());

                                        taskCreateUsuario.execute(usuario);
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
    public void setupToolBar() {

        super.setupToolBar();

        actionBar.setDisplayHomeAsUpEnabled(true);

        actionBar.setSubtitle(R.string.register);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //return super.onCreateOptionsMenu(menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }


    /**
     * Callback will be triggered when there is change in
     * network connection
     */
    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {

        showSnack((CoordinatorLayout) findViewById(R.id.coordlayoutsignup), isConnected);
    }


}
