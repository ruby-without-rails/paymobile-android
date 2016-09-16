/**
 * @author Felipe Rodrigues Michetti
 * @see http://portfolio-frmichetti.rhcloud.com
 * @see http://www.codecode.com.br
 * @see mailto:frmichetti@gmail.com
 */
package br.com.frmichetti.carhollics.android.view.activity.login;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import br.com.frmichetti.carhollics.android.R;
import br.com.frmichetti.carhollics.android.view.activity.BaseActivity;

public class ResetPasswordActivity extends BaseActivity {

    private EditText inputEmail;

    private Button btnReset, btnBack;

    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_reset_password);

        doCastComponents();

        doCreateListeners();

        setupToolBar();

    }


    @Override
    public void doCastComponents() {

        super.doCastComponents();

        inputEmail = (EditText) findViewById(R.id.email);

        btnReset = (Button) findViewById(R.id.btn_reset_password);

        btnBack = (Button) findViewById(R.id.btn_back);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);

    }

    @Override
    public void doCreateListeners() {

        btnBack.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }

        });

        btnReset.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                String email = inputEmail.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(context, getString(R.string.enter_email_address_id), Toast.LENGTH_SHORT).show();
                    return;
                }

                if (doCheckConnection(context)) {

                    progressBar.setVisibility(View.VISIBLE);

                    firebaseAuth.sendPasswordResetEmail(email)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {

                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    if (task.isSuccessful()) {
                                        Toast.makeText(context, getString(R.string.reset_password_success), Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(context, getString(R.string.reset_password_failed), Toast.LENGTH_SHORT).show();
                                    }

                                    progressBar.setVisibility(View.GONE);
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

        actionBar.setSubtitle(R.string.btn_reset_password);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == android.R.id.home) {

            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //  return super.onCreateOptionsMenu(menu);
        return true;
    }

    /**
     * Callback will be triggered when there is change in
     * network connection
     */
    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {

        showSnack((CoordinatorLayout) findViewById(R.id.coordlayoutResetPassword),isConnected);
    }


}
