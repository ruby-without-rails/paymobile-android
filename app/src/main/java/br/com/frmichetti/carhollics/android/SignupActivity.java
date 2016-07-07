package br.com.frmichetti.carhollics.android;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import br.com.frmichetti.carhollics.android.jobs.AsyncResponse;
import br.com.frmichetti.carhollics.android.jobs.TaskCreateUsuario;
import br.com.frmichetti.carhollics.android.model.Usuario;

public class SignupActivity extends AppCompatActivity implements MyPattern{

    private ActionBar actionBar;

    private Context context;

    private EditText inputEmail, inputPassword;

    private Button btnSignIn, btnSignUp, btnResetPassword;

    private ProgressBar progressBar;

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_signup);

        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();

        doCastComponents();

        doCreateListeners();

    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {

        super.onPostCreate(savedInstanceState);

        doConfigure();
    }

    @Override
    protected void onResume() {

        super.onResume();

        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void doCastComponents() {

        btnSignIn = (Button) findViewById(R.id.sign_in_button);

        btnSignUp = (Button) findViewById(R.id.sign_up_button);

        inputEmail = (EditText) findViewById(R.id.email);

        inputPassword = (EditText) findViewById(R.id.password);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        btnResetPassword = (Button) findViewById(R.id.btn_reset_password);
    }

    @Override
    public void doCreateListeners() {

        btnResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignupActivity.this, ResetPasswordActivity.class));
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

                String email = inputEmail.getText().toString().trim();

                String password = inputPassword.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (password.length() < 6) {
                    Toast.makeText(getApplicationContext(), "Password too short, enter minimum 6 characters!", Toast.LENGTH_SHORT).show();
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);

                //create user
                auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(SignupActivity.this, new OnCompleteListener<AuthResult>() {

                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                Toast.makeText(SignupActivity.this, "createUserWithEmail:onComplete:" + task.isSuccessful(), Toast.LENGTH_LONG).show();

                                progressBar.setVisibility(View.GONE);

                                // If sign in fails, display a message to the user. If sign in succeeds
                                // the auth state listener will be notified and logic to handle the
                                // signed in user can be handled in the listener.
                                if (!task.isSuccessful()) {

                                    Toast.makeText(SignupActivity.this, "Authentication failed." + task.getException(),
                                            Toast.LENGTH_LONG).show();

                                    Log.d("DEBUG-LOGIN","Authentication failed." + task.getException().toString());

                                } else {


                                    TaskCreateUsuario taskCreateLogin = new TaskCreateUsuario(context, new AsyncResponse<Usuario>() {

                                        @Override
                                        public void processFinish(Usuario output) {

                                            //TODO Logica do Login

                                            startActivity(new Intent(SignupActivity.this, ClientActivity.class).putExtra("Usuario",output));

                                            finish();
                                        }
                                    });

                                    FirebaseUser firebaseUser = auth.getCurrentUser();

                                    Usuario usuario = new Usuario();

                                    usuario.setFirebaseUUID(firebaseUser.getUid());

                                    usuario.setLogin(firebaseUser.getEmail());

                                    usuario.setEmail(firebaseUser.getEmail());

                                    taskCreateLogin.execute(usuario);
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

        actionBar.setDisplayHomeAsUpEnabled(true);

        actionBar.setSubtitle(R.string.action_sign_in_short);
    }

    @Override
    public void getExtras(Intent intent) {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if(id == android.R.id.home){
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
