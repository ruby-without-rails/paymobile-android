package br.com.codecode.paymobile.android.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.widget.TextView;

import br.com.codecode.paymobile.android.R;
import br.com.codecode.paymobile.android.model.compatibility.Checkout;

import static br.com.codecode.paymobile.android.model.IntentKeys.SELECTED_CHECKOUT_KEY;

public class CheckoutDetailActivity extends BaseActivity {

    private TextView textViewUUID, textViewDate, textViewStatus;

    private Checkout checkout;

    @Override
    public void doCastComponents() {

        super.doCastComponents();

        textViewUUID = findViewById(R.id.textViewCheckoutUUIDvar);

        textViewDate = findViewById(R.id.textViewCheckoutDateVar);

        textViewStatus = findViewById(R.id.textViewCheckoutStatusVar);

    }

    @Override
    public void doCreateListeners() {

    }

    @Override
    public void setupToolBar() {

        super.setupToolBar();

        actionBar.setSubtitle("Detalhes do Pedido");

    }

    private void doFillDate(Intent intent) {

        checkout = (Checkout) intent.getSerializableExtra(SELECTED_CHECKOUT_KEY);

        if (checkout != null) {

            textViewUUID.setText(checkout.getUuid());

            textViewDate.setText(String.valueOf(checkout.getPurchaseDate()));

            textViewStatus.setText(checkout.getStatus().name());

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_checkout_detail);

        doCastComponents();

        doCreateListeners();

        setupToolBar();
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {

        super.onPostCreate(savedInstanceState);

        doFillDate(intent);

    }

    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {

        showSnack((CoordinatorLayout) findViewById(R.id.coordLayoutCheckDetail), isConnected);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {

        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            finish();
        }

        return super.onKeyUp(keyCode, event);

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
}
