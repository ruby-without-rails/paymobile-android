/**
 * @author Felipe Rodrigues Michetti
 * @see http://portfolio-frmichetti.rhcloud.com
 * @see http://www.codecode.com.br
 * @see mailto:frmichetti@gmail.com
 */
package br.com.codecode.paymobile.android.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import br.com.codecode.paymobile.android.R;
import br.com.codecode.paymobile.android.view.activity.login.SignupActivity;
import br.com.codecode.paymobile.android.view.activity.shoppingCart.ShoppingCartActivity;


public class OptionsActivity extends BaseActivity {

    private Button btnChangeEmail, btnChangePassword, btnSendResetEmail, btnRemoveUser,
            changeEmail, changePassword, sendEmail, remove, signOut;

    private EditText oldEmail, newEmail, password, newPassword;

    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_options);

        doCastComponents();

        doCreateListeners();

        setupToolBar();

    }

    @Override
    public void setupToolBar() {

        super.setupToolBar();

        actionBar.setSubtitle(R.string.action_settings);

    }

    public void doCastComponents() {

        super.doCastComponents();

        btnChangeEmail = findViewById(R.id.change_email_button);

        btnChangePassword = findViewById(R.id.change_password_button);

        btnSendResetEmail = findViewById(R.id.sending_pass_reset_button);

        btnRemoveUser = findViewById(R.id.remove_user_button);

        changeEmail = findViewById(R.id.changeEmail);

        changePassword = findViewById(R.id.changePass);

        sendEmail = findViewById(R.id.send);

        remove = findViewById(R.id.remove);

        signOut = findViewById(R.id.sign_out);

        oldEmail = findViewById(R.id.old_email);

        newEmail = findViewById(R.id.new_email);

        password = findViewById(R.id.edt_password);

        newPassword = findViewById(R.id.newPassword);

        progressBar = findViewById(R.id.progressBar);

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

                if (firebaseUser != null && !newEmail.getText().toString().trim().equals("")) {

                    firebaseUser.updateEmail(newEmail.getText().toString().trim())
                            .addOnCompleteListener(new OnCompleteListener<Void>() {

                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    if (task.isSuccessful()) {

                                        Toast.makeText(context, getString(R.string.update_email_sucess),
                                                Toast.LENGTH_LONG).show();

                                        signOut();

                                        progressBar.setVisibility(View.GONE);

                                    } else {

                                        Toast.makeText(context, getString(R.string.update_email_failed), Toast.LENGTH_LONG).show();

                                        progressBar.setVisibility(View.GONE);
                                    }
                                }
                            });

                } else if (newEmail.getText().toString().trim().equals("")) {

                    newEmail.setError(getString(R.string.enter_email_address));

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

                if (firebaseUser != null && !newPassword.getText().toString().trim().equals("")) {

                    if (newPassword.getText().toString().trim().length() < 6) {

                        newPassword.setError(getString(R.string.minimum_password));

                        progressBar.setVisibility(View.GONE);

                    } else {

                        firebaseUser.updatePassword(newPassword.getText().toString().trim())
                                .addOnCompleteListener(new OnCompleteListener<Void>() {

                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {

                                        if (task.isSuccessful()) {

                                            Toast.makeText(context, getString(R.string.password_update_sucess), Toast.LENGTH_SHORT).show();

                                            signOut();

                                            progressBar.setVisibility(View.GONE);

                                        } else {

                                            Toast.makeText(context, getString(R.string.password_update_failed), Toast.LENGTH_SHORT).show();

                                            progressBar.setVisibility(View.GONE);
                                        }
                                    }
                                });
                    }

                } else if (newPassword.getText().toString().trim().equals("")) {

                    newPassword.setError(getString(R.string.enter_password));

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

                    firebaseAuth.sendPasswordResetEmail(oldEmail.getText().toString().trim())
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    if (task.isSuccessful()) {

                                        Toast.makeText(context, getString(R.string.reset_email_sucess), Toast.LENGTH_SHORT).show();

                                        progressBar.setVisibility(View.GONE);

                                    } else {

                                        Toast.makeText(context, getString(R.string.reset_email_failed), Toast.LENGTH_SHORT).show();

                                        progressBar.setVisibility(View.GONE);
                                    }
                                }
                            });
                } else {

                    oldEmail.setError(getString(R.string.enter_email_address));

                    progressBar.setVisibility(View.GONE);
                }
            }
        });

        btnRemoveUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                progressBar.setVisibility(View.VISIBLE);

                if (firebaseUser != null) {

                    firebaseUser.delete()
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    if (task.isSuccessful()) {

                                        Toast.makeText(context, getString(R.string.delete_account_success),
                                                Toast.LENGTH_SHORT).show();

                                        startActivity(new Intent(context, SignupActivity.class));

                                        finish();

                                        progressBar.setVisibility(View.GONE);

                                    } else {

                                        Toast.makeText(context, getString(R.string.delete_account_failed), Toast.LENGTH_SHORT).show();

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
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == android.R.id.home) {

            finish();

            return true;

        }

        if (id == R.id.action_cart) {

            doChangeActivity(context, ShoppingCartActivity.class);

            return true;

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {

        super.onResume();

        progressBar.setVisibility(View.GONE);
    }

    /**
     * Callback will be triggered when there is change in
     * network connection
     */
    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {

        showSnack((CoordinatorLayout) findViewById(R.id.coordlayoutOptions), isConnected);
    }


}