/**
 * @author Felipe Rodrigues Michetti
 * @see http://portfolio-frmichetti.rhcloud.com
 * @see http://www.codecode.com.br
 * @see mailto:frmichetti@gmail.com
 */
package br.com.frmichetti.carhollics.android.view.activity;

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
import com.google.firebase.iid.FirebaseInstanceId;

import br.com.frmichetti.carhollics.android.R;
import br.com.frmichetti.carhollics.android.jobs.AsyncResponse;
import br.com.frmichetti.carhollics.android.jobs.TaskCreateCustomer;
import br.com.frmichetti.carhollics.android.model.compatibility.Customer;
import br.com.frmichetti.carhollics.android.model.compatibility.User;


public class CustomerActivity extends BaseActivity {

    private FloatingActionButton fabConfirm;

    private EditText editTextName, editTextCPF, editTextPhone,editTextMobilePhone;

    private TextInputLayout txtInputLayoutName, txtInputLayoutCPF, txtInputLayoutPhone,txtInputLayoutMobilePhone;

    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_customer);

        doCastComponents();

        doCreateListeners();

    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {

        super.onPostCreate(savedInstanceState);

        doConfigure();

        doLoadExtras(intent);

        doLoadCustomer(customer);

    }

    @Override
    public void doCastComponents() {

        super.doCastComponents();

        editTextName = (EditText) findViewById(R.id.editTextName);

        txtInputLayoutName = (TextInputLayout) findViewById(R.id.input_layout_name);

        editTextCPF = (EditText) findViewById(R.id.editTextCPF);

        txtInputLayoutCPF = (TextInputLayout) findViewById(R.id.input_layout_cpf);

        editTextPhone = (EditText) findViewById(R.id.editTextPhone);

        txtInputLayoutPhone = (TextInputLayout) findViewById(R.id.input_layout_phone);

        editTextMobilePhone = (EditText) findViewById(R.id.editTextMobilePhone);

        txtInputLayoutMobilePhone = (TextInputLayout) findViewById(R.id.input_layout_mobilePhone);

        fabConfirm = (FloatingActionButton) findViewById(R.id.fab_action_done);

    }

    @Override
    public void doCreateListeners() {


        editTextName.addTextChangedListener(new MyTextWatcher(txtInputLayoutName));

        editTextCPF.addTextChangedListener(new MyTextWatcher(txtInputLayoutCPF));

        editTextPhone.addTextChangedListener(new MyTextWatcher(txtInputLayoutPhone));

        editTextMobilePhone.addTextChangedListener(new MyTextWatcher(txtInputLayoutMobilePhone));

        fabConfirm.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {

                if(submitForm()){

                    TaskCreateCustomer taskCreateCustomer = new TaskCreateCustomer(context, new AsyncResponse<Customer>() {

                        @Override
                        public void processFinish(Customer output) {

                            customer = output;

                            startActivity(new Intent(context, MainActivity.class)
                                    .putExtra("shoppingCart", shoppingCart)
                                    .putExtra("customer", customer)
                                    .putExtra("vehicle", selectedVehicle)
                                    .putExtra("service", selectedService));

                            finish();
                        }
                    });

                    customer = doLoadFields();

                    taskCreateCustomer.execute(customer);

                }

            }
        });

    }

    private Customer doLoadCustomer(Customer customer) {

        if (customer != null) {

            editTextName.setText(customer.getName());

            editTextCPF.setText(String.valueOf(customer.getCpf()));

            editTextPhone.setText(String.valueOf(customer.getPhone()));

            editTextMobilePhone.setText(String.valueOf(customer.getMobilePhone()));

        } else {

            try {

                editTextName.setText(firebaseUser.getDisplayName());

            } catch (NullPointerException e) {

                Log.d("DEBUG-USER", "Usuario sem Valores na conta");
            }

        }

        return customer;
    }

    private Customer doLoadFields() {

        Customer c;

        if (customer != null) {

            c = customer;

            if(c.getUser() != null){

                c.getUser().setFirebaseMessageToken(FirebaseInstanceId.getInstance().getToken());

                c.getUser().setFirebaseUUID(FirebaseAuth.getInstance().getCurrentUser().getUid());

            }else{

                Toast.makeText(context,"Usuário parece estar corrompido, por favor contate o Administrador ...",Toast.LENGTH_LONG).show();

                finish();
            }

        } else {

            c = new Customer();

            c.setUser(user);

        }

        c.setName(editTextName.getText().toString());

        c.setCpf(Long.parseLong(editTextCPF.getText().toString()));

        c.setPhone(Long.parseLong(editTextPhone.getText().toString()));

        c.setMobilePhone(Long.parseLong(editTextMobilePhone.getText().toString()));

        return c;

    }

    @Override
    public void doConfigure() {

        super.doConfigure();

        actionBar.setDisplayHomeAsUpEnabled(true);

        actionBar.setSubtitle("Cadastro de Cliente");

    }

    @Override
    public void doLoadExtras(Intent intent) {

        super.doLoadExtras(intent);

        user = (User) intent.getSerializableExtra("user");

        customer = (Customer) intent.getSerializableExtra("customer");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        super.onOptionsItemSelected(item);

        int id = item.getItemId();

        if (id == android.R.id.home) {

            if (user == null) {
                finish();
            }

        }

        return true;
    }

    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {

        showSnack((CoordinatorLayout) findViewById(R.id.coordlayoutCustomer), isConnected);
    }


    /**
     * Validating form
     */
    private boolean submitForm() {
        if (!validateName()) {
            return false;
        }

        if (!validateCPF()) {
            return false;
        }

        if (!validatePhone()) {
            return false;
        }

        if (!validateMobilePhone()) {
            return false;
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

    private boolean validatePhone() {

        if (editTextPhone.getText().toString().trim().isEmpty()) {

            txtInputLayoutPhone.setError(getString(R.string.err_msg_phone));

            requestFocus(editTextPhone);

            return false;

        } else {

            txtInputLayoutPhone.setErrorEnabled(false);
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

                case R.id.editTextName:
                    validateName();
                    break;
                case R.id.editTextCPF:
                    validateCPF();
                    break;
                case R.id.editTextPhone:
                    validatePhone();
                    break;
                case R.id.editTextMobilePhone:
                    validateMobilePhone();
                    break;
            }
        }
    }


}


