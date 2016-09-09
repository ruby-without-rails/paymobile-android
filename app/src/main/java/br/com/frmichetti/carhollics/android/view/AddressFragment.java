/**
 *
 * @author Felipe Rodrigues Michetti
 * @see http://portfolio-frmichetti.rhcloud.com
 * @see http://www.codecode.com.br
 * @see mailto:frmichetti@gmail.com
 * */
package br.com.frmichetti.carhollics.android.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.List;

import br.com.frmichetti.carhollics.android.R;
import br.com.frmichetti.carhollics.android.jobs.AsyncResponse;
import br.com.frmichetti.carhollics.android.jobs.TaskDownloadAddress;
import br.com.frmichetti.carhollics.android.model.compatibility.Address;
import br.com.frmichetti.carhollics.android.model.compatibility.Customer;
import br.com.frmichetti.carhollics.android.model.ShoppingCart;
import br.com.frmichetti.carhollics.android.model.compatibility.Vehicle;


public class AddressFragment extends Fragment {

    private Context context;

    private Intent intent;

    private ListView listView;

    private List<Address> addresses;

    private Address selectedAddress;

    private Vehicle selectedVehicle;

    private Customer customer;

    private ShoppingCart shoppingCart;

    public AddressFragment(){}

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        if(savedInstanceState != null){

            addresses = (List<Address>) savedInstanceState.getSerializable("addresses");

        }

        doConfigure();

        doLoadExtras();

        if(shoppingCart == null){

            shoppingCart = new ShoppingCart();
        }

        if(selectedAddress == null){

            selectedAddress = new Address();
        }

        if(selectedVehicle == null){

            selectedVehicle = new Vehicle();
        }

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {

        super.onSaveInstanceState(outState);

        outState.putSerializable("addresses", (Serializable) addresses);

        Log.d("DEBUG - Save State","Salvando Estado");

    }


    private void doConfigure() {

        context = getContext();

        intent = getActivity().getIntent();
    }

    private void doLoadExtras() {

        customer = (Customer) intent.getSerializableExtra("customer");

        shoppingCart = (ShoppingCart) intent.getSerializableExtra("shoppingCart");

        selectedAddress = (Address) intent.getSerializableExtra("address");

        selectedVehicle = (Vehicle) intent.getSerializableExtra("vehicle");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {


        if(savedInstanceState != null){

            addresses = (List<Address>) savedInstanceState.getSerializable("addresses");

        }

        View rootView = inflater.inflate(R.layout.fragment_address, container, false);

        doCastComponents(rootView);

        doCreateListeners();

        doLoadAddresses();

        // Inflate the layout for this fragment
        return rootView;
    }




    private void doCastComponents(View rootView) {

        listView = (ListView) rootView.findViewById(R.id.listViewAddress);

    }

    private void doCreateListeners() {

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                int itemPosition = position;

                Object itemValue = listView.getItemAtPosition(position);

                selectedAddress = (Address) itemValue;

/*                startActivity(new Intent(context,AddressDetailActivity.class)
                        .putExtra("shoppingCart", shoppingCart)
                        .putExtra("customer", customer)
                        .putExtra("vehicle", selectedVehicle)
                        .putExtra("address", selectedAddress)


                );

                */

                Toast.makeText(context,selectedAddress.getStreet(),Toast.LENGTH_SHORT).show();

            }
        });

    }

    private void doLoadAddresses(){

        if(addresses == null){

            Log.d("INFO","Load Addresses from webservice");

            TaskDownloadAddress taskLoadAddresses = new TaskDownloadAddress(context, new AsyncResponse<List<Address>>() {

                @Override
                public void processFinish(List<Address> output) {

                    addresses = output;

                    doFillData(addresses);
                }
            });

            taskLoadAddresses.execute();

        }


    }

    private void doFillData(List<Address> addresses){

        ArrayAdapter<Address> adpItem = new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, addresses);

        listView.setAdapter(adpItem);
    }



}
