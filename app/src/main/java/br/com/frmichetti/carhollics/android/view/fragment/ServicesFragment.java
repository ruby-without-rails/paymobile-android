/**
 * @author Felipe Rodrigues Michetti
 * @see http://portfolio-frmichetti.rhcloud.com
 * @see http://www.codecode.com.br
 * @see mailto:frmichetti@gmail.com
 */
package br.com.frmichetti.carhollics.android.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import br.com.frmichetti.carhollics.android.R;
import br.com.frmichetti.carhollics.android.jobs.AsyncResponse;
import br.com.frmichetti.carhollics.android.jobs.TaskDownloadServices;
import br.com.frmichetti.carhollics.android.model.compatibility.Service;
import br.com.frmichetti.carhollics.android.view.activity.ServiceDetailActivity;


public class ServicesFragment extends BaseFragment {

    private TextView textView;

    private ListView listView;

    private List<Service> services;

    public ServicesFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {


        if (savedInstanceState != null) {
            services = savedInstanceState.getParcelable("services");
        }

        View rootView = inflater.inflate(R.layout.fragment_services, container, false);

        doCastComponents(rootView);

        doCreateListeners();

        doLoadServices();

        // Inflate the layout for this fragment
        return rootView;
    }

    @Override
    protected void doCastComponents(View rootView) {

        textView = (TextView) rootView.findViewById(R.id.tvSelectedService);

        listView = (ListView) rootView.findViewById(R.id.listViewCheckouts);

    }

    @Override
    protected void doCreateListeners() {

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                int itemPosition = position;

                Object itemValue = listView.getItemAtPosition(position);

                selectedService = (Service) itemValue;

                startActivity(new Intent(context, ServiceDetailActivity.class)
                        .putExtra("shoppingCart", shoppingCart)
                        .putExtra("customer", customer)
                        .putExtra("vehicle", selectedVehicle)
                        .putExtra("service", selectedService)
                );

            }
        });

    }

    private void doLoadServices() {

        if (services == null) {

            Log.d("INFO", "Load Services from webservice");

            TaskDownloadServices taskDownloadServices = new TaskDownloadServices(context,
                    new AsyncResponse<List<Service>>() {

                @Override
                public void processFinish(List<Service> output) {

                    services = output;

                    doFillData(services);
                }
            });

            taskDownloadServices.execute();

        }


    }

    private void doFillData(List<Service> services) {

        ArrayAdapter<Service> adpItem = new ArrayAdapter<>(context,
                android.R.layout.simple_list_item_1, services);

        listView.setAdapter(adpItem);
    }


}
