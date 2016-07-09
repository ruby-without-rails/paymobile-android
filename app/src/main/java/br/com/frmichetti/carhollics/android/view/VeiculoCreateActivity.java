package br.com.frmichetti.carhollics.android.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import br.com.frmichetti.carhollics.android.R;
import br.com.frmichetti.carhollics.android.jobs.AsyncResponse;
import br.com.frmichetti.carhollics.android.jobs.TaskCreateVeiculo;
import br.com.frmichetti.carhollics.android.model.Veiculo;

public class VeiculoCreateActivity extends BaseActivity {

    private EditText editTextNomeVeiculo,editTextMarcaVeiculo;

    private Button buttonCadastrarVeiculo;

    private Veiculo veiculo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_veiculo_create);

    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {

        super.onPostCreate(savedInstanceState);

    }

    @Override
    public void doCastComponents() {

        editTextNomeVeiculo = (EditText) findViewById(R.id.editTextNomeVeículo);

        editTextMarcaVeiculo = (EditText) findViewById(R.id.editTextMarcaVeiculo);

        buttonCadastrarVeiculo = (Button) findViewById(R.id.buttonCadastrarVeiculo);

    }

    @Override
    public void doCreateListeners() {

        buttonCadastrarVeiculo.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                TaskCreateVeiculo taskCreateVeiculo = new TaskCreateVeiculo(context, new AsyncResponse<Veiculo>() {
                    @Override
                    public void processFinish(Veiculo output) {

                        //TODO FIXME Logic Here


                    }
                });

                taskCreateVeiculo.execute(veiculo);


            }
        });

    }

    @Override
    public void doConfigure() {

        super.doConfigure();

        actionBar.setSubtitle("Cadastrar Veículo");

        veiculo = new Veiculo();

    }

    @Override
    public void getExtras(Intent intent) {

    }

    private Veiculo doFillData(){

        //TODO FIXME Logic Here
        return null;

    }
}
