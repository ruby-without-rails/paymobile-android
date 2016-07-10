package br.com.frmichetti.carhollics.android.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import br.com.frmichetti.carhollics.android.R;
import br.com.frmichetti.carhollics.android.jobs.AsyncResponse;
import br.com.frmichetti.carhollics.android.jobs.TaskLoadServicos;
import br.com.frmichetti.carhollics.android.model.Cliente;
import br.com.frmichetti.carhollics.android.model.Servico;
import br.com.frmichetti.carhollics.android.model.Veiculo;

public class SimpleMainActivity extends BaseActivity{

    private TextView textView;

    private Button buttonVeiculos,buttonCadastro;

    private ListView listView;

    private List<Servico> servicos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_simple_main);

        doCastComponents();

        doCreateListeners();
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {

        super.onPostCreate(savedInstanceState);

        doConfigure();

        cliente = (Cliente) intent.getSerializableExtra("Cliente");

        veiculoSelecionado = (Veiculo) intent.getSerializableExtra("Veiculo");

        textView.setText("Bem Vindo " + cliente.getNome());

        doLoadServices();

    }


    @Override
    public void doConfigure() {

        super.doConfigure();

        actionBar.setSubtitle("Principal");

    }



    @Override
    public void doCastComponents() {

        textView = (TextView) findViewById(R.id.textViewItemSelecionadoVar);

        listView = (ListView) findViewById(R.id.listViewCarrinho);

        buttonCadastro = (Button) findViewById(R.id.buttonCadastro);

        buttonVeiculos = (Button) findViewById(R.id.buttonVeiculos);

    }

    @Override
    public void doCreateListeners() {

        buttonCadastro.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                startActivity(new Intent(context,ClientActivity.class)

                );

                finish();

            }
        });

        buttonVeiculos.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                finish();

            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                int itemPosition = position;

                Object itemValue = listView.getItemAtPosition(position);

                servicoSelecionado = (Servico) itemValue;

                Toast.makeText(context,"Serviço Selecionado " + servicoSelecionado,Toast.LENGTH_SHORT);


            }
        });
    }

     public void doLoadServices(){

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

        ArrayAdapter<Servico> adpItem = new ArrayAdapter<Servico>(this, android.R.layout.simple_list_item_1, servicos);

        listView.setAdapter(adpItem);
    }


}
