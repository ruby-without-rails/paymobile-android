/**
 * @author Felipe Rodrigues Michetti
 * @see http://portfolio-frmichetti.rhcloud.com
 * @see http://www.codecode.com.br
 * @see mailto:frmichetti@gmail.com
 */
package br.com.frmichetti.carhollics.android.view.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;

import br.com.frmichetti.carhollics.android.model.ShoppingCart;
import br.com.frmichetti.carhollics.android.model.compatibility.Address;
import br.com.frmichetti.carhollics.android.model.compatibility.Customer;
import br.com.frmichetti.carhollics.android.model.compatibility.Service;
import br.com.frmichetti.carhollics.android.model.compatibility.Vehicle;

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

        customer = intent.getParcelableExtra("customer");

        shoppingCart = (ShoppingCart) intent.getSerializableExtra("shoppingCart");

        selectedAddress = intent.getParcelableExtra("address");

        selectedVehicle = intent.getParcelableExtra("vehicle");

    }
}
