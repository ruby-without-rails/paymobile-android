package br.com.frmichetti.carhollics.android.view;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import br.com.frmichetti.carhollics.android.R;
import br.com.frmichetti.carhollics.android.model.Carrinho;
import br.com.frmichetti.carhollics.android.model.Cliente;
import br.com.frmichetti.carhollics.android.model.Servico;

public class MainActivity extends AppCompatActivity implements FragmentDrawer.FragmentDrawerListener {

    private static String TAG = MainActivity.class.getSimpleName();

    private Toolbar mToolbar;

    private Context context;

    private Intent intent;

    private FragmentDrawer drawerFragment;

    private Cliente cliente;

    private Carrinho carrinho;

    private Servico servicoSelecionado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(mToolbar);

        getSupportActionBar().setDisplayShowHomeEnabled(false);

        drawerFragment = (FragmentDrawer)
                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);

        drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), mToolbar);

        drawerFragment.setDrawerListener(this);

        // display the first navigation drawer view on app launch
        displayView(0);

    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {

        super.onPostCreate(savedInstanceState);

        context = this;

        intent = getIntent();

        carrinho = (Carrinho) intent.getSerializableExtra("Carrinho");

        cliente = (Cliente) intent.getSerializableExtra("Cliente");

        servicoSelecionado = (Servico) intent.getSerializableExtra("Servico");

        if (carrinho == null){

            carrinho = new Carrinho();
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {

            Toast.makeText(context, "Opções foi selecionado !", Toast.LENGTH_SHORT).show();

            startActivity(new Intent(context,OptionsActivity.class)
                    .putExtra("Carrinho",carrinho)
                    .putExtra("Cliente",cliente)
            );



            return true;
        }

        if(id == R.id.action_search){

            Toast.makeText(context, "Localizar foi selecionado !", Toast.LENGTH_SHORT).show();



            return true;
        }

        if(id == R.id.action_cart){

            Toast.makeText(context, "Carrinho foi selecionado !", Toast.LENGTH_SHORT).show();

            if(servicoSelecionado == null){

                Toast.makeText(context, "Item Selecionado Nulo !", Toast.LENGTH_SHORT).show();

            }else{

                startActivity(new Intent(context,CartActivity.class)
                        .putExtra("Carrinho",carrinho)
                        .putExtra("Cliente",cliente)
                );


            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDrawerItemSelected(View view, int position) {
        displayView(position);
    }

    private void displayView(int position) {

        Fragment fragment = null;

        String title = getString(R.string.app_name);

        switch (position) {

            case 0:
                fragment = new ServicosFragment();
                title = getString(R.string.title_services);
                break;
            case 1:
                fragment = new EnderecoFragment();
                title = getString(R.string.title_address);
                break;
            case 2:
                fragment = new VeiculosFragment();
                title = getString(R.string.title_vehicles);
                break;
            default:
                break;
        }

        if (fragment != null) {

            FragmentManager fragmentManager = getSupportFragmentManager();

            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            fragmentTransaction.replace(R.id.container_body, fragment);

            fragmentTransaction.commit();

            // set the toolbar title
            getSupportActionBar().setTitle(title);
        }
    }



}


