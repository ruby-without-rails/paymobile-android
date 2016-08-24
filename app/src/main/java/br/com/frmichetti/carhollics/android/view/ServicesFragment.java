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
import android.widget.TextView;

import java.io.Serializable;
import java.util.List;

import br.com.frmichetti.carhollics.android.R;
import br.com.frmichetti.carhollics.android.jobs.AsyncResponse;
import br.com.frmichetti.carhollics.android.jobs.TaskLoadServicos;
import br.com.frmichetti.carhollics.android.model.Customer;
import br.com.frmichetti.carhollics.android.model.Service;
import br.com.frmichetti.carhollics.android.model.ShoppingCart;
import br.com.frmichetti.carhollics.android.model.Vehicle;


public class ServicesFragment extends Fragment {

    private Context context;

    private Intent intent;

    private TextView textView;

    private ListView listView;

    private List<Service> services;

    private Service selectedService;

    private Vehicle selectedVehicle;

    private Customer customer;

    private ShoppingCart shoppingCart;

    public ServicesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        if(savedInstanceState != null){

            services = (List<Service>) savedInstanceState.getSerializable("Servicos");

        }

        doConfigure();

        doLoadExtras();

        if(shoppingCart == null){

            shoppingCart = new ShoppingCart();
        }

        if(selectedService == null){

            selectedService = new Service();
        }

        if(selectedVehicle == null){

            selectedVehicle = new Vehicle();
        }

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {

        super.onSaveInstanceState(outState);

        outState.putSerializable("Servicos", (Serializable) services);

        Log.d("DEBUG - Save State","Salvando Estado");

    }


    private void doConfigure() {

        context = getContext();

        intent = getActivity().getIntent();
    }

    private void doLoadExtras() {

        customer = (Customer) intent.getSerializableExtra("Cliente");

        shoppingCart = (ShoppingCart) intent.getSerializableExtra("Carrinho");

        selectedService = (Service) intent.getSerializableExtra("Servico");

        selectedVehicle = (Vehicle) intent.getSerializableExtra("Veiculo");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {


        if(savedInstanceState != null){

            services = (List<Service>) savedInstanceState.getSerializable("Servicos");

        }

        View rootView = inflater.inflate(R.layout.fragment_services, container, false);

        doCastComponents(rootView);

        doCreateListeners();

        doLoadServicos();

        // Inflate the layout for this fragment
        return rootView;
    }




    private void doCastComponents(View rootView) {

        textView = (TextView) rootView.findViewById(R.id.textViewBemVindo);

        listView = (ListView) rootView.findViewById(R.id.listViewPedidos);

    }

    private void doCreateListeners() {

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                int itemPosition = position;

                Object itemValue = listView.getItemAtPosition(position);

                selectedService = (Service) itemValue;

                startActivity(new Intent(context,ServiceDetailActivity.class)
                        .putExtra("Carrinho", shoppingCart)
                        .putExtra("Cliente", customer)
                        .putExtra("Veiculo", selectedVehicle)
                        .putExtra("Servico", selectedService)
                );

            }
        });

    }

    private void doLoadServicos(){

        if(services == null){

            Log.d("INFO","Load Servicos from webservice");

            TaskLoadServicos taskLoadServices = new TaskLoadServicos(context, new AsyncResponse<List<Service>>() {

                @Override
                public void processFinish(List<Service> output) {

                    services = output;

                    doFillData(services);
                }
            });

            taskLoadServices.execute();

        }


    }

    private void doFillData(List<Service> servicos){

        ArrayAdapter<Service> adpItem = new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, servicos);

        listView.setAdapter(adpItem);
    }



}
