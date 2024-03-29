/**
 * @author Felipe Rodrigues Michetti
 * @see http://portfolio-frmichetti.rhcloud.com
 * @see http://www.codecode.com.br
 * @see mailto:frmichetti@gmail.com
 */
package br.com.codecode.paymobile.android.view.activity.shoppingCart;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import br.com.codecode.paymobile.android.R;
import br.com.codecode.paymobile.android.adapter.CartListAdapter;
import br.com.codecode.paymobile.android.helper.RecyclerItemTouchHelper;
import br.com.codecode.paymobile.android.model.ShoppingItem;
import br.com.codecode.paymobile.android.model.compatibility.Checkout;
import br.com.codecode.paymobile.android.rest.dto.OrderDTO;
import br.com.codecode.paymobile.android.rest.dto.OrderReceiptDTO;
import br.com.codecode.paymobile.android.tasks.TaskCreateOrder;
import br.com.codecode.paymobile.android.view.activity.BaseActivity;
import br.com.codecode.paymobile.android.view.activity.MainActivity;
import br.com.codecode.paymobile.checkoutflow.OrderActivity;

import static br.com.codecode.paymobile.android.model.IntentKeys.ORDER_BUNDLE_KEY;


public class ShoppingCartActivity extends BaseActivity implements RecyclerItemTouchHelper.RecyclerItemTouchHelperListener {

    private FloatingActionButton fabEmptyCart;

    private TextView textViewSelectedItem, textViewQuantity, textViewPrice, textViewSubTotal, textViewTotal, textViewBottomTotal;

    private BottomNavigationView bottomSheet;
    private BottomSheetBehavior sheetBehavior;

    private ShoppingItem shoppingItem;

    private ImageView imageViewTouch;

    private Checkout checkout;

    private RecyclerView recyclerView;
    private CartListAdapter mAdapter;
    private CoordinatorLayout coordinatorLayout;
    private List<ShoppingItem> cartList;
    private ListView listView;

    private ArrayAdapter<ShoppingItem> adapter;

