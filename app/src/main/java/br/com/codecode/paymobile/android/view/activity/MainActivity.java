/**
 * @author Felipe Rodrigues Michetti
 * @see http://portfolio-frmichetti.rhcloud.com
 * @see http://www.codecode.com.br
 * @see mailto:frmichetti@gmail.com
 */
package br.com.codecode.paymobile.android.view.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.net.MalformedURLException;
import java.net.URL;

import br.com.codecode.paymobile.android.helper.BottomNavigationBehavior;
import br.com.codecode.paymobile.android.R;
import br.com.codecode.paymobile.android.tasks.DownloadImageTask;
import br.com.codecode.paymobile.android.model.ShoppingCart;
import br.com.codecode.paymobile.android.model.compatibility.Address;
import br.com.codecode.paymobile.android.model.compatibility.Product;
import br.com.codecode.paymobile.android.model.compatibility.Vehicle;
import br.com.codecode.paymobile.android.view.activity.shoppingCart.ShoppingCartActivity;
import br.com.codecode.paymobile.android.view.fragment.AddressFragment;
import br.com.codecode.paymobile.android.view.fragment.OrdersFragment;
import br.com.codecode.paymobile.android.view.fragment.FragmentDrawer;
import br.com.codecode.paymobile.android.view.fragment.ProductsFragment;
import br.com.codecode.paymobile.android.view.fragment.StoreFragment;
import br.com.codecode.paymobile.android.view.fragment.VehiclesFragment;


public class MainActivity extends BaseActivity implements FragmentDrawer.FragmentDrawerListener {

    private static String TAG = MainActivity.class.getSimpleName();

    private FragmentDrawer drawerFragment;

    private ImageView imageView;

    private TextView textView;

    private boolean permission;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        doCastComponents();

        doCreateListeners();

        setupToolBar();

    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {

        super.onPostCreate(savedInstanceState);

        doPrepareItems();

        doSetFragment();

        doShowInfo();


    }

    private void doPrepareItems() {

        if (shoppingCart == null) {

            shoppingCart = new ShoppingCart();
        }

        if (selectedProduct == null) {

            selectedProduct = new Product();
        }

        if (selectedVehicle == null) {

            selectedVehicle = new Vehicle();
        }

        if (selectedAddress == null) {

            selectedAddress = new Address();
        }

    }

    //TODO FIXME show info personal data
    private void doShowInfo() {

        if (firebaseUser != null) {

            Log.i("INFO - USER", ((firebaseUser.getDisplayName() != null) ? firebaseUser.getDisplayName() : ""));

            Log.i("INFO - USER", ((firebaseUser.getEmail() != null) ? firebaseUser.getEmail() : ""));

            Log.i("INFO - USER", ((firebaseUser.getProviderId() != null) ? firebaseUser.getProviderId() : ""));

            Log.i("INFO - USER", ((firebaseUser.getPhotoUrl() != null) ? firebaseUser.getPhotoUrl().toString() : ""));
        }


        if (firebaseUser.getPhotoUrl() != null) {

            try {

                new DownloadImageTask(imageView).execute(new URL(firebaseUser.getPhotoUrl().toString()));

            } catch (MalformedURLException e) {

                Log.d("DEBUG", "Não foi possivel alcançar a URL " + e);

            } catch (NullPointerException e) {

                Log.d("DEBUG", "Não foi possível obter a foto " + e);
            }


        }

        if (firebaseUser.getEmail() != null) {

            textView.append(firebaseUser.getEmail());

        }


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        super.onOptionsItemSelected(item);

        int id = item.getItemId();

        if (id == R.id.action_settings) {

            doChangeActivity(context, OptionsActivity.class);

        }

        if (id == R.id.action_cart) {

            if (!shoppingCart.isEmpty()) {

                doChangeActivity(context, ShoppingCartActivity.class);

            } else {

                Toast.makeText(context, getString(R.string.cart_is_empty), Toast.LENGTH_SHORT).show();
            }


        }

        if (id == R.id.action_personal_data) {

            doChangeActivity(context, CustomerActivity.class);
        }

        if (id == R.id.action_contact_developer) {

            Intent i = new Intent(Intent.ACTION_SEND);

            i.setType("message/rfc822");

            i.putExtra(Intent.EXTRA_EMAIL, new String[]{"frmichetti@gmail.com"});

            i.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.app_name));

            i.putExtra(Intent.EXTRA_TEXT, "Contato App Pay Mobile");

