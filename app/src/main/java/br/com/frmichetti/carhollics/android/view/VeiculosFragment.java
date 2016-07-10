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
import br.com.frmichetti.carhollics.android.model.Veiculo;


public class VeiculosFragment extends Fragment {

    private TextView textView;

    private ListView listView;

    private Button button;

    private List<Veiculo> veiculos;

    private Veiculo veiculoSelecionado;

    private Context context;

    private Intent intent;

    private Cliente cliente;

    private Carrinho carrinho;

    public VeiculosFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);


        context = getContext();

        intent = getActivity().getIntent();

        carrinho = (Carrinho) intent.getSerializableExtra("Carrinho");

        cliente = (Cliente) intent.getSerializableExtra("Cliente");

        if (carrinho == null){

            carrinho = new Carrinho();
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_veiculos, container, false);

        textView = (TextView) rootView.findViewById(R.id.textView14);

        listView = (ListView) rootView.findViewById(R.id.listViewVeiculos);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                int itemPosition = position;

                Object itemValue = listView.getItemAtPosition(position);

                veiculoSelecionado = (Veiculo) itemValue;

                Toast.makeText(context, "Veículo Selecionado " + veiculoSelecionado, Toast.LENGTH_SHORT).show();

                //TODO INTENT

                context.startActivity(new Intent(context, ServicoDetailActivity.class)
                        .putExtra("Veiculo", veiculoSelecionado)
                        .putExtra("Carrinho", carrinho)
                        .putExtra("Cliente", cliente)
                );

                getActivity().finish();

            }
        });


        doLoadVeiculos();


        // Inflate the layout for this fragment
        return rootView;
    }

    public void doLoadVeiculos(){

        Log.i("INFO","Load Servicos from webservice");

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