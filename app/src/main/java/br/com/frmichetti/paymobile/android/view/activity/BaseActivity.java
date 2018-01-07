/**
 * @author Felipe Rodrigues Michetti
 * @see http://portfolio-frmichetti.rhcloud.com
 * @see http://www.codecode.com.br
 * @see mailto:frmichetti@gmail.com
 */
package br.com.frmichetti.paymobile.android.view.activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import br.com.frmichetti.paymobile.android.R;
import br.com.frmichetti.paymobile.android.model.ShoppingCart;
import br.com.frmichetti.paymobile.android.model.compatibility.Address;
import br.com.frmichetti.paymobile.android.model.compatibility.Customer;
import br.com.frmichetti.paymobile.android.model.compatibility.Product;
import br.com.frmichetti.paymobile.android.model.compatibility.Vehicle;
import br.com.frmichetti.paymobile.android.util.ConnectivityReceiver;
import br.com.frmichetti.paymobile.android.view.activity.login.LoginActivity;

import static br.com.frmichetti.paymobile.android.model.IntentKeys.ADDRESS_BUNDLE_KEY;
import static br.com.frmichetti.paymobile.android.model.IntentKeys.CUSTOMER_BUNDLE_KEY;
import static br.com.frmichetti.paymobile.android.model.IntentKeys.PRODUCTS_BUNDLE_KEY;
import static br.com.frmichetti.paymobile.android.model.IntentKeys.SELECTED_SERVICE_BUNDLE_KEY;
import static br.com.frmichetti.paymobile.android.model.IntentKeys.SHOPPING_CART_BUNDLE_KEY;
import static br.com.frmichetti.paymobile.android.model.IntentKeys.VEHICLE_BUNDLE_KEY;

