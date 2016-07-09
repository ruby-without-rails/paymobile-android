package br.com.frmichetti.carhollics.android.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import br.com.frmichetti.carhollics.android.R;
import br.com.frmichetti.carhollics.android.jobs.AsyncResponse;
import br.com.frmichetti.carhollics.android.jobs.TaskLoadVeiculos;
import br.com.frmichetti.carhollics.android.model.Veiculo;

public class VeiculoActivity extends BaseActivity{

    private ListView listView;

    private Button button;

    private Veiculo veiculoSelecionado;

    private List<Veiculo> veiculos;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_veiculo);

        doCastComponents();

        doCreateListeners();
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {

        super.onPostCreate(savedInstanceState);

        doConfigure();

        doLoadServices();

    }

    @Override
    public void doCastComponents() {

        listView = (ListView) findViewById(R.id.listViewVeiculo);

        button = (Button) findViewById(R.id.buttonAdicionarVeiculo);

    }

    @Override
    public void doCreateListeners() {

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                int itemPosition = position;

                Object itemValue = listView.getItemAtPosition(position);

                veiculoSelecionado = (Veiculo) itemValue;

                Toast.makeText(context,"Veículo Selecionado " + veiculoSelecionado.toString(),Toast.LENGTH_SHORT).show();


            }
        });

        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                startActivity(new Intent(context,VeiculoCreateActivity.class));

            }
        });
    }


    @Override
    public void doConfigure() {

        context = this;

        actionBar = getSupportActionBar();

        actionBar.setTitle(R.string.app_name);

        actionBar.setSubtitle("Lista de Veículo");

        actionBar.setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public void getExtras(Intent intent) {

    }

    public void doLoadServices(){

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
