/**
 * @author Felipe Rodrigues Michetti
 * @see http://portfolio-frmichetti.rhcloud.com
 * @see http://www.codecode.com.br
 * @see mailto:frmichetti@gmail.com
 */
package br.com.frmichetti.carhollics.android.view.activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import br.com.frmichetti.carhollics.android.R;
import br.com.frmichetti.carhollics.android.model.ShoppingItem;
import br.com.frmichetti.carhollics.android.view.activity.shoppingCart.ShoppingCartActivity;


public class ServiceDetailActivity extends BaseActivity {

    private TextView textViewTitle, textViewDescription, textViewDuration, textViewObservation, textViewPrice;

    private FloatingActionButton floatButtonAddCart;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_service_detail);

        doCastComponents();

        doCreateListeners();


    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {

        super.onPostCreate(savedInstanceState);

        doConfigure();

        doLoadExtras(intent);

        doFillData();

        doCheckConnection(context);

    }


    public void doConfigure() {

        super.doConfigure();

        actionBar.setSubtitle(getString(R.string.service_details));


    }


    public void doCastComponents() {

        super.doCastComponents();

        textViewTitle = (TextView) findViewById(R.id.textViewTitle);

        textViewDescription = (TextView) findViewById(R.id.textViewDescricaoVar);

        textViewDuration = (TextView) findViewById(R.id.textViewDurationVar);

        textViewObservation = (TextView) findViewById(R.id.textViewObservationVar);

        textViewPrice = (TextView) findViewById(R.id.textViewPriceVar);

        floatButtonAddCart = (FloatingActionButton) findViewById(R.id.fab_add_to_cart);


    }

    public void doCreateListeners() {

        floatButtonAddCart.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                shoppingCart.add(new ShoppingItem(selectedService));

                startActivity(new Intent(context, ShoppingCartActivity.class)
                        .putExtra("customer", customer)
                        .putExtra("shoppingCart", shoppingCart)
                        .putExtra("service", selectedService)
                        .putExtra("vehicle", selectedVehicle)

                );

                finish();
            }
        });

    }

    private void doFillData() {

        textViewTitle.setText(selectedService.getTitle());

        textViewDescription.setText(selectedService.getDescription());

        textViewDuration.setText(String.valueOf((selectedService.getDuration() >= 60) ?
                (selectedService.getDuration() / 60) : (selectedService.getDuration())));

        textViewObservation.setText(selectedService.getObservation());

        textViewPrice.setText(String.valueOf(selectedService.getPrice()));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == android.R.id.home) {

            finish();

            return true;

        }


        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {

        super.onKeyUp(keyCode, event);

        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {

            //TODO FIXME Not Implemented Yet

        }

        if (event.getKeyCode() == KeyEvent.KEYCODE_HOME) {
            //TODO FIXME Not Implemented Yet

        }

        if (event.getKeyCode() == KeyEvent.KEYCODE_SEARCH) {

            //TODO FIXME Not Implemented Yet
        }

        return true;
    }

    /**
     * Callback will be triggered when there is change in
     * network connection
     */
    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {

        showSnack((CoordinatorLayout) findViewById(R.id.coordlayoutservicodetail), isConnected);
    }


}