            try {

                startActivity(Intent.createChooser(i, getString(R.string.send_mail_to_developer)));

            } catch (android.content.ActivityNotFoundException ex) {

                Toast.makeText(context, getString(R.string.no_email_client_installed), Toast.LENGTH_SHORT).show();
            }

        }


        return true;
    }

    /**
     * For GPS Access
     *
     * @param context
     * @param activity
     */
    private void checkPermissions(Context context, Activity activity) {

        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.ACCESS_FINE_LOCATION)) {


            } else {

                ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 0);
            }

        } else {

            Toast.makeText(context, getString(R.string.permission_granted), Toast.LENGTH_LONG).show();

            permission = true;

        }


    }

    @Override
    public void onDrawerItemSelected(View view, int position) {

        displayView(position);

        fragmentId = position;

    }

    private void displayView(int position) {

        Fragment fragment = null;

        String title = getString(R.string.app_name);

        switch (position) {
            case 0:
                fragment = new StoreFragment();
                title = getString(R.string.title_news);
                break;
            case 1:
                fragment = new ProductsFragment();
                title = getString(R.string.title_products);
                break;
            case 2:
                fragment = new OrdersFragment();
                title = getString(R.string.title_checkouts);
                break;
            case 3:
                fragment = new VehiclesFragment();
                title = getString(R.string.title_vehicles);
                break;
            case 4:
                fragment = new AddressFragment();
                title = getString(R.string.title_address);
                break;
            default:
                break;
        }

        loadFragment(fragment, title);
    }

    private void displayView(MenuItem menuItem) {

        Fragment fragment = null;

        String title = getString(R.string.app_name);

        switch (menuItem.getItemId()) {
            case R.id.navigation_news:
                fragment = new StoreFragment();
                title = getString(R.string.title_news);
                break;
            case R.id.navigation_gifts:
                fragment = new ProductsFragment();
                title = getString(R.string.title_products);
                break;
            /*case R.id.navigation_cart:
                if (!shoppingCart.isEmpty()) {

                    doChangeActivity(context, ShoppingCartActivity.class);

                } else {

                    Toast.makeText(context, getString(R.string.cart_is_empty), Toast.LENGTH_SHORT).show();
                }
                // fragment = new VehiclesFragment();
                // title = getString(R.string.title_vehicles);
                break;
            case R.id.navigation_settings:
                doChangeActivity(context, OptionsActivity.class);
                // fragment = new OrdersFragment();
                // title = getString(R.string.title_checkouts);
                break;
            case R.id.navigation_profile:
                doChangeActivity(context, CustomerActivity.class);
                break;*/
        }

        loadFragment(fragment, title);
    }

    private void loadFragment(Fragment fragment, String title) {
        if (fragment != null) {

            FragmentManager fragmentManager = getSupportFragmentManager();

            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            fragmentTransaction.replace(R.id.container_body, fragment);

            fragmentTransaction.addToBackStack(null);

            fragmentTransaction.commit();

            actionBar.setSubtitle(title);
        }
    }


    @Override
    public void doCreateListeners() {
    }


    private void doSetFragment() {

        drawerFragment = (FragmentDrawer)
                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);

        drawerFragment.setUp(R.id.fragment_navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout), toolbar);

        drawerFragment.setDrawerListener(this);

        // display the first bottom_navigation drawer view on app launch
        displayView(fragmentId);
    }

    @Override
    public void doCastComponents() {

        super.doCastComponents();

        imageView = findViewById(R.id.imageViewAccountImage);

        textView = findViewById(R.id.textViewTitle);


        BottomNavigationView navigation = findViewById(R.id.bottom_navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        //  Enables color Reveal effect
        // navigation.setColored(true);
        // Colors for selected (active) and non-selected items (in color reveal mode).
        // navigation.setColoredModeColors(Color.WHITE, fetchColor(R.color.bottomtab_item_resting));

        // navigation.setTranslucentNavigationEnabled(true);

        // attaching bottom sheet behaviour - hide / show on scroll
        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) navigation.getLayoutParams();
        layoutParams.setBehavior(new BottomNavigationBehavior());
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            displayView(item);

            return true;
        }
    };

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {

            case 0: {

                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    Toast.makeText(context, getString(R.string.permission_accepted), Toast.LENGTH_LONG).show();

                    permission = true;

                } else {

                    Toast.makeText(context, getString(R.string.permission_not_accepted), Toast.LENGTH_LONG).show();

                    permission = false;

                }
                return;
            }
        }
    }

    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        showSnack((CoordinatorLayout) findViewById(R.id.coord_layout_main), isConnected);
    }
}


