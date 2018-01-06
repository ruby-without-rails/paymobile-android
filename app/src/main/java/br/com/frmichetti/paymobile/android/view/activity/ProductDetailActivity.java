/**
 * @author Felipe Rodrigues Michetti
 * @see http://portfolio-frmichetti.rhcloud.com
 * @see http://www.codecode.com.br
 * @see mailto:frmichetti@gmail.com
 */
package br.com.frmichetti.paymobile.android.view.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import br.com.frmichetti.paymobile.android.R;
import br.com.frmichetti.paymobile.android.model.ShoppingCart;
import br.com.frmichetti.paymobile.android.model.ShoppingItem;
import br.com.frmichetti.paymobile.android.view.activity.shoppingCart.ShoppingCartActivity;

public class ProductDetailActivity extends BaseActivity {

    private TextView textViewTitle, textViewDescription, textCategoryDescription,
            textViewObservation, textViewPrice;

    private FloatingActionButton floatButtonAddCart;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_product_detail);

        doCastComponents();

        doCreateListeners();

        setupToolBar();

    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {

        super.onPostCreate(savedInstanceState);

        doFillData();

        if (shoppingCart == null) {
            shoppingCart = new ShoppingCart();
        }

    }


    @Override
    public void setupToolBar() {

        super.setupToolBar();

        actionBar.setSubtitle(getString(R.string.service_details));
    }

    @Override
    public void doCastComponents() {

        super.doCastComponents();

        textViewTitle = findViewById(R.id.textViewTitle);

        textViewDescription = findViewById(R.id.textViewDescricaoVar);

        textCategoryDescription = findViewById(R.id.textViewDurationVar);

        textViewObservation = findViewById(R.id.textViewObservationVar);

        textViewPrice = findViewById(R.id.textViewPriceVar);

        floatButtonAddCart = findViewById(R.id.fab_add_to_cart);


    }

    @Override
    public void doCreateListeners() {

        floatButtonAddCart.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                shoppingCart.add(new ShoppingItem(selectedService));

                doChangeActivity(context, ShoppingCartActivity.class);

                finish();
            }
        });

    }

    private void doFillData() {

        textViewTitle.setText(selectedService.getName());

        textViewDescription.setText(selectedService.getDescription());

        textCategoryDescription.setText(selectedService.getCategory().getName());

        textViewObservation.setText(selectedService.getNotes());

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

        showSnack((CoordinatorLayout) findViewById(R.id.coordLayoutServiceDetail), isConnected);
    }


}
