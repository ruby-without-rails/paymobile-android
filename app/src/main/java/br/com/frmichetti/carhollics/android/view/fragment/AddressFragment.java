/**
 * @author Felipe Rodrigues Michetti
 * @see http://portfolio-frmichetti.rhcloud.com
 * @see http://www.codecode.com.br
 * @see mailto:frmichetti@gmail.com
 */
package br.com.frmichetti.carhollics.android.view.fragment;

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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import br.com.frmichetti.carhollics.android.R;
import br.com.frmichetti.carhollics.android.jobs.AsyncResponse;
import br.com.frmichetti.carhollics.android.jobs.TaskDownloadAddress;
import br.com.frmichetti.carhollics.android.model.compatibility.Address;
import br.com.frmichetti.carhollics.android.view.activity.NewAddressActivity;


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

        listView = (ListView) rootView.findViewById(R.id.lvAddresses);

        fab = (FloatingActionButton) rootView.findViewById(R.id.fab_new_address);

    }

    @Override
    protected void doCreateListeners() {

        fab.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Toast.makeText(context, "New Address Button", Toast.LENGTH_SHORT).show();

                startActivity(new Intent(context, NewAddressActivity.class)
                        .putExtra("customer", (Serializable) customer)

                );
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                int itemPosition = position;

                Object itemValue = listView.getItemAtPosition(position);

                selectedAddress = (Address) itemValue;

                startActivity(new Intent(context, NewAddressActivity.class)
                        .putExtra("customer", (Serializable) customer)
                        .putExtra("address", (Serializable) selectedAddress)
                );

            }
        });

    }

    private void doLoadAddresses() {

        Log.d("INFO", "Load Addresses from webservice");

        TaskDownloadAddress taskLoadAddresses = new TaskDownloadAddress(context, new AsyncResponse<ArrayList<Address>>() {

            @Override
            public void processFinish(ArrayList<Address> output) {

                addresses = output;

                doFillData(addresses);
            }
        });

        taskLoadAddresses.execute(customer);


    }

    private void doFillData(List<Address> addresses) {

        ArrayAdapter<Address> adpItem = new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, addresses);

        listView.setAdapter(adpItem);
    }

}
