package br.com.frmichetti.carhollics.android.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import br.com.frmichetti.carhollics.android.R;
import br.com.frmichetti.carhollics.android.jobs.AsyncResponse;
import br.com.frmichetti.carhollics.android.jobs.TaskCreateCliente;
import br.com.frmichetti.carhollics.android.model.Cep;
import br.com.frmichetti.carhollics.android.model.Cliente;
import br.com.frmichetti.carhollics.android.model.Endereco;
import br.com.frmichetti.carhollics.android.model.Usuario;

public class ClientActivity extends BaseActivity{

    private Button buttonConfirmar;

    private EditText editTextNome,editTextCPF,editTextTelefone,editTextCEP,editTextComplemento,editTextNumero;

    private Usuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_client);

        doCastComponents();

        doCreateListeners();

    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {

        super.onPostCreate(savedInstanceState);

        doConfigure();

        cliente = (Cliente) intent.getSerializableExtra("Cliente");

        usuario = (Usuario) intent.getSerializableExtra("Usuario");

        doFillData(cliente);

    }

    @Override
    public void doCastComponents() {

        editTextNome = (EditText) findViewById(R.id.editTextNome);

        editTextCPF = (EditText) findViewById(R.id.editTextCPF);

        editTextTelefone = (EditText) findViewById(R.id.editTextTelefone);

        editTextCEP = (EditText) findViewById(R.id.editTextCEP);

        editTextComplemento = (EditText) findViewById(R.id.editTextComplemento);

        editTextNumero = (EditText) findViewById(R.id.editTextNumero);

        buttonConfirmar = (Button) findViewById(R.id.buttonConfirmar);

    }

    @Override
    public void doCreateListeners() {

        buttonConfirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TaskCreateCliente taskCreateCliente = new TaskCreateCliente(context, new AsyncResponse<Cliente>() {

                    @Override
                    public void processFinish(Cliente output) {

                        cliente = output;

                        //TODO FIXME getCliente

                        startActivity(new Intent(context,MainActivity.class).putExtra("Cliente",cliente));

                        finish();

                    }
                });

                taskCreateCliente.execute(cliente);



            }
        });

    }

    private Cliente doFillData(Cliente cliente) {

       if (cliente != null) {

           editTextNome.setText(cliente.getNome());

           editTextCPF.setText(String.valueOf(cliente.getCpf()));

           editTextCEP.setText(String.valueOf(cliente.getEndereco().getCep().getCep()));

           editTextComplemento.setText(String.valueOf(cliente.getEndereco().getComplemento()));

           editTextNumero.setText(String.valueOf(cliente.getEndereco().getNumero()));

           editTextTelefone.setText(String.valueOf(cliente.getTelefone()));

       }else{

           editTextNome.setText(user.getDisplayName());

           cliente = new Cliente();

           Endereco e  = new Endereco();

           Cep cep  = new Cep();

           e.setCep(cep);

           e.setComplemento(editTextComplemento.getText().toString());

           e.setNumero(editTextNumero.getText().toString());

           cliente.setNome(editTextNome.getText().toString());

           cliente.setCpf(Long.valueOf(editTextCPF.getText().toString()));

           cep.setCep(Long.valueOf(editTextCEP.getText().toString()));

           cliente.setEndereco(e);

           cliente.setTelefone(Long.valueOf(editTextTelefone.getText().toString()));

           if(cliente.getUsuario()==null){

               cliente.setUsuario(usuario);

           }

       }

        return cliente;

    }

    @Override
    public void doConfigure() {

        super.doConfigure();

        actionBar.setSubtitle("Cadastro de Cliente");

    }

}
