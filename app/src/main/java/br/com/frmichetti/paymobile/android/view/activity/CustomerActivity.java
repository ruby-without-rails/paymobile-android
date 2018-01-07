/**
 * @author Felipe Rodrigues Michetti
 * @see http://portfolio-frmichetti.rhcloud.com
 * @see http://www.codecode.com.br
 * @see mailto:frmichetti@gmail.com
 */
package br.com.frmichetti.paymobile.android.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.iid.FirebaseInstanceId;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import br.com.frmichetti.paymobile.android.MyApplication;
import br.com.frmichetti.paymobile.android.R;
import br.com.frmichetti.paymobile.android.dto.CustomerDTO;
import br.com.frmichetti.paymobile.android.model.Token;
import br.com.frmichetti.paymobile.android.model.compatibility.Customer;
import br.com.frmichetti.paymobile.android.tasks.AsyncResponse;
import br.com.frmichetti.paymobile.android.tasks.TaskSaveCustomer;

import static br.com.frmichetti.paymobile.android.model.IntentKeys.CUSTOMER_BUNDLE_KEY;


public class CustomerActivity extends BaseActivity {
    private FloatingActionButton fabConfirm;
    private EditText editTextName, editTextCPF, editTextMobilePhone;
    private TextInputLayout txtInputLayoutName, txtInputLayoutCPF, txtInputLayoutMobilePhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_customer);

        doCastComponents();

        doCreateListeners();

        setupToolBar();

    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        doLoadCustomer(customer);

    }

    @Override
    public void doCastComponents() {
        super.doCastComponents();

        editTextName = findViewById(R.id.edt_name);

        txtInputLayoutName = findViewById(R.id.input_layout_name);

        editTextCPF = findViewById(R.id.edt_cpf);

        txtInputLayoutCPF = findViewById(R.id.input_layout_cpf);

        editTextMobilePhone = findViewById(R.id.edt_mobile_phone);

        txtInputLayoutMobilePhone = findViewById(R.id.input_layout_mobilePhone);

        fabConfirm = findViewById(R.id.fab_action_done);

    }

    @Override
    public void doCreateListeners() {

        editTextName.addTextChangedListener(new MyTextWatcher(txtInputLayoutName));

        editTextCPF.addTextChangedListener(new MyTextWatcher(txtInputLayoutCPF));

        editTextMobilePhone.addTextChangedListener(new MyTextWatcher(txtInputLayoutMobilePhone));

        fabConfirm.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {

                if (submitForm()) {

                    HashMap<String, String> headers = new HashMap<>();
                    headers.put("PayWithRuby-Auth-Token", MyApplication.getSessionToken().getKey());

                    FirebaseAuth auth = FirebaseAuth.getInstance();
                    FirebaseUser firebaseUser = auth.getCurrentUser();
                    JSONObject payload = new JSONObject();

                    try {
                        payload.put("id", customer.getId());
                        payload.put("fcm_id", firebaseUser.getUid());
                        payload.put("email", firebaseUser.getEmail());
                        payload.put("name", customer.getName());
                        payload.put("cpf", customer.getCpf());
                        payload.put("mobile_phone_number", customer.getMobilePhone());
                        payload.put("fcm_message_token", FirebaseInstanceId.getInstance().getToken());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    new TaskSaveCustomer(context, new AsyncResponse<Customer>() {
                        @Override
                        public void onSuccess(Customer output) {
                            if (output != null) {
                                customer = output;

                                doChangeActivity(context, MainActivity.class);
                                finish();
                            }
                        }

                        @Override
                        public void onFails(Exception e) {
                            Toast.makeText(context, "Error on Update Customer info.", Toast.LENGTH_LONG).show();

                        }
                    }).execute(payload);

                    customer = doLoadFields();


                }

            }
        });

    }

    private Customer doLoadCustomer(Customer customer) {
        if (customer != null) {

            editTextName.setText(customer.getName());

            editTextCPF.setText(String.valueOf(customer.getCpf()));

            editTextMobilePhone.setText(String.valueOf(customer.getMobilePhone()));

        } else {

            try {

                editTextName.setText(firebaseUser.getDisplayName());

            } catch (NullPointerException e) {

                Log.d("DEBUG-USER", "Usuário sem Valores na conta");
            }

        }

        return customer;
    }

    private Customer doLoadFields() {
        Customer c = new Customer();

        if (customer != null) {
            c = customer;

            c.setFcmMessageToken(FirebaseInstanceId.getInstance().getToken());

            c.setFcmId(FirebaseAuth.getInstance().getCurrentUser().getUid());

        } else {
            Toast.makeText(context, "Usuário parece estar corrompido, por favor contate o Administrador ...", Toast.LENGTH_LONG).show();

            finish();
        }
        return c;
    }


    @Override
    public void setupToolBar() {

        super.setupToolBar();

        actionBar.setSubtitle("Cadastro de Cliente");
    }

    @Override
    public void doLoadExtras(Intent intent) {

        super.doLoadExtras(intent);

        customer = (Customer) intent.getSerializableExtra(CUSTOMER_BUNDLE_KEY);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        super.onOptionsItemSelected(item);

        int id = item.getItemId();

        if (id == android.R.id.home) {

            if (customer == null) {
                finish();
            }
        }

        return true;
    }

    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {

        showSnack((CoordinatorLayout) findViewById(R.id.coord_layout_customer), isConnected);
    }


    /**
     * Validating form
     */
    private boolean submitForm() {
        if (!validateName()) {
            return false;
        } else {
            customer.setName(editTextName.getText().toString());
        }

        if (!validateCPF()) {
            return false;
        } else {
            customer.setCpf(editTextCPF.getText().toString());
        }

        if (!validateMobilePhone()) {
            return false;
        } else {
            customer.setMobilePhone(editTextMobilePhone.getText().toString());
        }

        return true;

    }

    private boolean validateName() {

        if (editTextName.getText().toString().trim().isEmpty()) {

            txtInputLayoutName.setError(getString(R.string.err_msg_name));

            requestFocus(editTextName);

            return false;

        } else {

            txtInputLayoutName.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validateCPF() {

        if (editTextCPF.getText().toString().trim().isEmpty()) {

            txtInputLayoutCPF.setError(getString(R.string.err_msg_cpf));

            requestFocus(editTextCPF);

            return false;

        } else {

            txtInputLayoutCPF.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validateMobilePhone() {

        if (editTextMobilePhone.getText().toString().trim().isEmpty()) {

            txtInputLayoutMobilePhone.setError(getString(R.string.err_msg_phone));

            requestFocus(editTextMobilePhone);

            return false;

        } else {

            txtInputLayoutMobilePhone.setErrorEnabled(false);
        }

        return true;
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    private class MyTextWatcher implements TextWatcher {

        private View view;

        private MyTextWatcher(View view) {
            this.view = view;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        public void afterTextChanged(Editable editable) {
            switch (view.getId()) {
                case R.id.edt_name:
                    validateName();
                    break;
                case R.id.edt_cpf:
                    validateCPF();
                    break;
                case R.id.edt_mobile_phone:
                    validateMobilePhone();
                    break;
            }
        }
    }


}


