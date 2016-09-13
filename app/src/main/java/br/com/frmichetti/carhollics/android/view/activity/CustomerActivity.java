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
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

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

        editTextCPF = (EditText) findViewById(R.id.editTextCPF);

        editTextPhone = (EditText) findViewById(R.id.editTextPhone);

        editTextMobilePhone = (EditText) findViewById(R.id.editTextMobilePhone);

        fabConfirm = (FloatingActionButton) findViewById(R.id.fab_action_done);


    }

    @Override
    public void doCreateListeners() {

        fabConfirm.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {

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

            c.getUser().setFirebaseMessageToken(FirebaseInstanceId.getInstance().getToken());

            c.getUser().setFirebaseUUID(FirebaseAuth.getInstance().getCurrentUser().getUid());


        } else {

            c = new Customer();

            c.setUser(user);

        }

        c.setName(editTextName.getText().toString());

        c.setCpf(Long.valueOf(editTextCPF.getText().toString()));

        c.setPhone(Long.valueOf(editTextPhone.getText().toString()));

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
}
