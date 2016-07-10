package br.com.frmichetti.carhollics.android.view;

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

import br.com.frmichetti.carhollics.android.R;
import br.com.frmichetti.carhollics.android.jobs.AsyncResponse;
import br.com.frmichetti.carhollics.android.jobs.TaskCreateUsuario;
import br.com.frmichetti.carhollics.android.model.Usuario;

public class SignupActivity extends AppCompatActivity implements MyPattern{

    private FirebaseAuth auth;

    private ActionBar actionBar;

    private Context context;

    private EditText inputEmail, inputPassword;

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

                String email = inputEmail.getText().toString().trim();

                String password = inputPassword.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {

                    Toast.makeText(context, "Digite um Endereço de Email !", Toast.LENGTH_SHORT).show();

                    return;
                }

                if (TextUtils.isEmpty(password)) {

                    Toast.makeText(context, "Digite uma Senha !", Toast.LENGTH_SHORT).show();

                    return;
                }

                if (password.length() < 6) {

                    Toast.makeText(context, "Senha muito curta, Digite pelo menos 6 caracteres!", Toast.LENGTH_SHORT).show();

                    return;
                }

                progressBar.setVisibility(View.VISIBLE);

                //create user
                auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(SignupActivity.this, new OnCompleteListener<AuthResult>() {

                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                Toast.makeText(context, "Usuário foi Registrado ? " + task.isSuccessful(), Toast.LENGTH_LONG).show();

                                progressBar.setVisibility(View.GONE);

                                // If sign in fails, display a message to the user. If sign in succeeds
                                // the auth state listener will be notified and logic to handle the
                                // signed in user can be handled in the listener.
                                if (!task.isSuccessful()) {

                                    Toast.makeText(context, "Ocorreu uma Falha na Autenticação." + task.getException(),
                                            Toast.LENGTH_LONG).show();

                                    Log.d("DEBUG-LOGIN","Ocorreu uma Falha na Autenticação." + task.getException().toString());

                                } else {


                                    TaskCreateUsuario taskCreateUsuario = new TaskCreateUsuario(context, new AsyncResponse<Usuario>() {

                                        @Override
                                        public void processFinish(Usuario output) {

                                            //TODO Logica do Login

                                            startActivity(new Intent(context, ClientActivity.class).putExtra("Usuario",output));

                                            finish();
                                        }
                                    });

                                    FirebaseUser firebaseUser = auth.getCurrentUser();

                                    Usuario usuario = new Usuario();

                                    usuario.setFirebaseUUID(firebaseUser.getUid());

                                    usuario.setLogin(firebaseUser.getEmail());

                                    usuario.setEmail(firebaseUser.getEmail());

                                    taskCreateUsuario.execute(usuario);
                                }
                            }
                        });

            }
        });
    }

    @Override
    public void doConfigure() {

        context = this;

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
