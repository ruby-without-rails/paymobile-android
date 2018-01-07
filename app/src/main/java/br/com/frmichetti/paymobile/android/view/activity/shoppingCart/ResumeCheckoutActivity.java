package br.com.frmichetti.paymobile.android.view.activity.shoppingCart;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.frmichetti.paymobile.android.R;
import br.com.frmichetti.paymobile.android.tasks.AsyncResponse;
import br.com.frmichetti.paymobile.android.tasks.TaskCreateCheckout;
import br.com.frmichetti.paymobile.android.tasks.TaskDownloadAddress;
import br.com.frmichetti.paymobile.android.tasks.TaskDownloadVehicles;
import br.com.frmichetti.paymobile.android.model.ShoppingCart;
import br.com.frmichetti.paymobile.android.model.ShoppingItem;
import br.com.frmichetti.paymobile.android.model.compatibility.Address;
import br.com.frmichetti.paymobile.android.model.compatibility.Checkout;
import br.com.frmichetti.paymobile.android.model.compatibility.Vehicle;
import br.com.frmichetti.paymobile.android.view.activity.BaseActivity;
import br.com.frmichetti.paymobile.android.view.activity.MainActivity;

public class ResumeCheckoutActivity extends BaseActivity {

    private ListView listViewShoppingCart;

    private TextView tvTotal;

    private Spinner spinnerAddresses, spinnerVehicles;

    private Button buttonConfirm;

    private List<Vehicle> vehicles;

    private List<Address> addresses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_resume_checkout);

        doCastComponents();

        doCreateListeners();

        setupToolBar();
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {

        super.onPostCreate(savedInstanceState);

        doFillShoppingCart();

        doFillSpinners();

        tvTotal.setText(shoppingCart.getTotal().toString());
    }


    @Override
    public void doCreateListeners() {

        buttonConfirm.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Checkout checkout = new Checkout();

                checkout.setPurchaseDate(new Date());

                if (!shoppingCart.isEmpty()) {

                    checkout.setShoppingCart(shoppingCart.toJson());

                    checkout.setTotal(shoppingCart.getTotal());
                }

                checkout.setCustomer(customer);

                checkout.setAddress(selectedAddress.toJson());

                checkout.setVehicle(selectedVehicle);


                TaskCreateCheckout taskCreateCheckout = new TaskCreateCheckout(context, new AsyncResponse<Boolean>() {

                    @Override
                    public void onSuccess(Boolean output) {

                        if (output != null && output == true) {

                            Toast.makeText(context, getString(R.string.checkout_success), Toast.LENGTH_LONG).show();

                            shoppingCart = new ShoppingCart();

                            doChangeActivity(context, MainActivity.class);

                            finish();

                        } else {

                            Toast.makeText(context, getString(R.string.checkout_failed), Toast.LENGTH_LONG).show();

                        }

                    }

                    @Override
                    public void onFails(Exception e) {
                        Toast.makeText(context, e.getMessage(),Toast.LENGTH_LONG).show();
                        Log.d("Error", e.getMessage());
                    }
                });

                if (selectedAddress != null && selectedVehicle != null && customer != null) {

                    if (doCheckConnection(context)) {

                        taskCreateCheckout.execute(checkout);

                    } else {

                        showSnack((CoordinatorLayout) findViewById(R.id.coord_layout_shopping_cart), doCheckConnection(context));
                    }

                } else {

                    Toast.makeText(context, getString(R.string.cart_is_empty), Toast.LENGTH_SHORT).show();

                }

            }
        });

        spinnerAddresses.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                selectedAddress = (Address) spinnerAddresses.getSelectedItem();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinnerVehicles.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                selectedVehicle = (Vehicle) spinnerVehicles.getSelectedItem();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


    }

    @Override
    public void doCastComponents() {

        super.doCastComponents();

        listViewShoppingCart = findViewById(R.id.listViewShoppingCart);

        spinnerAddresses = findViewById(R.id.spinnerAddresses);

        spinnerVehicles = findViewById(R.id.spinnerVehicles);

        buttonConfirm = findViewById(R.id.buttonConfirmCheckout);

        tvTotal = findViewById(R.id.tv_total_var);

    }

    @Override
    public void setupToolBar() {

        super.setupToolBar();

        actionBar.setSubtitle("Resumo do Pedido");

    }

    private void doFillShoppingCart() {

        List<ShoppingItem> list = new ArrayList<>();

        list.addAll(shoppingCart.getList());

        ArrayAdapter<ShoppingItem> adapterItems = new ArrayAdapter<>(context,
                android.R.layout.simple_list_item_1, list);

        listViewShoppingCart.setAdapter(adapterItems);

    }

    private void doFillSpinners() {

        TaskDownloadAddress taskDownloadAddress = new TaskDownloadAddress(context, new AsyncResponse<ArrayList<Address>>() {

            @Override
            public void onSuccess(ArrayList<Address> output) {

                if (output != null) {

                    addresses = output;


                } else {

                    addresses = new ArrayList<>();
                }

                if (addresses.size() == 0) {

                    Toast.makeText(context, "Você precisa cadastrar um endereço para Prosseguir", Toast.LENGTH_LONG).show();

                    finish();
                }

                ArrayAdapter<Address> adapterAddresses = new ArrayAdapter<>(context,
                        android.R.layout.simple_list_item_1, addresses);

                spinnerAddresses.setAdapter(adapterAddresses);

            }

            @Override
            public void onFails(Exception e) {
                Toast.makeText(context, e.getMessage(),Toast.LENGTH_LONG).show();
                Log.d("Error", e.getMessage());
            }
        });

        taskDownloadAddress.execute(customer);


        TaskDownloadVehicles taskDownloadVehicles = new TaskDownloadVehicles(context, new AsyncResponse<ArrayList<Vehicle>>() {

            @Override
            public void onSuccess(ArrayList<Vehicle> output) {

                if (output != null) {

                    vehicles = output;

                } else {

                    vehicles = new ArrayList<>();
                }

                if (vehicles.size() == 0) {

                    Toast.makeText(context, "Você precisa cadastrar um Veículo para Prosseguir", Toast.LENGTH_LONG).show();

                    finish();
                }

                ArrayAdapter<Vehicle> adapterVehicles = new ArrayAdapter<>(context,
                        android.R.layout.simple_list_item_1, vehicles);

                spinnerVehicles.setAdapter(adapterVehicles);


            }

            @Override
            public void onFails(Exception e) {
                Toast.makeText(context, e.getMessage(),Toast.LENGTH_LONG).show();
                Log.d("Error", e.getMessage());
            }
        });

        taskDownloadVehicles.execute();


    }

    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {

        showSnack((CoordinatorLayout) findViewById(R.id.coordlayoutResumeCheckout), isConnected);
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


}


