package br.com.frmichetti.carhollics.android.view;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
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

    private boolean permission;

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

            startActivity(new Intent(context,OptionsActivity.class)
                    .putExtra("Carrinho",carrinho)
                    .putExtra("Cliente",cliente)
                    .putExtra("Veiculo",veiculoSelecionado)
                    .putExtra("Servico",servicoSelecionado)
            );

        }

        if(id == R.id.action_cart){

            Toast.makeText(context, "Carrinho foi selecionado !", Toast.LENGTH_SHORT).show();

                startActivity(new Intent(context,CartActivity.class)
                        .putExtra("Carrinho",carrinho)
                        .putExtra("Cliente",cliente)
                        .putExtra("Veiculo",veiculoSelecionado)
                        .putExtra("Servico",servicoSelecionado)
                );

        }

        if(id == R.id.action_personal_data){

            startActivity(new Intent(context,ClientActivity.class)
                    .putExtra("Carrinho",carrinho)
                    .putExtra("Cliente",cliente)
                    .putExtra("Veiculo",veiculoSelecionado)
                    .putExtra("Servico",servicoSelecionado)
            );
        }

        if(id == R.id.action_contact_developer){

            Intent i = new Intent(Intent.ACTION_SEND);

            i.setType("message/rfc822");

            i.putExtra(Intent.EXTRA_EMAIL  , new String[]{"frmichetti@gmail.com"});
            i.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.app_name));
            i.putExtra(Intent.EXTRA_TEXT   , "Contato App Carhollics");

            try {

                startActivity(Intent.createChooser(i, getString(R.string.send_mail_to_developer)));

            } catch (android.content.ActivityNotFoundException ex) {

                Toast.makeText(context, getString(R.string.no_email_client_installed), Toast.LENGTH_SHORT).show();
            }

        }

        if(id == R.id.action_map){

            checkPermissions(context,this);
        }

        return true;
    }

    private void checkPermissions(Context context, Activity activity) {

        if(ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){

            if(ActivityCompat.shouldShowRequestPermissionRationale(activity,Manifest.permission.ACCESS_FINE_LOCATION)){


            }else{

                ActivityCompat.requestPermissions(activity,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},0);
            }

        }else{

            Toast.makeText(context,getString(R.string.permission_granted),Toast.LENGTH_LONG).show();

            permission = true;

        }

        if(permission){

            //TODO FIXME Verificar Permissoes antes
            startActivity(new Intent(context,MapActivity.class));
        }

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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode){

            case 0: {

                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){

                    Toast.makeText(context,getString(R.string.permission_accepted),Toast.LENGTH_LONG).show();

                    permission = true;

                }else{

                    Toast.makeText(context,getString(R.string.permission_not_accepted),Toast.LENGTH_LONG).show();

                    permission = false;

                }
                return;
            }
        }
    }


}


