/**
 * @author Felipe Rodrigues Michetti
 * @see http://portfolio-frmichetti.rhcloud.com
 * @see http://www.codecode.com.br
 * @see mailto:frmichetti@gmail.com
 */
package br.com.frmichetti.paymobile.android.view.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;

import java.io.Serializable;

import br.com.frmichetti.paymobile.android.model.ShoppingCart;
import br.com.frmichetti.paymobile.android.model.compatibility.Address;
import br.com.frmichetti.paymobile.android.model.compatibility.Customer;
import br.com.frmichetti.paymobile.android.model.compatibility.Service;
import br.com.frmichetti.paymobile.android.model.compatibility.Vehicle;

public abstract class BaseFragment extends Fragment {

    protected Context context;

    protected Intent intent;

    protected Customer customer;

    protected ShoppingCart shoppingCart;

    protected Address selectedAddress;

    protected Vehicle selectedVehicle;

    protected Service selectedService;

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

            shoppingCart = (ShoppingCart) intent.getSerializableExtra("shoppingCart");

            customer = (Customer) intent.getSerializableExtra("customer");

            selectedVehicle = (Vehicle) intent.getSerializableExtra("vehicle");

            selectedService = (Service) intent.getSerializableExtra("service");

            selectedAddress = (Address) intent.getSerializableExtra("address");
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

        if (selectedService == null) {

            throw new RuntimeException("Forbidden - SelectedService is Null");

        }
*/
    }

    public void doChangeActivity(Context context, Class clazz) {

        startActivity(new Intent(context, clazz)
                .putExtra("shoppingCart", shoppingCart)
                .putExtra("customer", customer)
                .putExtra("vehicle", selectedVehicle)
                .putExtra("service", selectedService)
                .putExtra("address", selectedAddress));
    }
}
