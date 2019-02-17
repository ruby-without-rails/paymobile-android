/**
 * @author Felipe Rodrigues Michetti
 * @see http://portfolio-frmichetti.rhcloud.com
 * @see http://www.codecode.com.br
 * @see mailto:frmichetti@gmail.com
 */
package br.com.codecode.paymobile.android.view.activity;

import android.app.SearchManager;
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
import android.support.v7.widget.SearchView;
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

import br.com.codecode.paymobile.android.MyApplication;
import br.com.codecode.paymobile.android.R;
import br.com.codecode.paymobile.android.model.ShoppingCart;
import br.com.codecode.paymobile.android.model.compatibility.Address;
import br.com.codecode.paymobile.android.model.compatibility.Customer;
import br.com.codecode.paymobile.android.model.compatibility.Product;
import br.com.codecode.paymobile.android.model.compatibility.Vehicle;
import br.com.codecode.paymobile.android.tasks.AsyncResponse;
import br.com.codecode.paymobile.android.tasks.TaskLogout;
import br.com.codecode.paymobile.android.util.ConnectivityReceiver;
import br.com.codecode.paymobile.android.view.activity.login.LoginActivity;

import static br.com.codecode.paymobile.android.model.IntentKeys.ADDRESS_BUNDLE_KEY;
import static br.com.codecode.paymobile.android.model.IntentKeys.CUSTOMER_BUNDLE_KEY;
import static br.com.codecode.paymobile.android.model.IntentKeys.FRAGMENT_ID;
import static br.com.codecode.paymobile.android.model.IntentKeys.SELECTED_PRODUCT_BUNDLE_KEY;
import static br.com.codecode.paymobile.android.model.IntentKeys.SHOPPING_CART_BUNDLE_KEY;
import static br.com.codecode.paymobile.android.model.IntentKeys.VEHICLE_BUNDLE_KEY;

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

    protected Product selectedProduct;

    protected Vehicle selectedVehicle;

    protected Address selectedAddress;

    protected int fragmentId;

    protected BaseActivity() {
        Log.d("[INFO-INSTANCE]", "Create instance of " + this.getClass().getSimpleName());
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        firebaseAuth = MyApplication.getInstance().getFirebaseAuth();

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

        outState.putSerializable(CUSTOMER_BUNDLE_KEY, customer);

        outState.putSerializable(SHOPPING_CART_BUNDLE_KEY, shoppingCart);

        outState.putSerializable(SELECTED_PRODUCT_BUNDLE_KEY, selectedProduct);

        outState.putSerializable(VEHICLE_BUNDLE_KEY, selectedVehicle);

        outState.putSerializable(ADDRESS_BUNDLE_KEY, selectedAddress);

        outState.putInt(FRAGMENT_ID, fragmentId);

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

//        if (id == R.id.action_vehicle) {
//
//            Toast.makeText(context, getString(R.string.click_on_vehicle_button), Toast.LENGTH_SHORT).show();
//
//            return true;
//
//        }

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

        if(id == R.id.action_logout) {
            new TaskLogout(context, new AsyncResponse<String>() {
                @Override
                public void onSuccess(String output) {
                    startActivity(new Intent(context, LoginActivity.class));
                    finish();
                }

                @Override
                public void onFails(Exception e) {
                    Log.d("Error", e.getMessage());
                }
            }).execute(MyApplication.getInstance().getSessionToken().getKey());

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

            customer = (Customer) bundle.getSerializable(CUSTOMER_BUNDLE_KEY);

            shoppingCart = (ShoppingCart) bundle.getSerializable(SHOPPING_CART_BUNDLE_KEY);

            selectedProduct = (Product) bundle.getSerializable(SELECTED_PRODUCT_BUNDLE_KEY);

            selectedVehicle = (Vehicle) bundle.getSerializable(VEHICLE_BUNDLE_KEY);

            selectedAddress = (Address) bundle.getSerializable(ADDRESS_BUNDLE_KEY);

            fragmentId = bundle.getInt(FRAGMENT_ID);

            Log.d("[LOAD-BUNDLE]", "Load Saved State");

        }


    }

    public Bundle doSaveState() {

        Bundle bundle = new Bundle();

        bundle.putSerializable(CUSTOMER_BUNDLE_KEY, customer);

        bundle.putSerializable(SHOPPING_CART_BUNDLE_KEY, shoppingCart);

        bundle.putSerializable(SELECTED_PRODUCT_BUNDLE_KEY, selectedProduct);

        bundle.putSerializable(VEHICLE_BUNDLE_KEY, selectedVehicle);

        bundle.putSerializable(ADDRESS_BUNDLE_KEY, selectedAddress);

        bundle.putInt(FRAGMENT_ID, fragmentId);

        Log.d("[SAVE-BUNDLE]", "Saved State");

        return bundle;
    }

    public void doLoadExtras(Intent intent) {

        if (intent != null) {

            shoppingCart = (ShoppingCart) intent.getSerializableExtra(SHOPPING_CART_BUNDLE_KEY);

            customer = (Customer) intent.getSerializableExtra(CUSTOMER_BUNDLE_KEY);

            selectedProduct = (Product) intent.getSerializableExtra(SELECTED_PRODUCT_BUNDLE_KEY);

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

        if (selectedProduct == null) {
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
                .putExtra(SHOPPING_CART_BUNDLE_KEY, shoppingCart)
                .putExtra(CUSTOMER_BUNDLE_KEY, customer)
                .putExtra(VEHICLE_BUNDLE_KEY, selectedVehicle)
                .putExtra(SELECTED_PRODUCT_BUNDLE_KEY, selectedProduct)
                .putExtra(ADDRESS_BUNDLE_KEY, selectedAddress));
    }

}
