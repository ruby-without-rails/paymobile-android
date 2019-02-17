/**
 * @author Felipe Rodrigues Michetti
 * @see http://portfolio-frmichetti.rhcloud.com
 * @see http://www.codecode.com.br
 * @see mailto:frmichetti@gmail.com
 */
package br.com.codecode.paymobile.android.view.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.text.method.ScrollingMovementMethod;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import br.com.codecode.paymobile.android.R;
import br.com.codecode.paymobile.android.model.ShoppingCart;
import br.com.codecode.paymobile.android.model.ShoppingItem;
import br.com.codecode.paymobile.android.view.activity.shoppingCart.ShoppingCartActivity;

public class ProductDetailActivity extends BaseActivity {

    private TextView textViewTitle, textViewInnerTitle, textViewSubtitle, textViewDescription, textCategoryDescription,
            textViewObservation, textViewPrice;

    private ImageView imageView, imageViewBackCollapse;

    private FloatingActionButton floatButtonAddCart;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        // setContentView(R.layout.activity_product_detail);
        setContentView(R.layout.activity_product_detail_collapse);

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

        actionBar.setSubtitle(getString(R.string.product_details));
    }

    @Override
    public void doCastComponents() {

        super.doCastComponents();

        textViewTitle = findViewById(R.id.tv_title);

        textViewSubtitle = findViewById(R.id.tv_subtitle);

        textViewInnerTitle = findViewById(R.id.textViewTitle);

        textViewDescription = findViewById(R.id.textViewDescricaoVar);
        textViewDescription.setMovementMethod(new ScrollingMovementMethod());

        textCategoryDescription = findViewById(R.id.textViewDurationVar);

        textViewObservation = findViewById(R.id.textViewObservationVar);
        textViewObservation.setMovementMethod(new ScrollingMovementMethod());

        textViewPrice = findViewById(R.id.textViewPriceVar);

        floatButtonAddCart = findViewById(R.id.fab_add_to_cart);

        imageView = findViewById(R.id.imv_large);
        imageViewBackCollapse = findViewById(R.id.backdrop);

    }

    @Override
    public void doCreateListeners() {

        floatButtonAddCart.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                shoppingCart.add(new ShoppingItem(selectedProduct));

                doChangeActivity(context, ShoppingCartActivity.class);

                finish();
            }
        });

    }

    private void doFillData() {

        textViewTitle.setText(selectedProduct.getCategory().getTitle());
        textViewSubtitle.setText(selectedProduct.getCategory().getSubtitle());
        textViewInnerTitle.setText(selectedProduct.getName());

        getSupportActionBar().setTitle(selectedProduct.getName());
        // getSupportActionBar().setSubtitle(String.valueOf(selectedProduct.getPrice()));

        textViewDescription.setText(selectedProduct.getDescription());

        textCategoryDescription.setText(selectedProduct.getCategory().getTitle());

        textViewObservation.setText(selectedProduct.getNotes());

        textViewPrice.setText(String.valueOf(selectedProduct.getPrice()));

        Glide.with(context)
                .load(selectedProduct.getImage())
                .into(imageView);

        String thumbUrl = selectedProduct.getCategory().getThumb();
        if (thumbUrl != null) {
            if (thumbUrl.contains("{host}")) {
                thumbUrl = thumbUrl.replace("{host}", getString(R.string.server));
            }
        }

        Glide.with(context)
                .load(thumbUrl)
                .into(imageViewBackCollapse);

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
