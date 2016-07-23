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
import br.com.frmichetti.carhollics.android.jobs.TaskLoadPedidos;
import br.com.frmichetti.carhollics.json.model.Cliente;
import br.com.frmichetti.carhollics.json.model.Pedido;

public class PedidosFragment extends Fragment {

    private Context context;

    private Intent intent;

    private Pedido pedidoSelecionado;

    private List<Pedido> pedidos;

    private ListView listView;

    private Cliente cliente;

    public PedidosFragment() {

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        if(savedInstanceState != null){
            pedidos = (List<Pedido>) savedInstanceState.getSerializable("Pedidos");
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

        outState.putSerializable("Pedidos", (Serializable) pedidos);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if(savedInstanceState != null){
            pedidos = (List<Pedido>) savedInstanceState.getSerializable("Pedidos");
        }

        View rootView = inflater.inflate(R.layout.fragment_pedidos, container, false);

        doCastComponents(rootView);

        doCreateListeners();

        doLoadExtras(intent);

        doLoadPedidos();

        // Inflate the layout for this fragment
        return rootView;
    }

    private void doLoadExtras(Intent intent) {

        cliente = (Cliente) intent.getSerializableExtra("Cliente");

    }

    private void doCastComponents(View rootView) {

        listView = (ListView) rootView.findViewById(R.id.listViewPedidos);

    }

    private void doLoadPedidos() {

        if(pedidos == null){
            Log.d("INFO","Load Pedidos from webservice");

            TaskLoadPedidos taskLoadPedidos = new TaskLoadPedidos(context, new AsyncResponse<List<Pedido>>() {

                @Override
                public void processFinish(List<Pedido> output) {

                    pedidos = output;

                    doFillData(pedidos);
                }
            });

            taskLoadPedidos.execute(cliente);
        }




    }

    private void doFillData(List<Pedido> pedidos) {

        ArrayAdapter<Pedido> adpItem = new ArrayAdapter<Pedido>(context, android.R.layout.simple_list_item_1, pedidos);

        listView.setAdapter(adpItem);
    }

    private void doCreateListeners() {

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                int itemPosition = position;

                Object itemValue = listView.getItemAtPosition(position);

                pedidoSelecionado = (Pedido) itemValue;

                Toast.makeText(context, "Pedido Selecionado " + pedidoSelecionado.toString(), Toast.LENGTH_SHORT).show();

            }
        });

    }

}
