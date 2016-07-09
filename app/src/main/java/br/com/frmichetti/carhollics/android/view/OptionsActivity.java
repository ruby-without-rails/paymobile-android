package br.com.frmichetti.carhollics.android.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import br.com.frmichetti.carhollics.android.R;

public class OptionsActivity extends BaseActivity {

    private Button btnChangeEmail, btnChangePassword, btnSendResetEmail, btnRemoveUser,
            changeEmail, changePassword, sendEmail, remove, signOut;

    private EditText oldEmail, newEmail, password, newPassword;

    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_options);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {

        super.onPostCreate(savedInstanceState);

    }

    @Override
    protected void onResume() {

        super.onResume();

        progressBar.setVisibility(View.GONE);
    }


    @Override
    public void doCastComponents() {

        btnChangeEmail = (Button) findViewById(R.id.change_email_button);

        btnChangePassword = (Button) findViewById(R.id.change_password_button);

        btnSendResetEmail = (Button) findViewById(R.id.sending_pass_reset_button);

        btnRemoveUser = (Button) findViewById(R.id.remove_user_button);

        changeEmail = (Button) findViewById(R.id.changeEmail);

        changePassword = (Button) findViewById(R.id.changePass);

        sendEmail = (Button) findViewById(R.id.send);

        remove = (Button) findViewById(R.id.remove);

        signOut = (Button) findViewById(R.id.sign_out);

        oldEmail = (EditText) findViewById(R.id.old_email);

        newEmail = (EditText) findViewById(R.id.new_email);

        password = (EditText) findViewById(R.id.password);

        newPassword = (EditText) findViewById(R.id.newPassword);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        oldEmail.setVisibility(View.GONE);

        newEmail.setVisibility(View.GONE);

        password.setVisibility(View.GONE);

        newPassword.setVisibility(View.GONE);

        changeEmail.setVisibility(View.GONE);

        changePassword.setVisibility(View.GONE);

        sendEmail.setVisibility(View.GONE);

        remove.setVisibility(View.GONE);

        if (progressBar != null) {
            progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void doCreateListeners() {

        btnChangeEmail.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                oldEmail.setVisibility(View.GONE);

                newEmail.setVisibility(View.VISIBLE);

                password.setVisibility(View.GONE);

                newPassword.setVisibility(View.GONE);

                changeEmail.setVisibility(View.VISIBLE);

                changePassword.setVisibility(View.GONE);

                sendEmail.setVisibility(View.GONE);

                remove.setVisibility(View.GONE);
            }
        });

        changeEmail.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                progressBar.setVisibility(View.VISIBLE);

                if (user != null && !newEmail.getText().toString().trim().equals("")) {

                    user.updateEmail(newEmail.getText().toString().trim())
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    if (task.isSuccessful()) {

                                        Toast.makeText(OptionsActivity.this, "Endereço de email foi atualizado. Por favor entre com o novo ID de e-mail! ", Toast.LENGTH_LONG).show();

                                        signOut();

                                        progressBar.setVisibility(View.GONE);

                                    } else {

                                        Toast.makeText(OptionsActivity.this, "Falha ao Atualizar email!", Toast.LENGTH_LONG).show();

                                        progressBar.setVisibility(View.GONE);
                                    }
                                }
                            });

                } else if (newEmail.getText().toString().trim().equals("")) {

                    newEmail.setError("Digite o email");

                    progressBar.setVisibility(View.GONE);
                }
            }
        });

        btnChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                oldEmail.setVisibility(View.GONE);

                newEmail.setVisibility(View.GONE);

                password.setVisibility(View.GONE);

                newPassword.setVisibility(View.VISIBLE);

                changeEmail.setVisibility(View.GONE);

                changePassword.setVisibility(View.VISIBLE);

                sendEmail.setVisibility(View.GONE);

                remove.setVisibility(View.GONE);
            }
        });

        changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                progressBar.setVisibility(View.VISIBLE);

                if (user != null && !newPassword.getText().toString().trim().equals("")) {

                    if (newPassword.getText().toString().trim().length() < 6) {

                        newPassword.setError("Senha muito curta, digite no mínimo 6 caracteres !");

                        progressBar.setVisibility(View.GONE);

                    } else {

                        user.updatePassword(newPassword.getText().toString().trim())
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {

                                        if (task.isSuccessful()) {

                                            Toast.makeText(OptionsActivity.this, "Senha foi atualizada, entre com a nova senha !", Toast.LENGTH_SHORT).show();

                                            signOut();

                                            progressBar.setVisibility(View.GONE);

                                        } else {

                                            Toast.makeText(OptionsActivity.this, "Falha ao atualizar a senha!", Toast.LENGTH_SHORT).show();

                                            progressBar.setVisibility(View.GONE);
                                        }
                                    }
                                });
                    }
                } else if (newPassword.getText().toString().trim().equals("")) {

                    newPassword.setError("Enter password");

                    progressBar.setVisibility(View.GONE);
                }
            }
        });

        btnSendResetEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                oldEmail.setVisibility(View.VISIBLE);

                newEmail.setVisibility(View.GONE);

                password.setVisibility(View.GONE);

                newPassword.setVisibility(View.GONE);

                changeEmail.setVisibility(View.GONE);

                changePassword.setVisibility(View.GONE);

                sendEmail.setVisibility(View.VISIBLE);

                remove.setVisibility(View.GONE);
            }
        });

        sendEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                progressBar.setVisibility(View.VISIBLE);

                if (!oldEmail.getText().toString().trim().equals("")) {

                    auth.sendPasswordResetEmail(oldEmail.getText().toString().trim())
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    if (task.isSuccessful()) {

                                        Toast.makeText(OptionsActivity.this, "E-mail de redefinição de senha foi enviado !", Toast.LENGTH_SHORT).show();

                                        progressBar.setVisibility(View.GONE);

                                    } else {

                                        Toast.makeText(OptionsActivity.this, "Falha ao enviar email de reset!", Toast.LENGTH_SHORT).show();

                                        progressBar.setVisibility(View.GONE);
                                    }
                                }
                            });
                } else {

                    oldEmail.setError("Digite o email");

                    progressBar.setVisibility(View.GONE);
                }
            }
        });

        btnRemoveUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                progressBar.setVisibility(View.VISIBLE);

                if (user != null) {

                    user.delete()
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    if (task.isSuccessful()) {

                                        Toast.makeText(OptionsActivity.this, "O seu perfil foi excluído :( Criar uma conta agora!", Toast.LENGTH_SHORT).show();

                                        startActivity(new Intent(OptionsActivity.this, SignupActivity.class));

                                        finish();

                                        progressBar.setVisibility(View.GONE);

                                    } else {

                                        Toast.makeText(OptionsActivity.this, "Falha ao excluir a sua conta!", Toast.LENGTH_SHORT).show();

                                        progressBar.setVisibility(View.GONE);
                                    }
                                }
                            });
                }
            }
        });

        signOut.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                signOut();

            }
        });

    }



    @Override
    public void getExtras(Intent intent) {

    }
}