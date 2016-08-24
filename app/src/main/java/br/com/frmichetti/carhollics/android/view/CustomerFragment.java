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
import br.com.frmichetti.carhollics.android.jobs.TaskLoadCheckouts;
import br.com.frmichetti.carhollics.android.model.Checkout;
import br.com.frmichetti.carhollics.android.model.Customer;

public class CustomerFragment extends Fragment {

    private Context context;

    private Intent intent;

    private Checkout pedidoSelecionado;

    private List<Checkout> checkouts;

    private ListView listView;

    private Customer customer;

    public CustomerFragment() {

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        if(savedInstanceState != null){
            checkouts = (List<Checkout>) savedInstanceState.getSerializable("Pedidos");
        }

        doConfigure();

    }

    private void doConfigure() {

        context = getContext();

        intent = getActivity().getIntent();

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {

        super.onSaveInstanceState(outState);

        outState.putSerializable("Pedidos", (Serializable) checkouts);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if(savedInstanceState != null){
            checkouts = (List<Checkout>) savedInstanceState.getSerializable("Pedidos");
        }

        View rootView = inflater.inflate(R.layout.fragment_customer, container, false);

        doCastComponents(rootView);

        doCreateListeners();

        doLoadExtras(intent);

        doLoadPedidos();

        // Inflate the layout for this fragment
        return rootView;
    }

    private void doLoadExtras(Intent intent) {

        customer = (Customer) intent.getSerializableExtra("Cliente");

    }

    private void doCastComponents(View rootView) {

        listView = (ListView) rootView.findViewById(R.id.listViewPedidos);

    }

    private void doLoadPedidos() {

        if(checkouts == null){

            Log.d("INFO","Load Pedidos from webservice");

            TaskLoadCheckouts taskLoadPedidos = new TaskLoadCheckouts(context, new AsyncResponse<List<Checkout>>() {

                @Override
                public void processFinish(List<Checkout> output) {

                    checkouts = output;

                    doFillData(checkouts);
                }
            });

            taskLoadPedidos.execute(customer);
        }




    }

    private void doFillData(List<Checkout> pedidos) {

        ArrayAdapter<Checkout> adpItem = new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, pedidos);

        listView.setAdapter(adpItem);
    }

    private void doCreateListeners() {

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                int itemPosition = position;

                Object itemValue = listView.getItemAtPosition(position);

                pedidoSelecionado = (Checkout) itemValue;

                Toast.makeText(context, "Pedido Selecionado " + pedidoSelecionado.toString(), Toast.LENGTH_SHORT).show();

            }
        });

    }

}
