package br.com.frmichetti.carhollics.android;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import br.com.frmichetti.carhollics.android.jobs.AsyncResponse;
import br.com.frmichetti.carhollics.android.jobs.TaskCreateCliente;
import br.com.frmichetti.carhollics.android.model.Cep;
import br.com.frmichetti.carhollics.android.model.Cliente;
import br.com.frmichetti.carhollics.android.model.Endereco;
import br.com.frmichetti.carhollics.android.model.Usuario;

public class ClientActivity extends AppCompatActivity implements MyPattern{

    private ActionBar actionBar;

    private Context context;

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

        getExtras(getIntent());

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

                        startActivity(new Intent(context,SimpleMainActivity.class).putExtra("Cliente",output));
                        finish();

                    }
                });

                Cliente c = doFillData();

                c.setUsuario(usuario);

                taskCreateCliente.execute(c);

            }
        });

    }

    private Cliente doFillData() {

        Cliente c = new Cliente();

        Endereco e = new Endereco();

        Cep cep = new Cep();

        e.setCep(cep);

        e.setComplemento(editTextComplemento.getText().toString());

        e.setNumero(editTextNumero.getText().toString());

        c.setNome(editTextNome.getText().toString());

        c.setCpf(Long.valueOf(editTextCPF.getText().toString()));

        cep.setCep(Long.valueOf(editTextCEP.getText().toString()));

        c.setEndereco(e);

        c.setTelefone(Long.valueOf(editTextTelefone.getText().toString()));

        return c;

    }

    @Override
    public void doConfigure() {

        context = this;

        actionBar = getSupportActionBar();

        actionBar.setTitle(R.string.app_name);

        actionBar.setSubtitle("Cadastro de Cliente");

        actionBar.setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public void getExtras(Intent intent) {

        usuario = (Usuario) intent.getSerializableExtra("Usuario");

    }
}
