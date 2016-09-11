package br.com.frmichetti.carhollics.android.view.activity.shoppingCart;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.widget.TextView;

import br.com.frmichetti.carhollics.android.R;
import br.com.frmichetti.carhollics.android.model.compatibility.Checkout;
import br.com.frmichetti.carhollics.android.view.activity.BaseActivity;

public class CheckoutDetailActivity extends BaseActivity {

    private TextView textViewUUID, textViewDate, textViewStatus;

    private Checkout checkout;

    @Override
    public void doCastComponents() {

        super.doCastComponents();

        textViewUUID = (TextView) findViewById(R.id.textViewCheckoutUUIDvar);

        textViewDate = (TextView) findViewById(R.id.textViewCheckoutDateVar);

        textViewStatus = (TextView) findViewById(R.id.textViewCheckoutStatusVar);

    }

    @Override
    public void doCreateListeners() {

    }

    @Override
    public void doConfigure() {

        super.doConfigure();

    }

    private void doFillDate(Intent intent) {

        checkout = (Checkout) intent.getSerializableExtra("selectedCheckout");

        if (checkout != null) {

            textViewUUID.setText(checkout.getUuid());

            textViewDate.setText(checkout.getPurchaseDate().toString());

            textViewStatus.setText(checkout.getStatus().name());

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_checkout_detail);

        doCastComponents();

        doConfigure();

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


}
