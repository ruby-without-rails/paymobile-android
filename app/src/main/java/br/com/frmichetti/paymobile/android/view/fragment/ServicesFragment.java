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

import java.io.Serializable;
import java.util.ArrayList;

import br.com.frmichetti.paymobile.android.R;
import br.com.frmichetti.paymobile.android.jobs.AsyncResponse;
import br.com.frmichetti.paymobile.android.jobs.TaskDownloadServices;
import br.com.frmichetti.paymobile.android.model.compatibility.Service;
import br.com.frmichetti.paymobile.android.view.activity.ServiceDetailActivity;


public class ServicesFragment extends BaseFragment {

    private TextView textView;

    private ListView listView;

    private ArrayList<Service> services;

    public ServicesFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {


        if (savedInstanceState != null) {
            services = (ArrayList<Service>) savedInstanceState.getSerializable("services");
        }

        View rootView = inflater.inflate(R.layout.fragment_services, container, false);

        doCastComponents(rootView);

        doCreateListeners();

        doLoadServices();

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

                doChangeActivity(context, ServiceDetailActivity.class);

            }
        });

    }

    private void doLoadServices() {

        if (services == null) {

            Log.d("[DOWNLOAD-SERVICES]", "Load Services from webservice");

            TaskDownloadServices taskDownloadServices = new TaskDownloadServices(context,
                    new AsyncResponse<ArrayList<Service>>() {

                        @Override
                        public void processFinish(ArrayList<Service> output) {

                            services = output;

                            doFillData(services);
                        }
                    });

            taskDownloadServices.execute();

        }


    }

    private void doFillData(ArrayList<Service> services) {

        ArrayAdapter<Service> adpItem = new ArrayAdapter<>(context,
                android.R.layout.simple_list_item_1, services);

        listView.setAdapter(adpItem);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {

        super.onSaveInstanceState(outState);

        outState.putSerializable("services", (Serializable) services);
    }
}
