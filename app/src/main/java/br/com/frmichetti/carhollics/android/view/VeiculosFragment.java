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
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import br.com.frmichetti.carhollics.android.R;
import br.com.frmichetti.carhollics.android.jobs.AsyncResponse;
import br.com.frmichetti.carhollics.android.jobs.TaskLoadVeiculos;
import br.com.frmichetti.carhollics.android.model.Carrinho;
import br.com.frmichetti.carhollics.android.model.Cliente;
import br.com.frmichetti.carhollics.android.model.Servico;
import br.com.frmichetti.carhollics.android.model.Veiculo;


public class VeiculosFragment extends Fragment {

    private Context context;

    private Intent intent;

    private TextView textView;

    private ListView listView;

    private Button button;

    private List<Veiculo> veiculos;

    private Servico servicoSelecionado;

    private Veiculo veiculoSelecionado;

    private Cliente cliente;

    private Carrinho carrinho;

    public VeiculosFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        doConfigure();

        doLoadExtras();

    }

    private void doLoadExtras() {

        cliente = (Cliente) intent.getSerializableExtra("Cliente");

        carrinho = (Carrinho) intent.getSerializableExtra("Carrinho");

        servicoSelecionado = (Servico) intent.getSerializableExtra("Servico");

        veiculoSelecionado = (Veiculo) intent.getSerializableExtra("Veiculo");
    }

    private void doConfigure() {

        context = getContext();

        intent = getActivity().getIntent();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_veiculos, container, false);

        doCastComponents(rootView);

        doCreateListeners();

        doLoadVeiculos();

        // Inflate the layout for this fragment
        return rootView;
    }

    private void doCreateListeners() {

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                int itemPosition = position;

                Object itemValue = listView.getItemAtPosition(position);

                veiculoSelecionado = (Veiculo) itemValue;

                Toast.makeText(context, "Veículo Selecionado " + veiculoSelecionado, Toast.LENGTH_SHORT).show();

            /*    startActivity(new Intent(context, ServicoDetailActivity.class)
                        .putExtra("Carrinho",carrinho)
                        .putExtra("Cliente",cliente)
                        .putExtra("Veiculo",veiculoSelecionado)
                        .putExtra("Servico",servicoSelecionado)
                );*/

            }
        });

        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Toast.makeText(context,"Implementar",Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void doCastComponents(View rootView) {

        textView = (TextView) rootView.findViewById(R.id.textView14);

        listView = (ListView) rootView.findViewById(R.id.listViewVeiculos);

        button = (Button) rootView.findViewById(R.id.buttonCadastrarVeiculo);


    }

    public void doLoadVeiculos(){

        Log.d("INFO","Load Servicos from webservice");

        TaskLoadVeiculos taskLoadVeiculos = new TaskLoadVeiculos(context, new AsyncResponse<List<Veiculo>>() {

            @Override
            public void processFinish(List<Veiculo> output) {

                veiculos = output;

                doFillData(veiculos);
            }
        });

        taskLoadVeiculos.execute();
    }

    private void doFillData(List<Veiculo> veiculos){

        ArrayAdapter<Veiculo> adpItem = new ArrayAdapter<Veiculo>(context, android.R.layout.simple_list_item_1, veiculos);

        listView.setAdapter(adpItem);
    }


}