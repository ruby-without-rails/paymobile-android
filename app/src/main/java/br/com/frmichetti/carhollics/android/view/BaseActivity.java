package br.com.frmichetti.carhollics.android.view;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.iid.FirebaseInstanceId;

import br.com.frmichetti.carhollics.android.R;
import br.com.frmichetti.carhollics.android.model.Carrinho;
import br.com.frmichetti.carhollics.android.model.Cliente;
import br.com.frmichetti.carhollics.android.model.Servico;
import br.com.frmichetti.carhollics.android.model.Veiculo;

/**
 * Created by Felipe on 08/07/2016.
 */
public abstract class BaseActivity extends AppCompatActivity implements MyPattern {

    protected ActionBar actionBar;

    protected Toolbar toolbar;

    protected Context context;

    protected Intent intent;

    private FirebaseAuth.AuthStateListener authListener;

    protected FirebaseAuth auth;

    protected FirebaseUser user;

    protected Cliente cliente;

    protected Carrinho carrinho;

    protected Servico servicoSelecionado;

    protected Veiculo veiculoSelecionado;

    protected BaseActivity(){

        Log.d("[INFO-INSTANCE]","Create instance of " + this.getClass().getSimpleName());

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        doRecoverState(savedInstanceState);

        auth = FirebaseAuth.getInstance();

        user = auth.getCurrentUser();

        doCreateFirebaseListener();

        Log.d("DEBUG-ON-CREATE","Super On Create");

        Log.d("Firebase-ID ",  FirebaseInstanceId.getInstance().getToken());

    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {

        super.onPostCreate(savedInstanceState);

        doRecoverState(savedInstanceState);

        doConfigure();

        Log.d("DEBUG-ON-POST-CREATE","Super On Post Create");

    }

    @Override
    protected void onStart() {

        super.onStart();

        auth.addAuthStateListener(authListener);

        Log.d("[DEBUG-AUTH-LISTENER]","Registered Authentication Listener");

    }


    @Override
    protected void onStop() {

        super.onStop();

        if (authListener != null) {

            auth.removeAuthStateListener(authListener);

            Log.d("[DEBUG-AUTH-LISTENER]","Removed Authentication Listener");

        }

    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {

        super.onSaveInstanceState(outState);

        outState.putSerializable("Cliente",cliente);

        outState.putSerializable("Carrinho",carrinho);

        outState.putSerializable("Servico",servicoSelecionado);

        outState.putSerializable("Veiculo",veiculoSelecionado);

        Log.d("[INFO-SAVE-BUNDLE]","Save State");

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if(id == android.R.id.home){

            Toast.makeText(context,"Click on Back Button ",Toast.LENGTH_SHORT).show();

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

        if(id == R.id.action_about){

            PackageInfo pinfo = null;

            try {

                pinfo = getPackageManager().getPackageInfo(getPackageName(), 0);

            } catch (PackageManager.NameNotFoundException e) {

                e.printStackTrace();
            }

            int versionNumber = pinfo.versionCode;

            String versionName = pinfo.versionName;

            Toast.makeText(context,"Versão deste App : " + versionName,Toast.LENGTH_LONG).show();

            return true;
        }

        if(id == R.id.action_map){

            Toast.makeText(context,"Click on Map Button ",Toast.LENGTH_SHORT).show();

            startActivity(new Intent(context,MapActivity.class));

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {

        //Log.d("[INFO-KEY-UP]","KeyUp " + String.valueOf(event.getKeyCode()));

        if(event.getKeyCode() == KeyEvent.KEYCODE_BACK){

            Log.d("Info","KeyUp Back Button");

            Toast.makeText(context,"KeyUp Back Button Pressed",Toast.LENGTH_SHORT).show();

        }

        if(event.getKeyCode() == KeyEvent.KEYCODE_HOME){

            Log.d("Info","KeyUp Home Button");

            Toast.makeText(context,"KeyUp Home Button Pressed",Toast.LENGTH_SHORT).show();
        }

        if(event.getKeyCode() == KeyEvent.KEYCODE_SEARCH){

            Log.d("Info","KeyUp Search Button");

            Toast.makeText(context,"KeyUp Search Button Pressed",Toast.LENGTH_SHORT).show();
        }

        return true;
    }

    private void doCreateFirebaseListener() {

        authListener = new FirebaseAuth.AuthStateListener() {

            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                FirebaseUser user = firebaseAuth.getCurrentUser();

                if (user == null) {

                    startActivity(new Intent(context, LoginActivity.class));

                    finish();

                    Log.d("[DEBUG-INFO]","User Null, Send to LoginActivity");

                }
            }
        };
    }

    @Override
    public void doCastComponents() {

        toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        actionBar = getSupportActionBar();

        Log.d("DEBUG-DO-CAST-COMP","Super Do Cast Components");
    }

    @Override
    public void doConfigure() {

        context = this;

        intent = getIntent();

        actionBar.setTitle(getString(R.string.app_name));

        actionBar.setDisplayHomeAsUpEnabled(true);

        Log.d("DEBUG-DO-CONFIGURE","Super Do Configure");

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }

    public void signOut() {

        auth.signOut();

        Log.d("[INFO-SIGNOUT]","SignOut");

    }

    public void doRecoverState(Bundle bundle){

        if(bundle != null){

            cliente = (Cliente) bundle.getSerializable("Cliente");

            carrinho = (Carrinho) bundle.getSerializable("Carrinho");

            servicoSelecionado = (Servico) bundle.getSerializable("Servico");

            veiculoSelecionado = (Veiculo) bundle.getSerializable("Veiculo");

            Log.d("[INFO-LOAD-BUNDLE]","Load Saved State");

        }


    }

    public Bundle doSaveState(){

        Bundle bundle = new Bundle();

        bundle.putSerializable("Cliente",cliente);

        bundle.putSerializable("Carrinho",carrinho);

        bundle.putSerializable("Servico",servicoSelecionado);

        bundle.putSerializable("Veiculo",veiculoSelecionado);

        Log.d("[INFO-SAVE-BUNDLE]","Saved State");

        return bundle;
    }

    public void doLoadExtras(Intent intent) {

        cliente = (Cliente) intent.getSerializableExtra("Cliente");

        carrinho = (Carrinho) intent.getSerializableExtra("Carrinho");

        servicoSelecionado = (Servico) intent.getSerializableExtra("Servico");

        veiculoSelecionado = (Veiculo) intent.getSerializableExtra("Veiculo");

        Log.d("DEBUG-LOAD-EXTRAS","Load Extras");
    }
}
