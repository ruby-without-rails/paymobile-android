package br.com.frmichetti.carhollics.android.view;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import br.com.frmichetti.carhollics.android.R;
import br.com.frmichetti.carhollics.android.model.ItemCarrinho;

public class ServicoDetailActivity extends BaseActivity{

    private TextView textViewNome,textViewDescricao,textViewDuracao,textViewPreço;

    private Button buttonAddCart;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_servico_detail);

        doCastComponents();

        doCreateListeners();


    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {

        super.onPostCreate(savedInstanceState);

        doConfigure();

        doLoadExtras(intent);

        doFillData();

    }


    public void doConfigure() {

        super.doConfigure();

        actionBar.setSubtitle("Detalhes do Serviço");

    }


    public void doCastComponents() {

        super.doCastComponents();

        textViewNome = (TextView) findViewById(R.id.textViewNome);

        textViewDescricao = (TextView) findViewById(R.id.textViewDescricaoVar);

        textViewDuracao = (TextView) findViewById(R.id.textViewDuracaoVar);

        textViewPreço = (TextView) findViewById(R.id.textViewPrecoUnitVar);

        buttonAddCart = (Button) findViewById(R.id.buttonAddCart);


    }

    public void doCreateListeners() {

        buttonAddCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

              carrinho.add(new ItemCarrinho(servicoSelecionado));

               startActivity(new Intent(context,CartActivity.class)
                       .putExtra("Cliente",cliente)
                       .putExtra("Carrinho",carrinho)
                       .putExtra("Servico",servicoSelecionado)
                       .putExtra("Veiculo",veiculoSelecionado)

               );

                finish();


            }
        });

    }

    private void doFillData() {

        textViewNome.setText(servicoSelecionado.getNome());

        textViewDescricao.setText(servicoSelecionado.getDescricao());

        textViewDuracao.setText(String.valueOf(servicoSelecionado.getDuracao()));

        textViewPreço.setText(String.valueOf(servicoSelecionado.getPreco()));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        super.onOptionsItemSelected(item);

        int id = item.getItemId();

        if(id == android.R.id.home){

            Toast.makeText(context,"Click on Back Button ",Toast.LENGTH_SHORT).show();

            finish();

            return true;

        }

        if (id == R.id.action_settings) {

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {

        super.onKeyUp(keyCode,event);

        if(event.getKeyCode() == KeyEvent.KEYCODE_BACK){



        }

        if(event.getKeyCode() == KeyEvent.KEYCODE_HOME){


        }

        if(event.getKeyCode() == KeyEvent.KEYCODE_SEARCH){


        }

        return true;
    }




}
