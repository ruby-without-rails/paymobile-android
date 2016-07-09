package br.com.frmichetti.carhollics.android.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
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

public class SimpleMainActivity extends BaseActivity{

    private TextView textView;

    private ListView listView;

    private List<Servico> servicos;

    private Cliente cliente;

    private Carrinho carrinho;

    private Servico servicoSelecionado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        if(savedInstanceState!=null){

            cliente = (Cliente) savedInstanceState.getSerializable("Cliente");

            carrinho = (Carrinho) savedInstanceState.getSerializable("Carrinho");

            servicoSelecionado = (Servico) savedInstanceState.getSerializable("Servico");

            Log.i("[INFO-SAVE-BUNDLE]","Load Saved State");

        }

        setContentView(R.layout.activity_simple_main);

        doCastComponents();

        doCreateListeners();
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {

        super.onPostCreate(savedInstanceState);

        if(savedInstanceState!=null){

            cliente = (Cliente) savedInstanceState.getSerializable("Cliente");

            carrinho = (Carrinho) savedInstanceState.getSerializable("Carrinho");

            servicoSelecionado = (Servico) savedInstanceState.getSerializable("Servico");

            Log.i("[INFO-SAVE-BUNDLE]","Load Saved State");

        }

        getExtras(intent);

        textView.setText("Bem Vindo " + cliente.getNome());

        Log.i("INFO","Load Servicos from webservice");

        doLoadServices();

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {

        super.onSaveInstanceState(outState);

        outState.putSerializable("Cliente",cliente);

        outState.putSerializable("Carrinho",carrinho);

        outState.putSerializable("Servico",servicoSelecionado);

    }

    @Override
    public void doConfigure() {

        super.doConfigure();

        actionBar.setSubtitle("Principal");

    }

    @Override
    public void getExtras(Intent intent){

        carrinho = (Carrinho) intent.getSerializableExtra("Carrinho");

        if(carrinho == null){

            carrinho = new Carrinho();

        }

    cliente = (Cliente) intent.getSerializableExtra("Cliente");

        if(cliente == null){

            new Intent(context,LoginActivity.class);

            super.signOut();

            finish();

            //TODO FIXME Urgent!!!
            Log.d("DEBUG - CLIENTE", "NULL -> Redirect to Login");
        }

    }

    public void putExtras(Intent intent){

        intent.putExtra("Carrinho",carrinho);

        intent.putExtra("Servico",servicoSelecionado);

        intent.putExtra("Cliente",cliente);
    }

    @Override
    public void doCastComponents() {

        textView = (TextView) findViewById(R.id.textViewItemSelecionadoVar);

        listView = (ListView) findViewById(R.id.listViewCarrinho);

    }

    @Override
    public void doCreateListeners() {

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                int itemPosition = position;

                Object itemValue = listView.getItemAtPosition(position);

                servicoSelecionado = (Servico) itemValue;

                intent = new Intent();

                putExtras(intent);

                intent.setClass(context,SimpleDetailActivity.class);

                context.startActivity(intent);

            }
        });
    }

     public void doLoadServices(){

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

        ArrayAdapter<Servico> adpItem = new ArrayAdapter<Servico>(this, android.R.layout.simple_list_item_1, servicos);

        listView.setAdapter(adpItem);
    }

}
