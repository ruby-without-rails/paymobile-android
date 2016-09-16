/**
 * @author Felipe Rodrigues Michetti
 * @see http://portfolio-frmichetti.rhcloud.com
 * @see http://www.codecode.com.br
 * @see mailto:frmichetti@gmail.com
 */
package br.com.frmichetti.carhollics.android.view.activity.shoppingCart;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import br.com.frmichetti.carhollics.android.R;
import br.com.frmichetti.carhollics.android.model.ShoppingItem;
import br.com.frmichetti.carhollics.android.model.compatibility.Checkout;
import br.com.frmichetti.carhollics.android.model.compatibility.Service;
import br.com.frmichetti.carhollics.android.view.activity.BaseActivity;
import br.com.frmichetti.carhollics.android.view.activity.MainActivity;


public class ShoppingCartActivity extends BaseActivity {

    private FloatingActionButton fabRemoveItem, fabPurchase;

    private TextView textViewSelectedItem, textViewQuantity, textViewPrice, textViewSubTotal, textViewTotal;

    private ListView listViewShoppingCart;

    private ShoppingItem shoppingItem;

    private Checkout checkout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_shopping_cart);

        doCastComponents();

        doCreateListeners();

        setupToolBar();

    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {

        super.onPostCreate(savedInstanceState);

        doFillData();

        doRefresh();

        checkout = new Checkout();

    }

    private void doRefresh() {

        if (!shoppingCart.isEmpty()) {

            textViewSelectedItem.setText(shoppingItem.toString());

            textViewSubTotal.setText(String.valueOf(shoppingCart.getTotal(shoppingItem)));

            textViewTotal.setText(String.valueOf(shoppingCart.getTotal()));

            textViewPrice.setText(String.valueOf(shoppingItem.getService().getPrice()));

            textViewQuantity.setText(String.valueOf(shoppingCart.getQuantityOfItens(shoppingItem)));
        }


    }

    private void doFillData() {

        shoppingItem = new ShoppingItem(selectedService);

        ArrayAdapter<ShoppingItem> adpItem = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,
                new ArrayList<>(shoppingCart.getList()));

        listViewShoppingCart.setAdapter(adpItem);

    }


    public void doCastComponents() {

        super.doCastComponents();

        fabRemoveItem = (FloatingActionButton) findViewById(R.id.fab_remove_item_from_cart);

        fabPurchase = (FloatingActionButton) findViewById(R.id.fab_buy);

        listViewShoppingCart = (ListView) findViewById(R.id.lvShoppingCartItems);

        textViewSelectedItem = (TextView) findViewById(R.id.tvSelectedServiceVar);

        textViewQuantity = (TextView) findViewById(R.id.tvQuantityVar);

        textViewPrice = (TextView) findViewById(R.id.tvPriceVar);

        textViewSubTotal = (TextView) findViewById(R.id.tvSubtotalVar);

        textViewTotal = (TextView) findViewById(R.id.tvTotalVar);


    }


    public void doCreateListeners() {

        listViewShoppingCart.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                int itemPosition = position;

                Object itemValue = listViewShoppingCart.getItemAtPosition(position);

                shoppingItem = (ShoppingItem) itemValue;

                Toast.makeText(context, getString(R.string.service_selected) + shoppingItem.toString(), Toast.LENGTH_SHORT).show();

                doRefresh();

            }
        });

        fabRemoveItem.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (!shoppingCart.isEmpty()) {

                    shoppingCart.remove(shoppingItem);

                    Toast.makeText(context, getString(R.string.remove) + shoppingItem.toString(), Toast.LENGTH_SHORT).show();

                    doFillData();

                    textViewSelectedItem.setText("");

                    selectedService = new Service();

                } else {

                    Toast.makeText(context, getString(R.string.cart_is_empty), Toast.LENGTH_SHORT).show();

                }


            }
        });

        fabPurchase.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                doChangeActivity(context, ResumeCheckoutActivity.class);

                finish();
            }
        });

    }

    @Override
    public void setupToolBar() {

        super.setupToolBar();

        actionBar.setSubtitle(getString(R.string.action_cart));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        super.onOptionsItemSelected(item);

        int id = item.getItemId();

        if (id == android.R.id.home) {

            doChangeActivity(context, MainActivity.class);

            finish();


        }

        return true;
    }


    /**
     * Callback will be triggered when there is change in
     * network connection
     */
    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {

        showSnack((CoordinatorLayout) findViewById(R.id.coordlayoutcart), isConnected);
    }

}