public abstract class BaseActivity extends AppCompatActivity implements MyPattern,
        ConnectivityReceiver.ConnectivityReceiverListener {

    protected ActionBar actionBar;

    protected Toolbar toolbar;

    protected Context context;

    protected Intent intent;

    private FirebaseAuth.AuthStateListener authListener;

    protected FirebaseAuth firebaseAuth;

    protected FirebaseUser firebaseUser;

    protected ShoppingCart shoppingCart;

    protected Customer customer;

    protected Product selectedService;

    protected Vehicle selectedVehicle;

    protected Address selectedAddress;

    protected int fragmentId;

    protected BaseActivity() {
        Log.d("[INFO-INSTANCE]", "Create instance of " + this.getClass().getSimpleName());
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        firebaseAuth = FirebaseAuth.getInstance();

        firebaseUser = firebaseAuth.getCurrentUser();

        doRegisterFirebaseListener();

        Log.d("[ON-CREATE]", "Super On Create");

    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {

        super.onPostCreate(savedInstanceState);

        doRecoverState(savedInstanceState);

        doConfigure();

        doLoadExtras(intent);

        doCheckConnection(context);

        Log.d("[POST-CREATE]", "Super On Post Create");

    }

    @Override
    protected void onResume() {

        super.onResume();

        this.setConnectivityListener(this);
    }

    @Override
    protected void onStart() {

        super.onStart();

        firebaseAuth.addAuthStateListener(authListener);

        Log.d("[AUTH-LISTENER]", "Registered Authentication Listener");

    }


    @Override
    protected void onStop() {

        super.onStop();

        if (authListener != null) {

            firebaseAuth.removeAuthStateListener(authListener);

            Log.d("[AUTH-LISTENER]", "Removed Authentication Listener");

        }

    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {

        super.onSaveInstanceState(outState);

        outState.putSerializable("customer", customer);

        outState.putSerializable("shoppingCart", shoppingCart);

        outState.putSerializable("service", selectedService);

        outState.putSerializable("vehicle", selectedVehicle);

        outState.putSerializable("address", selectedAddress);

        outState.putInt("fragmentId", fragmentId);

        Log.d("[INFO-SAVE-BUNDLE]", "Save State");

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == android.R.id.home) {

            Toast.makeText(context, getString(R.string.click_on_back_button), Toast.LENGTH_SHORT).show();

            return true;

        }

        if (id == R.id.action_settings) {

            Toast.makeText(context, getString(R.string.click_on_settings_button), Toast.LENGTH_SHORT).show();

            return true;
        }

        if (id == R.id.action_vehicle) {

            Toast.makeText(context, getString(R.string.click_on_vehicle_button), Toast.LENGTH_SHORT).show();

            return true;

        }

        if (id == R.id.action_contact_developer) {

            Toast.makeText(context, getString(R.string.click_on_developer_button), Toast.LENGTH_SHORT).show();

            return true;
        }

        if (id == R.id.action_personal_data) {

            Toast.makeText(context, getString(R.string.click_on_personalData_button), Toast.LENGTH_SHORT).show();

            return true;
        }


        if (id == R.id.action_cart) {

            Toast.makeText(context, getString(R.string.click_on_cart_button), Toast.LENGTH_SHORT).show();

            return true;
        }


        if (id == R.id.action_about) {

            PackageInfo pinfo = null;

            try {

                pinfo = getPackageManager().getPackageInfo(getPackageName(), 0);

            } catch (PackageManager.NameNotFoundException e) {

                e.printStackTrace();
            }

            int versionNumber = pinfo.versionCode;

            String versionName = pinfo.versionName;

            Toast.makeText(context, getString(R.string.version_of_app) + versionName, Toast.LENGTH_LONG).show();

            return true;
        }


        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {

        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {

            Toast.makeText(context, getString(R.string.keyUp_back_button), Toast.LENGTH_SHORT).show();

        }

        if (event.getKeyCode() == KeyEvent.KEYCODE_HOME) {

            Toast.makeText(context, getString(R.string.keyUp_home_button), Toast.LENGTH_SHORT).show();
        }

        if (event.getKeyCode() == KeyEvent.KEYCODE_SEARCH) {

            Toast.makeText(context, getString(R.string.keyUp_search_button), Toast.LENGTH_SHORT).show();
        }

        return true;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }

    @Override
    public void doCastComponents() {

        toolbar = findViewById(R.id.toolbar);

        Log.d("[DO-CAST-COMP]", "Super Do Cast Components");

    }

    @Override
    public void doConfigure() {

        context = this;

        intent = getIntent();

        Log.d("[DO-CONFIGURE]", "Super Do Configure");

    }

    @Override
    public void setupToolBar() {

        if (toolbar != null) {

            setSupportActionBar(toolbar);

            actionBar = getSupportActionBar();

            actionBar.setTitle(getString(R.string.app_name));

            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        Log.d("[SETUP-TOOLBAR]", "Super Setup Toolbar");

    }

    public void signOut() {

        firebaseAuth.signOut();

        Log.d("[INFO-SIGNOUT]", "SignOut");

    }

    public void doRecoverState(Bundle bundle) {

        if (bundle != null) {

            customer = (Customer) bundle.getSerializable("customer");

            //TODO CHANGE TO PARCELABLE
            shoppingCart = (ShoppingCart) bundle.getSerializable("shoppingCart");

            selectedService = (Product) bundle.getSerializable("service");

            selectedVehicle = (Vehicle) bundle.getSerializable("vehicle");

            selectedAddress = (Address) bundle.getSerializable("address");

            fragmentId = bundle.getInt("fragmentId");

            Log.d("[LOAD-BUNDLE]", "Load Saved State");

        }


    }

    public Bundle doSaveState() {

        Bundle bundle = new Bundle();

        bundle.putSerializable("customer", customer);

        //TODO CHANGE TO PARCELABLE
        bundle.putSerializable("shoppingCart", shoppingCart);

        bundle.putSerializable("service", selectedService);

        bundle.putSerializable("vehicle", selectedVehicle);

        bundle.putSerializable("address", selectedAddress);

        bundle.putInt("fragmentId", fragmentId);

        Log.d("[SAVE-BUNDLE]", "Saved State");

        return bundle;
    }

    public void doLoadExtras(Intent intent) {

        if (intent != null) {

            shoppingCart = (ShoppingCart) intent.getSerializableExtra(SHOPPING_CART_BUNDLE_KEY);

            customer = (Customer) intent.getSerializableExtra(CUSTOMER_BUNDLE_KEY);

            selectedService = (Product) intent.getSerializableExtra(SELECTED_SERVICE_BUNDLE_KEY);

            selectedVehicle = (Vehicle) intent.getSerializableExtra(VEHICLE_BUNDLE_KEY);

        }

        /*
        if (shoppingCart == null) {

            throw new RuntimeException("Forbidden - Shopping Cart is Null");

        }

        if (customer == null) {

            throw new RuntimeException("Forbidden - Customer is Null");
        }

        if (selectedVehicle == null) {
            throw new RuntimeException("Forbidden - SelectedVehicle is Null");
        }

        if (selectedAddress == null) {

            throw new RuntimeException("Forbidden - SelectedAddress is Null");

        }

        if (selectedService == null) {

            throw new RuntimeException("Forbidden - SelectedService is Null");

        }
*/

        Log.d("DEBUG-LOAD-EXTRAS", "Load Extras");
    }

    // Method to manually check connection status
    protected boolean doCheckConnection(Context context) {
        return ConnectivityReceiver.isConnected(context);
    }

    // Showing the status in Snackbar
    protected void showSnack(CoordinatorLayout coordinatorLayout, boolean isConnected) {

        String message;

        int color;

        if (isConnected) {

            message = getString(R.string.connected_on_internet);

            color = Color.GREEN;

        } else {

            message = getString(R.string.not_connected_on_internet);

            color = Color.RED;
        }

        Snackbar snackbar = Snackbar
                .make(coordinatorLayout, message, Snackbar.LENGTH_LONG);

        View sbView = snackbar.getView();

        TextView textView = sbView.findViewById(android.support.design.R.id.snackbar_text);

        textView.setTextColor(color);

        snackbar.show();

    }


    private void doRegisterFirebaseListener() {

        authListener = new FirebaseAuth.AuthStateListener() {

            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                final FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

                if (firebaseUser == null) {

                    startActivity(new Intent(context, LoginActivity.class));

                    finish();

                    Log.d("[DEBUG-INFO]", "User Null, Send to LoginActivity");

                }
            }
        };


    }

    private void setConnectivityListener(ConnectivityReceiver.ConnectivityReceiverListener listener) {

        ConnectivityReceiver.connectivityReceiverListener = listener;
    }

    @Override
    public void doChangeActivity(Context context, Class clazz) {

        startActivity(new Intent(context, clazz)
                .putExtra(PRODUCTS_BUNDLE_KEY, shoppingCart)
                .putExtra(CUSTOMER_BUNDLE_KEY, customer)
                .putExtra(VEHICLE_BUNDLE_KEY, selectedVehicle)
                .putExtra(SELECTED_SERVICE_BUNDLE_KEY, selectedService)
                .putExtra(ADDRESS_BUNDLE_KEY, selectedAddress));
    }

}
