package br.com.frmichetti.carhollics.android.view;


import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import br.com.frmichetti.carhollics.android.R;

import br.com.frmichetti.carhollics.android.model.Carrinho;
import br.com.frmichetti.carhollics.android.model.Cliente;
import br.com.frmichetti.carhollics.android.model.ItemCarrinho;
import br.com.frmichetti.carhollics.android.model.Servico;

public class SimpleDetailActivity extends AppCompatActivity implements MyPattern {

    private ActionBar actionBar;

    private Context context;

    private Intent intent;

    private TextView textViewNome,textViewDescricao,textViewDuracao,textViewPreço;

    private Button buttonAddCart;

    private Servico servicoSelecionado;

    private Cliente cliente;

    private Carrinho carrinho;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_simple_detail);

    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {

        super.onPostCreate(savedInstanceState);

        doConfigure();

        getExtras(intent);

        doCastComponents();

        doCreateListeners();

        doFillData();

    }

    @Override
    public void doConfigure() {

        context = this;

        intent = getIntent();

        actionBar = getSupportActionBar();

        actionBar.setDisplayHomeAsUpEnabled(true);

        actionBar.setTitle(R.string.app_name);

        actionBar.setSubtitle("Detalhes do Serviço");

    }

    @Override
    public void doCastComponents() {

        textViewNome = (TextView) findViewById(R.id.textViewNome);

        textViewDescricao = (TextView) findViewById(R.id.textViewDescricaoVar);

        textViewDuracao = (TextView) findViewById(R.id.textViewDuracaoVar);

        textViewPreço = (TextView) findViewById(R.id.textViewPrecoUnitVar);

        buttonAddCart = (Button) findViewById(R.id.buttonAddCart);

    }

    @Override
    public void doCreateListeners() {

        buttonAddCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                carrinho.add(new ItemCarrinho(servicoSelecionado));

                intent = new Intent();

                putExtras(intent);

                intent.setClass(context,CartActivity.class);

                context.startActivity(intent);


            }
        });

    }



    @Override
    public void getExtras(Intent intent) {

        carrinho = (Carrinho) intent.getSerializableExtra("Carrinho");

        servicoSelecionado = (Servico) intent.getSerializableExtra("Servico");

        cliente = (Cliente) intent.getSerializableExtra("Cliente");


    }

    public void putExtras(Intent intent){

        intent.putExtra("Carrinho",carrinho);

        intent.putExtra("Servico",servicoSelecionado);

        intent.putExtra("Cliente",cliente);
    }


    private void doFillData() {

        textViewNome.setText(servicoSelecionado.getNome());

        textViewDescricao.setText(servicoSelecionado.getDescricao());

        textViewDuracao.setText(String.valueOf(servicoSelecionado.getDuracao()));

        textViewPreço.setText(String.valueOf(servicoSelecionado.getPreco()));
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
