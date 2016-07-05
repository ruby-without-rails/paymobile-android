package br.com.frmichetti.carhollics.android;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import br.com.frmichetti.carhollics.android.R;

import br.com.frmichetti.carhollics.android.jobs.AsyncResponse;
import br.com.frmichetti.carhollics.android.jobs.TaskLoadServices;
import br.com.frmichetti.carhollics.android.model.Carrinho;
import br.com.frmichetti.carhollics.android.model.Cliente;
import br.com.frmichetti.carhollics.android.model.Servico;

import java.util.List;

public class SimpleMainActivity extends AppCompatActivity implements MyPattern{

    private ActionBar actionBar;

    private Context context;

    private Intent intent;

    private TextView textView;

    private ListView listView;

    private TaskLoadServices taskLoadServices;

    private List<Servico> servicos;

    private Cliente cliente;

    private Carrinho carrinho;

    private Servico servicoSelecionado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_simple_main);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {

        super.onPostCreate(savedInstanceState);

        doConfigure();

        getExtras(intent);

        doCastComponents();

        doCreateListeners();

        doLoadServices();

        textView.setText("Bem Vindo " + cliente.getNome());

        Log.i("INFO","Load Services from webservice");


    }

    @Override
    public void doConfigure() {

        context = this;

        intent = getIntent();

        actionBar = getSupportActionBar();

        actionBar.setDisplayHomeAsUpEnabled(true);

        actionBar.setTitle(R.string.app_name);

        actionBar.setSubtitle("Principal");

    }

    @Override
    public void getExtras(Intent intent){

    carrinho = (Carrinho) intent.getSerializableExtra("Carrinho");

        if(carrinho == null){
            carrinho = new Carrinho();
        }

    cliente = (Cliente) intent.getSerializableExtra("Cliente");

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

        taskLoadServices = new TaskLoadServices(context, new AsyncResponse<List<Servico>>() {
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



    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {

        Log.i("KEY",String.valueOf(event.getKeyCode()));

        if(event.getKeyCode()==KeyEvent.KEYCODE_BACK){

            finish();

        }

        if(event.getKeyCode()==KeyEvent.KEYCODE_HOME){

            Log.i("Info","apertou Home");
        }

        if(event.getKeyCode()== KeyEvent.KEYCODE_SEARCH){

            Log.i("Info","apertou Search");
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if(id == android.R.id.home){
            finish();
        }

        return super.onOptionsItemSelected(item);
    }



}
