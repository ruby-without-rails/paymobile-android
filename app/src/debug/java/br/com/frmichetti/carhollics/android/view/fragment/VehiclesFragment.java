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
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.List;

import br.com.frmichetti.carhollics.android.R;
import br.com.frmichetti.carhollics.android.jobs.AsyncResponse;
import br.com.frmichetti.carhollics.android.jobs.TaskDownloadVehicles;
import br.com.frmichetti.carhollics.android.model.ShoppingCart;
import br.com.frmichetti.carhollics.android.model.compatibility.Customer;
import br.com.frmichetti.carhollics.android.model.compatibility.Service;
import br.com.frmichetti.carhollics.android.model.compatibility.Vehicle;


public class VehiclesFragment extends Fragment {

    private Context context;

    private Intent intent;

    private TextView textView;

    private ListView listView;

    private List<Vehicle> vehicles;

    private Service selectedService;

    private Vehicle selectedVehicle;

    private Customer customer;

    private ShoppingCart shoppingCart;

    public VehiclesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            vehicles = (List<Vehicle>) savedInstanceState.getSerializable("vehicles");
        }

        doConfigure();

        doLoadExtras();

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {

        super.onSaveInstanceState(outState);

        outState.putSerializable("vehicles", (Serializable) vehicles);
    }

    private void doLoadExtras() {

        customer = (Customer) intent.getSerializableExtra("customer");

        shoppingCart = (ShoppingCart) intent.getSerializableExtra("shoppingCart");

        selectedService = (Service) intent.getSerializableExtra("service");

        selectedVehicle = (Vehicle) intent.getSerializableExtra("vehicle");
    }

    private void doConfigure() {

        context = getContext();

        intent = getActivity().getIntent();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (savedInstanceState != null) {
            vehicles = (List<Vehicle>) savedInstanceState.getSerializable("vehicles");
        }


        View rootView = inflater.inflate(R.layout.fragment_vehicles, container, false);

        doCastComponents(rootView);

        doCreateListeners();

        doLoadVehicles();

        // Inflate the layout for this fragment
        return rootView;
    }

    private void doCreateListeners() {

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                int itemPosition = position;

                Object itemValue = listView.getItemAtPosition(position);

                selectedVehicle = (Vehicle) itemValue;

                Toast.makeText(context, "Veículo Selecionado " + selectedVehicle, Toast.LENGTH_SHORT).show();

            }
        });


    }

    private void doCastComponents(View rootView) {

        textView = (TextView) rootView.findViewById(R.id.textView14);

        listView = (ListView) rootView.findViewById(R.id.listViewVeiculos);

    }

    public void doLoadVeiculos() {

        if (vehicles == null) {

            Log.d("INFO", "Load Services from webservice");

            TaskDownloadVehicles taskLoadVeiculos = new TaskDownloadVehicles(context, new AsyncResponse<List<Vehicle>>() {

                @Override
                public void processFinish(List<Vehicle> output) {

                    vehicles = output;

                    doFillData(vehicles);
                }
            });

            taskLoadVeiculos.execute();
        }


    }

    private void doFillData(List<Vehicle> veiculos) {

        ArrayAdapter<Vehicle> adpItem = new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, veiculos);

        listView.setAdapter(adpItem);
    }


}