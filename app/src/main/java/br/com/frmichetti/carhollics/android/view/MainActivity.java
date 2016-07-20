package br.com.frmichetti.carhollics.android.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import br.com.frmichetti.carhollics.android.R;
import br.com.frmichetti.carhollics.json.model.Carrinho;
import br.com.frmichetti.carhollics.json.model.Servico;
import br.com.frmichetti.carhollics.json.model.Veiculo;


public class MainActivity extends BaseActivity implements FragmentDrawer.FragmentDrawerListener {

    private static String TAG = MainActivity.class.getSimpleName();

    private FragmentDrawer drawerFragment;

    private ImageView imageView;

    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        doCastComponents();

    }


    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {

        super.onPostCreate(savedInstanceState);

        doConfigure();

        doLoadExtras(intent);

        if(carrinho == null){

            carrinho = new Carrinho();
        }

        if(servicoSelecionado == null){

            servicoSelecionado = new Servico();
        }

        if(veiculoSelecionado == null){

            veiculoSelecionado = new Veiculo();
        }

        doSetFragment();

        doShowInfo();

    }

    //TODO FIXME show info personal data
    private void doShowInfo() {
/*
        if(user.getPhotoUrl() != null){
            try {

                new DownloadImageTask(imageView).execute(new URL(user.getPhotoUrl().toString()));

            } catch (MalformedURLException e) {

                e.printStackTrace();

            }


        }
*/


        textView.setText(user.getDisplayName());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        super.onOptionsItemSelected(item);

        int id = item.getItemId();

        if (id == R.id.action_settings) {

            Toast.makeText(context, "Opções foi selecionado !", Toast.LENGTH_SHORT).show();

            startActivity(new Intent(context,OptionsActivity.class)
                    .putExtra("Carrinho",carrinho)
                    .putExtra("Cliente",cliente)
                    .putExtra("Veiculo",veiculoSelecionado)
                    .putExtra("Servico",servicoSelecionado)
            );

            return true;
        }

        if(id == R.id.action_search){

            Toast.makeText(context, "Localizar foi selecionado !", Toast.LENGTH_SHORT).show();

            return true;
        }

        if(id == R.id.action_cart){

            Toast.makeText(context, "Carrinho foi selecionado !", Toast.LENGTH_SHORT).show();

                startActivity(new Intent(context,CartActivity.class)
                        .putExtra("Carrinho",carrinho)
                        .putExtra("Cliente",cliente)
                        .putExtra("Veiculo",veiculoSelecionado)
                        .putExtra("Servico",servicoSelecionado)
                );

            return true;
        }

        if(id == R.id.action_personal_data){

            startActivity(new Intent(context,ClientActivity.class)
                    .putExtra("Carrinho",carrinho)
                    .putExtra("Cliente",cliente)
                    .putExtra("Veiculo",veiculoSelecionado)
                    .putExtra("Servico",servicoSelecionado)
            );

            return true;

        }

        if(id == R.id.action_contact_developer){

            Intent i = new Intent(Intent.ACTION_SEND);

            i.setType("message/rfc822");

            i.putExtra(Intent.EXTRA_EMAIL  , new String[]{"frmichetti@gmail.com"});
            i.putExtra(Intent.EXTRA_SUBJECT, "App Carhollics");
            i.putExtra(Intent.EXTRA_TEXT   , "App Carhollics");

            try {

                startActivity(Intent.createChooser(i, "Enviar email para o desenvolvedor..."));

            } catch (android.content.ActivityNotFoundException ex) {

                Toast.makeText(context, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
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

            case 3:
                fragment = new PedidosFragment();
                title = getString(R.string.title_checkouts);
                break;
            default:
                break;
        }

        if (fragment != null) {

            FragmentManager fragmentManager = getSupportFragmentManager();

            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            fragmentTransaction.replace(R.id.container_body, fragment);

            fragmentTransaction.commit();

            actionBar.setSubtitle(title);
        }
    }


    @Override
    public void doCreateListeners() {

    }


    private void doSetFragment() {

        drawerFragment = (FragmentDrawer)
                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);

        drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), toolbar);

        drawerFragment.setDrawerListener(this);

        // display the first navigation drawer view on app launch
        displayView(0);
    }

    @Override
    public void doCastComponents() {

        super.doCastComponents();

        imageView = (ImageView) findViewById(R.id.imageViewAccountImage);

        textView = (TextView) findViewById(R.id.textViewNome);
    }


}