    private Button buttonPayment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_shopping_cart);

        doCastComponents();

        doCreateListeners();

        setupToolBar();

        recyclerView = findViewById(R.id.recycler_view);
        coordinatorLayout = findViewById(R.id.coord_layout_shopping_cart);

    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {

        super.onPostCreate(savedInstanceState);

        doFillData();

        doRefresh();

        checkout = new Checkout();

        Animation pulse = AnimationUtils.loadAnimation(context, R.anim.pulse_animation);

        imageViewTouch.startAnimation(pulse);

    }

    private void doRefresh() {

        if (!shoppingCart.isEmpty()) {

            textViewSelectedItem.setText(shoppingItem.toString());

            textViewSubTotal.setText(String.valueOf(shoppingCart.getTotal(shoppingItem)));

            textViewTotal.setText(String.valueOf(shoppingCart.getTotal()));

            textViewPrice.setText(String.valueOf(shoppingItem.getProduct().getPrice()));

            textViewQuantity.setText(String.valueOf(shoppingCart.getQuantityOfItens(shoppingItem)));

            textViewBottomTotal.setText(String.valueOf(shoppingCart.getTotal()));

        }


    }

    private void doFillData() {

        shoppingItem = new ShoppingItem(selectedProduct);

        cartList = new ArrayList<>();

        cartList.addAll(shoppingCart.getList());

        mAdapter = new CartListAdapter(context, shoppingCart);

        adapter = new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, cartList);

        listView.setAdapter(adapter);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(mAdapter);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0 && fabEmptyCart.getVisibility() == View.VISIBLE) {
                    fabEmptyCart.hide();
                } else if (dy < 0 && fabEmptyCart.getVisibility() != View.VISIBLE) {
                    fabEmptyCart.show();
                }
            }
        });
        // adding item touch helper
        // only ItemTouchHelper.LEFT added to detect Right to Left swipe
        // if you want both Right -> Left and Left -> Right
        // add pass ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT as param
        ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new RecyclerItemTouchHelper(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT, this);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerView);

    }


    public void doCastComponents() {

        super.doCastComponents();

        fabEmptyCart = findViewById(R.id.fab_empty_cart);

        textViewSelectedItem = findViewById(R.id.tv_product_var);

        textViewQuantity = findViewById(R.id.tv_quantity_var);

        textViewPrice = findViewById(R.id.tv_price_var);

        textViewSubTotal = findViewById(R.id.tv_subtotal_var);

        textViewTotal = findViewById(R.id.tv_total_var);

        bottomSheet = findViewById(R.id.bottom_sheet);

        sheetBehavior = BottomSheetBehavior.from(bottomSheet);

        buttonPayment = findViewById(R.id.btn_pay);

        listView = findViewById(R.id.lv_bottom_cart_items);

        textViewBottomTotal = findViewById(R.id.tv_bottom_total);

        imageViewTouch = findViewById(R.id.iv_bottom_touch);

    }


    public void doCreateListeners() {

        fabEmptyCart.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                shoppingCart.clearCart();

                cartList = new ArrayList<>();

                selectedProduct = null;

                // doChangeActivity(context, ResumeCheckoutActivity.class);

                finish();

                Toast.makeText(context, R.string.your_cart_is_empty, Toast.LENGTH_SHORT).show();
            }
        });


        /**
         * bottom sheet state change listener
         * we are changing button text when sheet changed state
         * */
        sheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                switch (newState) {
                    case BottomSheetBehavior.STATE_HIDDEN:
                        break;
                    case BottomSheetBehavior.STATE_EXPANDED: {}
                    break;
                    case BottomSheetBehavior.STATE_COLLAPSED: {
                        fabEmptyCart.show();
                    }
                    break;
                    case BottomSheetBehavior.STATE_DRAGGING:
                        fabEmptyCart.hide();
                        break;
                    case BottomSheetBehavior.STATE_SETTLING:
                        break;
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });

        buttonPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                OrderReceiptDTO orderDTO;
                try {
                    orderDTO = new TaskCreateOrder(context).execute(shoppingCart).get();
                    if (orderDTO != null) {
                        startActivity(new Intent(context, OrderActivity.class).putExtra(ORDER_BUNDLE_KEY, orderDTO.order));
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
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

        showSnack((CoordinatorLayout) findViewById(R.id.coord_layout_shopping_cart), isConnected);
    }

    /**
     * callback when recycler view is swiped
     * item will be removed on swiped
     * undo option will be provided in snackbar to restore the item
     */
    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
        if (viewHolder instanceof CartListAdapter.MyViewHolder) {
            // get the removed item name to display it in snack bar
            String name = cartList.get(viewHolder.getAdapterPosition()).getProduct().getName() + " ";

            // backup of removed item for undo purpose
            final ShoppingItem deletedItem = cartList.get(viewHolder.getAdapterPosition());
            final int deletedIndex = viewHolder.getAdapterPosition();

            // remove the item from recycler view
            mAdapter.removeItem(viewHolder.getAdapterPosition());

            mAdapter.notifyDataSetChanged();
            adapter.notifyDataSetChanged();

            // showing snack bar with Undo option
            Snackbar snackbar = Snackbar
                    .make(coordinatorLayout, name + getString(R.string.removed_from_cart), Snackbar.LENGTH_LONG);
            snackbar.setAction(R.string.action_undo, new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    // undo is selected, restore the deleted item
                    mAdapter.restoreItem(deletedItem, deletedIndex);
                    shoppingCart.add(deletedItem);
                    cartList.add(deletedItem);
                    textViewBottomTotal.setText(String.valueOf(shoppingCart.getTotal()));

                    adapter.notifyDataSetChanged();
                    mAdapter.notifyDataSetChanged();

                    if(shoppingCart.isEmpty()){
                        buttonPayment.setEnabled(false);
                        cartList.clear();
                    }else{
                        buttonPayment.setEnabled(true);
                    }

                }
            });
            snackbar.setActionTextColor(Color.YELLOW);
            View snackbarView = snackbar.getView();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                snackbarView.setElevation(30);
            }
            snackbar.show();

            shoppingCart.remove(deletedItem);

            if(shoppingCart.isEmpty()){
                buttonPayment.setEnabled(false);
                cartList.clear();
            }else{
                buttonPayment.setEnabled(true);
            }

            textViewBottomTotal.setText(String.valueOf(shoppingCart.getTotal()));
        }
    }

    /**
     * manually opening / closing bottom sheet on button click
     */
    public void toggleBottomSheet() {
        if (sheetBehavior.getState() != BottomSheetBehavior.STATE_EXPANDED) {
            sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            // btnBottomSheet.setText("Close sheet");
        } else {
            sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            // btnBottomSheet.setText("Expand sheet");
        }
    }
}
