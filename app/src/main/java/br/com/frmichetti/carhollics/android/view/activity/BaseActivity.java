/**
 * @author Felipe Rodrigues Michetti
 * @see http://portfolio-frmichetti.rhcloud.com
 * @see http://www.codecode.com.br
 * @see mailto:frmichetti@gmail.com
 */
package br.com.frmichetti.carhollics.android.view.activity;

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
import com.google.firebase.iid.FirebaseInstanceId;

import br.com.frmichetti.carhollics.android.R;
import br.com.frmichetti.carhollics.android.model.ShoppingCart;
import br.com.frmichetti.carhollics.android.model.compatibility.Address;
import br.com.frmichetti.carhollics.android.model.compatibility.Customer;
import br.com.frmichetti.carhollics.android.model.compatibility.Service;
import br.com.frmichetti.carhollics.android.model.compatibility.Vehicle;
import br.com.frmichetti.carhollics.android.util.ConnectivityReceiver;
import br.com.frmichetti.carhollics.android.view.activity.login.LoginActivity;

public abstract class BaseActivity extends AppCompatActivity implements MyPattern,
        ConnectivityReceiver.ConnectivityReceiverListener {

    protected ActionBar actionBar;

    protected Toolbar toolbar;

    protected Context context;

    protected Intent intent;

    protected FirebaseAuth firebaseAuth;

    protected FirebaseUser firebaseUser;

    protected Customer customer;

    protected ShoppingCart shoppingCart;

    protected Service selectedService;

    protected Vehicle selectedVehicle;

    protected Address selectedAddress;

    private FirebaseAuth.AuthStateListener authListener;

    protected BaseActivity() {
        Log.d("[INFO-INSTANCE]", "Create instance of " + this.getClass().getSimpleName());
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        doRecoverState(savedInstanceState);

        firebaseAuth = FirebaseAuth.getInstance();

        firebaseUser = firebaseAuth.getCurrentUser();

        doCreateFirebaseListener();

        Log.d("DEBUG-ON-CREATE", "Super On Create");

        Log.d("Firebase-ID ", FirebaseInstanceId.getInstance().getToken());

    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {

        super.onPostCreate(savedInstanceState);

        doRecoverState(savedInstanceState);

        doConfigure();

        Log.d("DEBUG-ON-POST-CREATE", "Super On Post Create");

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

        Log.d("[DEBUG-AUTH-LISTENER]", "Registered Authentication Listener");

    }


    @Override
    protected void onStop() {

        super.onStop();

        if (authListener != null) {

            firebaseAuth.removeAuthStateListener(authListener);

            Log.d("[DEBUG-AUTH-LISTENER]", "Removed Authentication Listener");

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

        if (id == R.id.action_map) {

            Toast.makeText(context, getString(R.string.click_on_map_button), Toast.LENGTH_SHORT).show();

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

    private void doCreateFirebaseListener() {

        authListener = new FirebaseAuth.AuthStateListener() {

            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                final FirebaseUser user = firebaseAuth.getCurrentUser();

                if (user == null) {

                    startActivity(new Intent(context, LoginActivity.class));

                    finish();

                    Log.d("[DEBUG-INFO]", "User Null, Send to LoginActivity");

                }
            }
        };
    }

    @Override
    public void doCastComponents() {

        toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        actionBar = getSupportActionBar();

        Log.d("DEBUG-DO-CAST-COMP", "Super Do Cast Components");
    }

    @Override
    public void doConfigure() {

        context = this;

        intent = getIntent();

        if(actionBar != null){

            actionBar.setTitle(getString(R.string.app_name));

            actionBar.setDisplayHomeAsUpEnabled(true);

        }

        Log.d("DEBUG-DO-CONFIGURE", "Super Do Configure");

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }

    public void signOut() {

        firebaseAuth.signOut();

        Log.d("[INFO-SIGNOUT]", "SignOut");

    }

    public void doRecoverState(Bundle bundle) {

        if (bundle != null) {

            customer = (Customer) bundle.getSerializable("customer");

            shoppingCart = (ShoppingCart) bundle.getSerializable("shoppingCart");

            selectedService = (Service) bundle.getSerializable("service");

            selectedVehicle = (Vehicle) bundle.getSerializable("vehicle");

            selectedAddress = (Address) bundle.getSerializable("address");

            Log.d("[INFO-LOAD-BUNDLE]", "Load Saved State");

        }


    }

    public Bundle doSaveState() {

        Bundle bundle = new Bundle();

        bundle.putSerializable("customer", customer);

        bundle.putSerializable("shoppingCart", shoppingCart);

        bundle.putSerializable("service", selectedService);

        bundle.putSerializable("vehicle", selectedVehicle);

        bundle.putSerializable("address", selectedAddress);

        Log.d("[INFO-SAVE-BUNDLE]", "Saved State");

        return bundle;
    }

    public void doLoadExtras(Intent intent) {

        customer = (Customer) intent.getSerializableExtra("customer");

        shoppingCart = (ShoppingCart) intent.getSerializableExtra("shoppingCart");

        selectedService = (Service) intent.getSerializableExtra("service");

        selectedVehicle = (Vehicle) intent.getSerializableExtra("vehicle");

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

        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);

        textView.setTextColor(color);

        snackbar.show();

    }

    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {

    }


    private void setConnectivityListener(ConnectivityReceiver.ConnectivityReceiverListener listener) {

        ConnectivityReceiver.connectivityReceiverListener = listener;
    }

}
