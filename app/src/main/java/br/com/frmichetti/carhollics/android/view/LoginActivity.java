package br.com.frmichetti.carhollics.android.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import br.com.frmichetti.carhollics.android.R;
import br.com.frmichetti.carhollics.android.jobs.AsyncResponse;
import br.com.frmichetti.carhollics.android.jobs.TaskLoginFirebase;
import br.com.frmichetti.carhollics.android.model.Cliente;

public class LoginActivity extends AppCompatActivity implements MyPattern{

    private Context context;

    private ActionBar actionBar;

    private EditText inputEmail, inputPassword;

    private FirebaseAuth auth;

    private ProgressBar progressBar;

    private Button btnSignup, btnLogin, btnReset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        auth = FirebaseAuth.getInstance();

        setContentView(R.layout.activity_login);

        doCastComponents();

        doCreateListeners();

    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {

        super.onPostCreate(savedInstanceState);

        doConfigure();

    }

    @Override
    public void doCastComponents() {

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        inputEmail = (EditText) findViewById(R.id.email);

        inputPassword = (EditText) findViewById(R.id.password);

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

                String email = inputEmail.getText().toString();

                final String password = inputPassword.getText().toString();

                if (TextUtils.isEmpty(email)) {

                    Toast.makeText(context, "Enter email address!", Toast.LENGTH_SHORT).show();

                    return;
                }

                if (TextUtils.isEmpty(password)) {

                    Toast.makeText(context, "Enter password!", Toast.LENGTH_SHORT).show();

                    return;
                }

                progressBar.setVisibility(View.VISIBLE);

                //authenticate user
                auth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {

                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                // If sign in fails, display a message to the user. If sign in succeeds
                                // the auth state listener will be notified and logic to handle the
                                // signed in user can be handled in the listener.
                                progressBar.setVisibility(View.GONE);

                                if (!task.isSuccessful()) {

                                    // there was an error
                                    if (password.length() < 6) {

                                        inputPassword.setError(getString(R.string.minimum_password));

                                    } else {

                                        Toast.makeText(context, getString(R.string.auth_failed), Toast.LENGTH_LONG).show();

                                        Log.d("DEBUG-LOGIN",String.valueOf(R.string.auth_failed));
                                    }

                                } else {

                                    TaskLoginFirebase taskLoginFirebase = new TaskLoginFirebase(context, new AsyncResponse<Cliente>() {

                                        @Override
                                        public void processFinish(Cliente output) {

                                            if (output != null){

                                                startActivity(new Intent(context, MainActivity.class).putExtra("Cliente",output));

                                                finish();

                                            }else{

                                                Toast.makeText(context,"Não foi Possivel autorizar o Cliente, contate o desenvolvedor do sistema",Toast.LENGTH_LONG).show();
                                            }
                                        }
                                    });

                                    taskLoginFirebase.execute(auth.getCurrentUser().getUid());



                                }
                            }
                        });
            }
        });

    }

    @Override
    public void doConfigure() {

        context = this;

        actionBar = getSupportActionBar();

        actionBar.setTitle(R.string.app_name);

        actionBar.setSubtitle(R.string.action_sign_in);

        actionBar.setDisplayHomeAsUpEnabled(false);

        actionBar.setDisplayUseLogoEnabled(true);



    }



}
