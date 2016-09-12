package br.com.frmichetti.carhollics.android.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import br.com.frmichetti.carhollics.android.R;
import br.com.frmichetti.carhollics.android.jobs.AsyncResponse;
import br.com.frmichetti.carhollics.android.jobs.TaskCreateAddress;
import br.com.frmichetti.carhollics.android.jobs.TaskDeleteAddress;
import br.com.frmichetti.carhollics.android.model.compatibility.Address;

public class NewAddressActivity extends BaseActivity {

    private EditText edtCep,edtStreet,edtNeighborhood,edtComplement,edtNumber;

    private Button buttonSave, buttonDelete;

    private Address address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_new_address);

        doCastComponents();

        doCreateListeners();
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {

        super.onPostCreate(savedInstanceState);

        address = new Address();

        doConfigure();

        doLoadExtras(intent);

        if(selectedAddress != null){

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

        buttonSave.setOnClickListener(new View.OnClickListener() {
            
            @Override
            public void onClick(View view) {

                doFillData();

                doSendData();
                
            }
        });

        buttonDelete.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                new TaskDeleteAddress(context).execute(address);

                finish();

            }
        });

    }

    private void doSendData() {

        TaskCreateAddress taskCreateAddress = new TaskCreateAddress(context, new AsyncResponse<Address>() {

            @Override
            public void processFinish(Address output) {

                if(output.getId() != null){
                    if(output.getId() > 0){

                        Toast.makeText(context,"Address Created with Success",Toast.LENGTH_LONG).show();

                        finish();
                    }else{

                        Toast.makeText(context,"Address Not Created , try Again",Toast.LENGTH_LONG).show();
                    }
                }

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

        edtCep = (EditText) findViewById(R.id.editTextCep);

        edtStreet = (EditText) findViewById(R.id.editTextStreet);

        edtNeighborhood = (EditText) findViewById(R.id.editTextNeighborhood);

        edtComplement = (EditText) findViewById(R.id.editTextComplement);

        edtNumber = (EditText) findViewById(R.id.editTextNumber);

        buttonSave = (Button) findViewById(R.id.buttonSave);

        buttonDelete = (Button) findViewById(R.id.buttonDelete);

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
    public void doConfigure() {

        super.doConfigure();

        if(actionBar != null){
            actionBar.setSubtitle("New Address");
        }
    }

    @Override
    public void doLoadExtras(Intent intent) {

        super.doLoadExtras(intent);

        selectedAddress = (Address) intent.getSerializableExtra("address");

    }
}
