package br.com.frmichetti.carhollics.android.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import br.com.frmichetti.carhollics.android.R;
import br.com.frmichetti.carhollics.android.jobs.AsyncResponse;
import br.com.frmichetti.carhollics.android.jobs.TaskLogin;
import br.com.frmichetti.carhollics.android.model.Cliente;
import br.com.frmichetti.carhollics.android.model.Usuario;

public class SimpleLoginActivity extends AppCompatActivity implements MyPattern {

    private ActionBar actionBar;

    private Context context;

    private Intent intent;

    private Button buttonSignin;

    private EditText editTextLogin,editTextPassword;

    private TaskLogin taskLogin;

    private Usuario usuario = new Usuario();

    private Cliente cliente;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_simple_login);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {

        super.onPostCreate(savedInstanceState);

        doConfigure();

        doCastComponents();

        doCreateListeners();
    }

    @Override
    public void doCastComponents() {

        editTextLogin = (EditText) findViewById(R.id.editTextLogin);

        editTextPassword = (EditText) findViewById(R.id.editTextPassword);

        buttonSignin = (Button) findViewById(R.id.buttonSignin);

    }

    @Override
    public void doCreateListeners() {

        buttonSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                doLogin();

            }
        });
    }

    @Override
    public void doConfigure() {

        context = this;

        actionBar = getSupportActionBar();

    }


    //TODO Lógica de login aqui
    private void doLogin(){

        if(doFillUsuario()){
            taskLogin = new TaskLogin(context, new AsyncResponse<Cliente>() {

                @Override
                public void processFinish(Cliente output) {

                    cliente = output;

                    if(output!=null){

                        Log.i("OBJECT ---------->", cliente.toString());

                        doChangeActivity();
                    }

                }
            });

            taskLogin.execute(usuario);

        }

    }


    private void doChangeActivity(){

        intent = new Intent();

        intent.putExtra("Cliente",cliente);

        intent.setClass(context,SimpleMainActivity.class);

        context.startActivity(intent);
    }

    private boolean doFillUsuario(){

        if(editTextLogin.getText().toString().isEmpty() || editTextPassword.getText().toString().isEmpty()){

            Toast.makeText(context,"Usuário ou Senha Inválidos",Toast.LENGTH_SHORT).show();

            return false;

        }else{

            usuario.setLogin(editTextLogin.getText().toString());

            usuario.setSenha(editTextPassword.getText().toString());

            return true;
        }

    }

}
