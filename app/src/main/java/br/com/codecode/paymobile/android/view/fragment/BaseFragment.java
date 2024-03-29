/**
 * @author Felipe Rodrigues Michetti
 * @see http://portfolio-frmichetti.rhcloud.com
 * @see http://www.codecode.com.br
 * @see mailto:frmichetti@gmail.com
 */
package br.com.codecode.paymobile.android.view.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;

import br.com.codecode.paymobile.android.model.ShoppingCart;
import br.com.codecode.paymobile.android.model.compatibility.Address;
import br.com.codecode.paymobile.android.model.compatibility.Customer;
import br.com.codecode.paymobile.android.model.compatibility.Product;
import br.com.codecode.paymobile.android.model.compatibility.Vehicle;

import static br.com.codecode.paymobile.android.model.IntentKeys.ADDRESS_BUNDLE_KEY;
import static br.com.codecode.paymobile.android.model.IntentKeys.CUSTOMER_BUNDLE_KEY;
import static br.com.codecode.paymobile.android.model.IntentKeys.SELECTED_PRODUCT_BUNDLE_KEY;
import static br.com.codecode.paymobile.android.model.IntentKeys.SHOPPING_CART_BUNDLE_KEY;
import static br.com.codecode.paymobile.android.model.IntentKeys.VEHICLE_BUNDLE_KEY;

public abstract class BaseFragment extends Fragment {

    protected Context context;

    protected Intent intent;

    protected Customer customer;

    protected ShoppingCart shoppingCart;

    protected Address selectedAddress;

    protected Vehicle selectedVehicle;

    protected Product selectedProduct;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        doConfigure();

        doLoadExtras(intent);
    }


    protected abstract void doCastComponents(View rootView);

    protected abstract void doCreateListeners();

    private void doConfigure() {

        context = getContext();

        intent = getActivity().getIntent();

        setRetainInstance(true);

    }

    //TODO FIXME VERIFYME
    protected void doLoadExtras(Intent intent) {

        if (intent != null) {

            shoppingCart = (ShoppingCart) intent.getSerializableExtra(SHOPPING_CART_BUNDLE_KEY);

            customer = (Customer) intent.getSerializableExtra(CUSTOMER_BUNDLE_KEY);

            selectedVehicle = (Vehicle) intent.getSerializableExtra(VEHICLE_BUNDLE_KEY);

            selectedProduct = (Product) intent.getSerializableExtra(SELECTED_PRODUCT_BUNDLE_KEY);

            selectedAddress = (Address) intent.getSerializableExtra(ADDRESS_BUNDLE_KEY);
        } else {
            throw new RuntimeException("Forbidden - Could not get Intent");
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
    }

    public void doChangeActivity(Context context, Class clazz) {

        startActivity(new Intent(context, clazz)
                .putExtra(SHOPPING_CART_BUNDLE_KEY, shoppingCart)
                .putExtra(CUSTOMER_BUNDLE_KEY, customer)
                .putExtra(VEHICLE_BUNDLE_KEY, selectedVehicle)
                .putExtra(SELECTED_PRODUCT_BUNDLE_KEY, selectedProduct)
                .putExtra(ADDRESS_BUNDLE_KEY, selectedAddress));
    }
}
