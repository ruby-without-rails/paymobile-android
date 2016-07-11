package br.com.frmichetti.carhollics.android.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import br.com.frmichetti.carhollics.android.R;
import br.com.frmichetti.carhollics.android.jobs.AsyncResponse;
import br.com.frmichetti.carhollics.android.jobs.TaskFazerPedido;
import br.com.frmichetti.carhollics.android.model.ItemCarrinho;
import br.com.frmichetti.carhollics.android.model.Pedido;

public class CartActivity extends BaseActivity {

    private Button buttonRemoverItem, buttonConfirmarCompra,buttonContinuarComprando;

    private TextView textViewItemSelecionado,textViewQuantidade,textViewPreco,textViewSubTotal,textViewTotal;

    private ListView listViewCarrinho;

    private ItemCarrinho itemCarrinhoSelecionado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_cart);

        doCastComponents();

        doCreateListeners();

    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {

        super.onPostCreate(savedInstanceState);

        doConfigure();

        doLoadExtras(intent);

        doFillData();

        doRefresh();

    }

    private void doRefresh() {

        textViewItemSelecionado.setText(itemCarrinhoSelecionado.toString());

        textViewSubTotal.setText(String.valueOf(carrinho.getTotal(itemCarrinhoSelecionado)));

        textViewTotal.setText(String.valueOf(carrinho.getTotal()));

        //  textViewPreco.setText(String.valueOf(itemCarrinhoSelecionado.getPrice()));

        // textViewQuantidade.setText(String.valueOf(carrinho.getQuantity()));
    }

    private void doFillData() {

        itemCarrinhoSelecionado = new ItemCarrinho(servicoSelecionado);

        ArrayAdapter<ItemCarrinho> adpItem = new ArrayAdapter<ItemCarrinho>(this, android.R.layout.simple_list_item_1, new ArrayList<ItemCarrinho>(carrinho.getList()));

        listViewCarrinho.setAdapter(adpItem);

    }


    public void doCastComponents() {

        super.doCastComponents();

        buttonRemoverItem = (Button) findViewById(R.id.buttonRemoverItem);

        buttonConfirmarCompra = (Button) findViewById(R.id.buttonCadastro);

        buttonContinuarComprando = (Button) findViewById(R.id.buttonContinuarComprando);

        listViewCarrinho = (ListView) findViewById(R.id.listViewServicos);

        textViewItemSelecionado = (TextView) findViewById(R.id.textViewItemSelecionadoVar);

        textViewQuantidade = (TextView) findViewById(R.id.textViewQuantidadeVar);

        textViewPreco = (TextView) findViewById(R.id.textViewPrecoUnitVar);

        textViewSubTotal = (TextView) findViewById(R.id.textViewSubtotalVar);

        textViewTotal = (TextView) findViewById(R.id.textViewTotalVar);


    }


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

                //TODO FIXME Activity de Resumo de Compra
                TaskFazerPedido taskFazerPedido = new TaskFazerPedido(context, new AsyncResponse<Boolean>() {

                    @Override
                    public void processFinish(Boolean output) {

                        if(output){

                            Toast.makeText(context, "Pedido Solicitado com sucesso! Entraremos em contato para Confirmar Agendamento", Toast.LENGTH_LONG).show();

                            startActivity(new Intent(context,MainActivity.class)
                                    .putExtra("Cliente",cliente)
                                    .putExtra("Carrinho",carrinho)
                                    .putExtra("Servico",servicoSelecionado)
                                    .putExtra("Veiculo",veiculoSelecionado)
                            );

                            finish();

                        }else{

                            Toast.makeText(context,"Não foi possível concluir o pedido, Tente Novamente ",Toast.LENGTH_LONG).show();

                        }

                    }
                });

                taskFazerPedido.execute(new Pedido(cliente,carrinho));

            }
        });

        buttonContinuarComprando.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                startActivity(new Intent(context,MainActivity.class)
                        .putExtra("Cliente",cliente)
                        .putExtra("Carrinho",carrinho)
                        .putExtra("Servico",servicoSelecionado)
                        .putExtra("Veiculo",veiculoSelecionado) );

                finish();


            }
        });


    }


    public void doConfigure() {

        super.doConfigure();

        actionBar.setSubtitle("Carrinho");

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

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


}