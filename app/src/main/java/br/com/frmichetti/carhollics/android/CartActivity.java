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
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import br.com.frmichetti.carhollics.android.R;

import br.com.frmichetti.carhollics.android.model.Carrinho;
import br.com.frmichetti.carhollics.android.model.Cliente;
import br.com.frmichetti.carhollics.android.model.ItemCarrinho;
import br.com.frmichetti.carhollics.android.model.Servico;

import java.util.ArrayList;

public class CartActivity extends AppCompatActivity implements MyPattern{

    private ActionBar actionBar;

    private Context context;

    private Intent intent;

    private Carrinho carrinho;

    private Cliente cliente;

    private Servico servicoSelecionado;

    private ItemCarrinho itemCarrinhoSelecionado;

    private Button buttonRemoverItem, buttonConfirmarCompra,buttonContinuarComprando;

    private TextView textViewItemSelecionado,textViewQuantidade,textViewPreco,textViewSubTotal,textViewTotal;

    private ListView listViewCarrinho;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_cart);

    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {

        super.onPostCreate(savedInstanceState);

        doConfigure();

        getExtras(intent);

        doCastComponents();

        doCreateListeners();

        doFillData();

        doRefresh();

    }

    private void doRefresh() {

        textViewItemSelecionado.setText(itemCarrinhoSelecionado.toString());

        textViewSubTotal.setText(String.valueOf(carrinho.getTotal(itemCarrinhoSelecionado)));

        textViewTotal.setText(String.valueOf(carrinho.getTotal()));

      //  textViewPreco.setText(String.valueOf(itemCarrinhoSelecionado.getPrice()));

 //       textViewQuantidade.setText(String.valueOf(carrinho.getQuantity()));
    }

    private void doFillData() {

        itemCarrinhoSelecionado = new ItemCarrinho(servicoSelecionado);

        ArrayAdapter<ItemCarrinho> adpItem = new ArrayAdapter<ItemCarrinho>(this, android.R.layout.simple_list_item_1, new ArrayList<ItemCarrinho>(carrinho.getList()));

        listViewCarrinho.setAdapter(adpItem);

    }

    @Override
    public void doCastComponents() {

        buttonRemoverItem = (Button) findViewById(R.id.buttonRemoverItem);

        buttonConfirmarCompra = (Button) findViewById(R.id.buttonConfirmarCompra);

        buttonContinuarComprando = (Button) findViewById(R.id.buttonContinuarComprando);

        listViewCarrinho = (ListView) findViewById(R.id.listViewCarrinho);

        textViewItemSelecionado = (TextView) findViewById(R.id.textViewItemSelecionadoVar);

        textViewQuantidade = (TextView) findViewById(R.id.textViewQuantidadeVar);

        textViewPreco = (TextView) findViewById(R.id.textViewPrecoUnitVar);

        textViewSubTotal = (TextView) findViewById(R.id.textViewSubtotalVar);

        textViewTotal = (TextView) findViewById(R.id.textViewTotalVar);


    }

    @Override
    public void doCreateListeners() {

        listViewCarrinho.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                int itemPosition = position;

                Object itemValue = listViewCarrinho.getItemAtPosition(position);

                itemCarrinhoSelecionado = (ItemCarrinho) itemValue;

                Toast.makeText(context,"Servico Selecionado " + itemCarrinhoSelecionado.toString(),Toast.LENGTH_SHORT).show();

                doRefresh();

            }
        });

        buttonRemoverItem.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                carrinho.remove(itemCarrinhoSelecionado);

                Toast.makeText(context,"Removido " + itemCarrinhoSelecionado.toString(),Toast.LENGTH_SHORT).show();

                doFillData();

                textViewItemSelecionado.setText("");


            }
        });

        buttonConfirmarCompra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //doChangeActivity();
            }
        });

        buttonContinuarComprando.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doChangeActivity(SimpleMainActivity.class);
            }
        });
    }

    @Override
    public void doConfigure() {

        context = this;

        intent = getIntent();

        actionBar = getSupportActionBar();

        actionBar.setDisplayHomeAsUpEnabled(true);

        actionBar.setTitle(R.string.app_name);

        actionBar.setSubtitle("Carrinho");

    }

    @Override
    public void getExtras(Intent intent) {

        cliente = (Cliente) intent.getSerializableExtra("Cliente");

        carrinho = (Carrinho) intent.getSerializableExtra("Carrinho");

        servicoSelecionado = (Servico) intent.getSerializableExtra("Servico");

    }

    public void putExtras(Intent intent){

        intent.putExtra("Cliente",cliente);

        intent.putExtra("Carrinho",carrinho);

        intent.putExtra("Servico",servicoSelecionado);
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

    private void doChangeActivity(Class classe){

        intent = new Intent();

        putExtras(intent);

        intent.setClass(context,classe);

        context.startActivity(intent);
    }


}
