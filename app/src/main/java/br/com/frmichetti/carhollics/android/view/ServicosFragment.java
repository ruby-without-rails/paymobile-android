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

import java.util.List;

import br.com.frmichetti.carhollics.android.R;
import br.com.frmichetti.carhollics.android.jobs.AsyncResponse;
import br.com.frmichetti.carhollics.android.jobs.TaskLoadServicos;
import br.com.frmichetti.carhollics.android.model.Carrinho;
import br.com.frmichetti.carhollics.android.model.Cliente;
import br.com.frmichetti.carhollics.android.model.Servico;
import br.com.frmichetti.carhollics.android.model.Veiculo;


public class ServicosFragment extends Fragment {

    private Context context;

    private Intent intent;

    private TextView textView;

    private ListView listView;

    private List<Servico> servicos;

    private Servico servicoSelecionado;

    private Veiculo veiculoSelecionado;

    private Cliente cliente;

    private Carrinho carrinho;

    public ServicosFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        doConfigure();

        doLoadExtras();

        if(carrinho == null){

            carrinho = new Carrinho();
        }

        if(servicoSelecionado == null){

            servicoSelecionado = new Servico();
        }

        if(veiculoSelecionado == null){

            veiculoSelecionado = new Veiculo();
        }

    }

    private void doConfigure() {

        context = getContext();

        intent = getActivity().getIntent();
    }

    private void doLoadExtras() {

        cliente = (Cliente) intent.getSerializableExtra("Cliente");

        carrinho = (Carrinho) intent.getSerializableExtra("Carrinho");

        servicoSelecionado = (Servico) intent.getSerializableExtra("Servico");

        veiculoSelecionado = (Veiculo) intent.getSerializableExtra("Veiculo");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_servicos, container, false);

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

                servicoSelecionado = (Servico) itemValue;

                startActivity(new Intent(context,ServicoDetailActivity.class)
                        .putExtra("Carrinho",carrinho)
                        .putExtra("Cliente",cliente)
                        .putExtra("Veiculo",veiculoSelecionado)
                        .putExtra("Servico",servicoSelecionado)
                );

            }
        });

    }

    private void doLoadServicos(){

        Log.d("INFO","Load Servicos from webservice");

        TaskLoadServicos taskLoadServices = new TaskLoadServicos(context, new AsyncResponse<List<Servico>>() {

            @Override
            public void processFinish(List<Servico> output) {

                servicos = output;

                doFillData(servicos);
            }
        });

        taskLoadServices.execute();
    }

    private void doFillData(List<Servico> servicos){

        ArrayAdapter<Servico> adpItem = new ArrayAdapter<Servico>(context, android.R.layout.simple_list_item_1, servicos);

        listView.setAdapter(adpItem);
    }



}
