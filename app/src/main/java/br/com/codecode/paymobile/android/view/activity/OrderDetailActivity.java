package br.com.codecode.paymobile.android.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.widget.TextView;

import br.com.codecode.paymobile.android.R;
import br.com.codecode.paymobile.android.model.compatibility.Order;

import static br.com.codecode.paymobile.android.model.IntentKeys.SELECTED_ORDER_KEY;

public class OrderDetailActivity extends BaseActivity {

    private TextView textViewUUID, textViewDate, textViewStatus;

    private Order order;

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

        order = (Order) intent.getSerializableExtra(SELECTED_ORDER_KEY);

        if (order != null) {

            textViewUUID.setText(String.valueOf(order.id));

            textViewDate.setText(String.valueOf(order.createdAt));

            textViewStatus.setText(String.valueOf(order.total));

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_order_detail);

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

        showSnack((CoordinatorLayout) findViewById(R.id.coordLayoutOrderDetail), isConnected);
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
