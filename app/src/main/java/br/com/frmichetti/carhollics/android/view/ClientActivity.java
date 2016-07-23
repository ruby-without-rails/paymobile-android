/**
 *
 * @author Felipe Rodrigues Michetti
 * @see http://portfolio-frmichetti.rhcloud.com
 * @see http://www.codecode.com.br
 * @see mailto:frmichetti@gmail.com
 * */
package br.com.frmichetti.carhollics.android.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.iid.FirebaseInstanceId;

import br.com.frmichetti.carhollics.android.R;
import br.com.frmichetti.carhollics.android.jobs.AsyncResponse;
import br.com.frmichetti.carhollics.android.jobs.TaskCreateCliente;
import br.com.frmichetti.carhollics.json.model.Cliente;
import br.com.frmichetti.carhollics.json.model.Usuario;


public class ClientActivity extends BaseActivity{

    private FloatingActionButton fabConfirmar;

    private EditText editTextNome,editTextCPF,editTextTelefone;

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

        fabConfirmar = (FloatingActionButton) findViewById(R.id.fab_action_done);


    }

    @Override
    public void doCreateListeners() {

        fabConfirmar.setOnClickListener(new View.OnClickListener() {


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

                taskCreateCliente.execute(cliente);


            }
        });

    }

    private Cliente doLoadCliente(Cliente cliente) {

        if (cliente != null) {

            editTextNome.setText(cliente.getNome());

            editTextCPF.setText(String.valueOf(cliente.getCpf()));

            editTextTelefone.setText(String.valueOf(cliente.getTelefone()));

        }else{

            try{

                editTextNome.setText(user.getDisplayName());

            }catch (NullPointerException e){
                
                Log.d("DEBUG-USER", "Usuario sem Valores na conta");
            }

        }

        return cliente;
    }

    private Cliente doLoadFields(){

        Cliente c;

        if(cliente != null){

            c = cliente;

            c.getUsuario().setFirebaseMessageToken(FirebaseInstanceId.getInstance().getToken());

            c.getUsuario().setFirebaseUUID(FirebaseAuth.getInstance().getCurrentUser().getUid());


        }else {

            c = new Cliente();

            c.setUsuario(usuario);

        }

        c.setNome(editTextNome.getText().toString());

        c.setCpf(Long.valueOf(editTextCPF.getText().toString()));

        c.setTelefone(Long.valueOf(editTextTelefone.getText().toString()));

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

        cliente = (Cliente) intent.getSerializableExtra("Cliente");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        super.onOptionsItemSelected(item);

        int id = item.getItemId();

        if(id == android.R.id.home){

            if(usuario == null){
                finish();
            }

        }

        return true;


    }
}
