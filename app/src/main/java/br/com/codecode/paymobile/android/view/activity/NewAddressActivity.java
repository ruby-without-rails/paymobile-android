package br.com.codecode.paymobile.android.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import br.com.codecode.paymobile.android.R;
import br.com.codecode.paymobile.android.tasks.AsyncResponse;
import br.com.codecode.paymobile.android.tasks.TaskCreateAddress;
import br.com.codecode.paymobile.android.tasks.TaskDeleteAddress;
import br.com.codecode.paymobile.android.model.compatibility.Address;

import static br.com.codecode.paymobile.android.model.IntentKeys.ADDRESS_BUNDLE_KEY;

public class NewAddressActivity extends BaseActivity {

    private EditText edtCep, edtStreet, edtNeighborhood, edtComplement, edtNumber;

    private TextInputLayout txtInputLayoutCep, txtInputLayoutStreet, txtInputLayoutNeighborhood,
            txtInputLayoutComplement, txtInputLayoutNumber;

    private Button buttonSave, buttonDelete;

    private Address address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_new_address);

        doCastComponents();

        doCreateListeners();

        setupToolBar();

    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {

        super.onPostCreate(savedInstanceState);

        address = new Address();

        doReadAddress();

    }

    private void doReadAddress() {

        if (selectedAddress != null) {

            address.setId(selectedAddress.getId());

            edtCep.setText(String.valueOf(selectedAddress.getCep()));

            edtStreet.setText(selectedAddress.getStreet());

            edtNeighborhood.setText(selectedAddress.getNeighborhood());

            edtComplement.setText(selectedAddress.getComplement());

            edtNumber.setText(selectedAddress.getNumber());

        }

    }

    @Override
    public void doCreateListeners() {

        edtCep.addTextChangedListener(new MyTextWatcher(txtInputLayoutCep));

        edtStreet.addTextChangedListener(new MyTextWatcher(txtInputLayoutStreet));

        edtNeighborhood.addTextChangedListener(new MyTextWatcher(txtInputLayoutNeighborhood));

        edtComplement.addTextChangedListener(new MyTextWatcher(txtInputLayoutComplement));

        edtNumber.addTextChangedListener(new MyTextWatcher(txtInputLayoutNumber));

        buttonSave.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                if (submitForm()) {

                    doFillData();

                    doSendData();
                }


            }
        });

        buttonDelete.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                if (address != null) {

                    if (address.getId() != null) {

                        if (address.getId() != 0) {

                            new TaskDeleteAddress(context).execute(address);

                            finish();
                        }
                    }


                }


            }
        });

    }

    private void doSendData() {

        TaskCreateAddress taskCreateAddress = new TaskCreateAddress(context, new AsyncResponse<Address>() {

            @Override
            public void onSuccess(Address output) {

                if (output.getId() != null) {

                    if (output.getId() > 0) {

                        Toast.makeText(context, "Address Created with Success", Toast.LENGTH_LONG).show();

                        finish();

                    } else {

                        Toast.makeText(context, "Address Not Created , try Again", Toast.LENGTH_LONG).show();
                    }
                }

            }

            @Override
            public void onFails(Exception e) {
                Toast.makeText(context, e.toString(),Toast.LENGTH_LONG).show();
                Log.d("Error", e.toString());
            }
        });

        taskCreateAddress.execute(address);

    }

    private void doFillData() {

        address.setCep(Long.parseLong(edtCep.getText().toString()));

        address.setStreet(edtStreet.getText().toString());

        address.setNeighborhood(edtNeighborhood.getText().toString());

        address.setComplement(edtComplement.getText().toString());

        address.setNumber(edtNumber.getText().toString());

        address.setCustomer(customer);

    }

    @Override
    public void doCastComponents() {

        super.doCastComponents();

        edtCep = findViewById(R.id.editTextCep);

        txtInputLayoutCep = findViewById(R.id.input_layout_cep);

        edtStreet = findViewById(R.id.editTextStreet);

        txtInputLayoutStreet = findViewById(R.id.input_layout_street);

        edtNeighborhood = findViewById(R.id.editTextNeighborhood);

        txtInputLayoutNeighborhood = findViewById(R.id.input_layout_neighborhood);

        edtComplement = findViewById(R.id.editTextComplement);

        txtInputLayoutComplement = findViewById(R.id.input_layout_complement);

        edtNumber = findViewById(R.id.editTextNumber);

        txtInputLayoutNumber = findViewById(R.id.input_layout_number);

        buttonSave = findViewById(R.id.buttonSave);

        buttonDelete = findViewById(R.id.buttonDelete);

    }

    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {

        showSnack((CoordinatorLayout) findViewById(R.id.coordlayoutNewAddress), isConnected);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {

        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {

            finish();

        }

        return super.onKeyUp(keyCode, event);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == android.R.id.home) {

            finish();

            return true;

        }

        return super.onOptionsItemSelected(item);

    }

    @Override
    public void setupToolBar() {

        super.setupToolBar();

        actionBar.setSubtitle("New Address");

    }

    @Override
    public void doLoadExtras(Intent intent) {

        super.doLoadExtras(intent);

        selectedAddress = (Address) intent.getSerializableExtra(ADDRESS_BUNDLE_KEY);

    }

    /**
     * Validating form
     */
    private boolean submitForm() {

        if (!validateCEP()) {
            return false;
        }

        if (!validateStreet()) {
            return false;
        }

        if (!validateNeighBorhood()) {
            return false;
        }


        if (!validateComplement()) {
            return false;
        }


        return validateNumber();

    }

    private boolean validateCEP() {

        if (edtCep.getText().toString().trim().isEmpty()) {

            txtInputLayoutCep.setError(getString(R.string.err_msg_cep));

            requestFocus(edtCep);

            return false;

        } else {

            txtInputLayoutCep.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validateStreet() {

        if (edtStreet.getText().toString().trim().isEmpty()) {

            txtInputLayoutStreet.setError(getString(R.string.err_msg_street));

            requestFocus(edtStreet);

            return false;

        } else {

            txtInputLayoutStreet.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validateNeighBorhood() {

        if (edtNeighborhood.getText().toString().trim().isEmpty()) {

            txtInputLayoutNeighborhood.setError(getString(R.string.err_msg_neighborhood));

            requestFocus(edtNeighborhood);

            return false;

        } else {

            txtInputLayoutNeighborhood.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validateComplement() {

        if (edtComplement.getText().toString().trim().isEmpty()) {

            txtInputLayoutComplement.setError(getString(R.string.err_msg_complement));

            requestFocus(edtComplement);

            return false;

        } else {

            txtInputLayoutComplement.setErrorEnabled(false);
        }

        return true;

    }

    private boolean validateNumber() {

        if (edtNumber.getText().toString().trim().isEmpty()) {

            txtInputLayoutNumber.setError(getString(R.string.err_msg_number));

            requestFocus(edtNumber);

            return false;

        } else {

            txtInputLayoutNumber.setErrorEnabled(false);
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
                case R.id.editTextCep:
                    validateCEP();
                    break;
                case R.id.editTextStreet:
                    validateStreet();
                    break;
                case R.id.editTextNeighborhood:
                    validateNeighBorhood();
                    break;
                case R.id.editTextComplement:
                    validateComplement();
                    break;
                case R.id.editTextNumber:
                    validateNumber();
                    break;

            }
        }
    }


}
