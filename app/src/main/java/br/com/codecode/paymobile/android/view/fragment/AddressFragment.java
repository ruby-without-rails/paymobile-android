/**
 * @author Felipe Rodrigues Michetti
 * @see http://portfolio-frmichetti.rhcloud.com
 * @see http://www.codecode.com.br
 * @see mailto:frmichetti@gmail.com
 */
package br.com.codecode.paymobile.android.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import br.com.codecode.paymobile.android.R;
import br.com.codecode.paymobile.android.tasks.AsyncResponse;
import br.com.codecode.paymobile.android.tasks.TaskDownloadAddress;
import br.com.codecode.paymobile.android.model.compatibility.Address;
import br.com.codecode.paymobile.android.view.activity.NewAddressActivity;

import static br.com.codecode.paymobile.android.model.IntentKeys.ADDRESS_BUNDLE_KEY;
import static br.com.codecode.paymobile.android.model.IntentKeys.CUSTOMER_BUNDLE_KEY;


public class AddressFragment extends BaseFragment {

    private FloatingActionButton fab;

    private ListView listView;

    private ArrayList<Address> addresses;

    public AddressFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_address, container, false);

        doCastComponents(rootView);

        doCreateListeners();

        // Inflate the layout for this fragment
        return rootView;
    }

    @Override
    public void onResume() {

        super.onResume();

        doLoadAddresses();

    }

    @Override
    protected void doCastComponents(View rootView) {

        listView = rootView.findViewById(R.id.lvAddresses);

        fab = rootView.findViewById(R.id.fab_new_address);

    }

    @Override
    protected void doCreateListeners() {

        fab.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Toast.makeText(context, "New Address Button", Toast.LENGTH_SHORT).show();

                startActivity(new Intent(context, NewAddressActivity.class)
                        .putExtra(CUSTOMER_BUNDLE_KEY, customer)

                );
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Object itemValue = listView.getItemAtPosition(position);

                selectedAddress = (Address) itemValue;

                startActivity(new Intent(context, NewAddressActivity.class)
                        .putExtra(CUSTOMER_BUNDLE_KEY, customer)
                        .putExtra(ADDRESS_BUNDLE_KEY, selectedAddress)
                );

            }
        });

    }

    private void doLoadAddresses() {

        Log.d("INFO", "Load Addresses from webservice");

        TaskDownloadAddress taskLoadAddresses = new TaskDownloadAddress(context, new AsyncResponse<ArrayList<Address>>() {

            @Override
            public void onSuccess(ArrayList<Address> output) {

                addresses = output;

                doFillData(addresses);
            }

            @Override
            public void onFails(Exception e) {
                Toast.makeText(context, e.toString(),Toast.LENGTH_LONG).show();
                Log.d("Error", e.toString());
            }
        });

        taskLoadAddresses.execute(customer);


    }

    private void doFillData(List<Address> addresses) {

        ArrayAdapter<Address> adpItem = new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, addresses);

        listView.setAdapter(adpItem);
    }

}
