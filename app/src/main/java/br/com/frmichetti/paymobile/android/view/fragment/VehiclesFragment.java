/**
 * @author Felipe Rodrigues Michetti
 * @see http://portfolio-frmichetti.rhcloud.com
 * @see http://www.codecode.com.br
 * @see mailto:frmichetti@gmail.com
 */
package br.com.frmichetti.paymobile.android.view.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import br.com.frmichetti.paymobile.android.R;
import br.com.frmichetti.paymobile.android.jobs.AsyncResponse;
import br.com.frmichetti.paymobile.android.jobs.TaskDownloadVehicles;
import br.com.frmichetti.paymobile.android.model.compatibility.Vehicle;


public class VehiclesFragment extends BaseFragment {

    private TextView textView;

    private ListView listView;

    private ArrayList<Vehicle> vehicles;

    public VehiclesFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (savedInstanceState != null) {
            vehicles = savedInstanceState.getParcelable("vehicles");
        }


        View rootView = inflater.inflate(R.layout.fragment_vehicles, container, false);

        doCastComponents(rootView);

        doCreateListeners();

        doLoadVehicles();

        // Inflate the layout for this fragment
        return rootView;
    }

    @Override
    protected void doCreateListeners() {

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

    @Override
    protected void doCastComponents(View rootView) {

        textView = (TextView) rootView.findViewById(R.id.textView14);

        listView = (ListView) rootView.findViewById(R.id.listViewVeiculos);

    }

    public void doLoadVehicles() {

        if (vehicles == null) {

            Log.d("INFO", "Load Services from webservice");

            TaskDownloadVehicles taskDownloadVehicles = new TaskDownloadVehicles(context, new AsyncResponse<ArrayList<Vehicle>>() {

                @Override
                public void processFinish(ArrayList<Vehicle> output) {

                    vehicles = output;

                    doFillData(vehicles);
                }
            });

            taskDownloadVehicles.execute();
        }


    }

    private void doFillData(List<Vehicle> vehicles) {

        ArrayAdapter<Vehicle> adpItem = new ArrayAdapter<>(context,
                android.R.layout.simple_list_item_1, vehicles);

        listView.setAdapter(adpItem);
    }


}