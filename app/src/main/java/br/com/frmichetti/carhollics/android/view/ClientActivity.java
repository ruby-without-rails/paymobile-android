package br.com.frmichetti.carhollics.android.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import br.com.frmichetti.carhollics.android.R;
import br.com.frmichetti.carhollics.android.jobs.AsyncResponse;
import br.com.frmichetti.carhollics.android.jobs.TaskCreateCliente;
import br.com.frmichetti.carhollics.android.model.Cep;
import br.com.frmichetti.carhollics.android.model.Cliente;
import br.com.frmichetti.carhollics.android.model.Endereco;
import br.com.frmichetti.carhollics.android.model.Usuario;

public class ClientActivity extends BaseActivity{

    private Button buttonConfirmar,buttonPular;

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

        doLoadExtras(intent);

        doLoadCliente(cliente);

    }

    @Override
    public void doCastComponents() {

        super.doCastComponents();

        editTextNome = (EditText) findViewById(R.id.editTextNome);

        editTextCPF = (EditText) findViewById(R.id.editTextCPF);

        editTextTelefone = (EditText) findViewById(R.id.editTextTelefone);

        editTextCEP = (EditText) findViewById(R.id.editTextCEP);

        editTextComplemento = (EditText) findViewById(R.id.editTextComplemento);

        editTextNumero = (EditText) findViewById(R.id.editTextNumero);

        buttonConfirmar = (Button) findViewById(R.id.buttonConfirmar);

        buttonPular = (Button) findViewById(R.id.buttonPreencherDepois);

    }

    @Override
    public void doCreateListeners() {

        buttonPular.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Toast.makeText(context,"Implementar",Toast.LENGTH_SHORT).show();

            }
        });

        buttonConfirmar.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {

                TaskCreateCliente taskCreateCliente = new TaskCreateCliente(context, new AsyncResponse<Cliente>() {

                    @Override
                    public void processFinish(Cliente output) {

                        cliente = output;

                        //TODO FIXME getCliente

                        startActivity(new Intent(context,MainActivity.class)
                                        .putExtra("Carrinho",carrinho)
                                        .putExtra("Cliente",cliente)
                                        .putExtra("Veiculo",veiculoSelecionado)
                                        .putExtra("Servico",servicoSelecionado));

                        finish();



                    }
                });

                cliente = doLoadFields();

                cliente.setUsuario(usuario);

                if(usuario != null){

                    taskCreateCliente.execute(cliente);

                }else{

                    Toast.makeText(context,"Implementar Atualização do Cliente",Toast.LENGTH_SHORT).show();

                }





            }
        });

    }

    private Cliente doLoadCliente(Cliente cliente) {

       if (cliente != null) {

           editTextNome.setText(cliente.getNome());

           editTextCPF.setText(String.valueOf(cliente.getCpf()));

           editTextCEP.setText(String.valueOf(cliente.getEndereco().getCep().getCep()));

           editTextComplemento.setText(String.valueOf(cliente.getEndereco().getComplemento()));

           editTextNumero.setText(String.valueOf(cliente.getEndereco().getNumero()));

           editTextTelefone.setText(String.valueOf(cliente.getTelefone()));

       }else{

           editTextNome.setText(user.getDisplayName());

       }

        return cliente;
    }

    private Cliente doLoadFields(){

        Cliente c = new Cliente();

        Endereco e  = new Endereco();

        Cep cep  = new Cep();

        c.setEndereco(e);

        e.setCep(cep);

        c.setNome(editTextNome.getText().toString());

        c.setCpf(Long.valueOf(editTextCPF.getText().toString()));

        c.setTelefone(Long.valueOf(editTextTelefone.getText().toString()));

        e.setNumero(editTextNumero.getText().toString());

        e.setComplemento(editTextComplemento.getText().toString());

        cep.setCep(Long.valueOf(editTextCPF.getText().toString()));

        return  c;

    }

    @Override
    public void doConfigure() {

        super.doConfigure();

        actionBar.setDisplayHomeAsUpEnabled(true);

        actionBar.setSubtitle("Cadastro de Cliente");

    }

    @Override
    public void doLoadExtras(Intent intent) {

        super.doLoadExtras(intent);

        usuario = (Usuario) intent.getSerializableExtra("Usuario");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        super.onOptionsItemSelected(item);

        int id = item.getItemId();

        if(id == android.R.id.home){

            Toast.makeText(context,"Click on Back Button ",Toast.LENGTH_SHORT).show();

            if(usuario == null){
                finish();
            }

            return true;

        }

        if (id == R.id.action_settings) {

            Toast.makeText(context,"Click on Settings Button ",Toast.LENGTH_SHORT).show();

            return true;
        }

        if(id == R.id.action_car){

            Toast.makeText(context,"Click on Car Button ",Toast.LENGTH_SHORT).show();

            return true;

        }

        if(id == R.id.action_search){

            Toast.makeText(context,"Click on Search Button ",Toast.LENGTH_SHORT).show();

            return true;
        }

        if(id == R.id.action_contact_developer){

            Toast.makeText(context,"Click on Contact Developer Button ",Toast.LENGTH_SHORT).show();

            return true;
        }

        if(id == R.id.action_personal_data){

            Toast.makeText(context,"Click on Personal Data Button ",Toast.LENGTH_SHORT).show();

            return true;
        }





        return super.onOptionsItemSelected(item);
    }
}
