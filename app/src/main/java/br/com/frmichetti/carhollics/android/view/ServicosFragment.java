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
import android.widget.Toast;

import java.util.List;

import br.com.frmichetti.carhollics.android.R;
import br.com.frmichetti.carhollics.android.jobs.AsyncResponse;
import br.com.frmichetti.carhollics.android.jobs.TaskLoadServicos;
import br.com.frmichetti.carhollics.android.model.Carrinho;
import br.com.frmichetti.carhollics.android.model.Cliente;
import br.com.frmichetti.carhollics.android.model.Servico;


public class ServicosFragment extends Fragment {

    private TextView textView;

    private ListView listView;

    private List<Servico> servicos;

    private Servico servicoSelecionado;

    private Context context;

    private Intent intent;

    private Cliente cliente;

    private Carrinho carrinho;

    public ServicosFragment() {
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
    public void onResume() {

        super.onResume();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_servicos, container, false);

        textView = (TextView) rootView.findViewById(R.id.textViewBemVindo);

        listView = (ListView) rootView.findViewById(R.id.listViewServicos);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                int itemPosition = position;

                Object itemValue = listView.getItemAtPosition(position);

                servicoSelecionado = (Servico) itemValue;

                Toast.makeText(context,"Serviço Selecionado " + servicoSelecionado,Toast.LENGTH_SHORT).show();

                //TODO INTENT

                context.startActivity(new Intent(context,ServicoDetailActivity.class)
                        .putExtra("Servico",servicoSelecionado)
                        .putExtra("Carrinho",carrinho)
                        .putExtra("Cliente",cliente)
                );

                getActivity().finish();

            }
});

        doLoadServicos();


        // Inflate the layout for this fragment
        return rootView;
    }

    public void doLoadServicos(){

        Log.i("INFO","Load Servicos from webservice");

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
