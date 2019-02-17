/**
 * @author Felipe Rodrigues Michetti
 * @see http://portfolio-frmichetti.rhcloud.com
 * @see http://www.codecode.com.br
 * @see mailto:frmichetti@gmail.com
 */
package br.com.codecode.paymobile.android.view.fragment;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.ShareActionProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import br.com.codecode.paymobile.android.R;
import br.com.codecode.paymobile.android.adapter.SimpleCardItemAdapter;
import br.com.codecode.paymobile.android.listener.CardItemClickListener;
import br.com.codecode.paymobile.android.listener.ItemAdapterListener;
import br.com.codecode.paymobile.android.listener.RecyclerTouchListener;
import br.com.codecode.paymobile.android.listener.SimpleItemSelectionListener;
import br.com.codecode.paymobile.android.model.compatibility.Product;
import br.com.codecode.paymobile.android.tasks.AsyncResponse;
import br.com.codecode.paymobile.android.tasks.TaskDownloadProducts;
import br.com.codecode.paymobile.android.view.activity.MosaicActivity;
import br.com.codecode.paymobile.android.view.activity.ProductDetailActivity;

import static br.com.codecode.paymobile.android.MyApplication.getSessionToken;
import static br.com.codecode.paymobile.android.model.IntentKeys.PRODUCTS_BUNDLE_KEY;

public class ProductsFragment extends BaseFragment implements SimpleItemSelectionListener, ItemAdapterListener {
    private TextView textView;
    private ArrayList<Product> products;
    private RecyclerView recyclerView;
    private SimpleCardItemAdapter simpleCardItemAdapter;
    private int lastItemSelected;
    private SearchView searchView = null;
    private SearchView.OnQueryTextListener queryTextListener;

    public ProductsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {

        if (savedInstanceState != null) {
            products = (ArrayList<Product>) savedInstanceState.getSerializable(PRODUCTS_BUNDLE_KEY);
        }

        View rootView = inflater.inflate(R.layout.fragment_services, container, false);

        doCastComponents(rootView);

        doCreateListeners();

        doLoadProducts(getSessionToken().getKey());

        return rootView;
    }

    @Override
    protected void doCastComponents(View rootView) {
        textView = rootView.findViewById(R.id.tv_product_label);
        recyclerView = rootView.findViewById(R.id.recycler_view);
    }

    @Override
    protected void doCreateListeners() {

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);

        MenuItem searchItem = menu.findItem(R.id.action_filter_search);
        if (searchItem != null) {
            searchView = (SearchView) searchItem.getActionView();
        }
        if (searchView != null) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
            queryTextListener = new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextChange(String newText) {
                    simpleCardItemAdapter.getFilter().filter(newText);
                    return false;
                }
                @Override
                public boolean onQueryTextSubmit(String query) {
                    simpleCardItemAdapter.getFilter().filter(query);
                    return false;
                }
            };
            searchView.setOnQueryTextListener(queryTextListener);
        }

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_filter_search:
                // Not implemented here
                return false;
            default:
                break;
        }
        searchView.setOnQueryTextListener(queryTextListener);
        return super.onOptionsItemSelected(item);
    }

    private void doLoadProducts(String sessionToken) {
        if (products == null) {
            Log.d("[DOWNLOAD-PRODUCTS]", "Load Products from webservice");

            new TaskDownloadProducts(context,
                    new AsyncResponse<ArrayList<Product>>() {

                        @Override
                        public void onSuccess(ArrayList<Product> output) {
                            products = output;

                            doFillData(products);
                        }

                        @Override
                        public void onFails(Exception e) {
                            Toast.makeText(context, e.toString(), Toast.LENGTH_LONG).show();
                            Log.d("Error", e.toString());
                        }
                    }).execute(sessionToken);
        }
    }

    private void doFillData(ArrayList<Product> products) {
        if (products != null) {

            createAdapter(products);

            notifyData(simpleCardItemAdapter);

            configureRecyclerView(context, simpleCardItemAdapter);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(PRODUCTS_BUNDLE_KEY, products);
    }

    protected void createAdapter(List<Product> productList) {
        simpleCardItemAdapter = new SimpleCardItemAdapter(productList, getActivity(), this, this) {};
    }

    protected void configureRecyclerView(final Context context, final SimpleCardItemAdapter simpleCardItemAdapter) {
        // white background notification bar
        // whiteNotificationBar(recyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(context, LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(simpleCardItemAdapter);
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(context,
                recyclerView, new CardItemClickListener() {
            @Override
            public void onClick(View view, final int position) {
                lastItemSelected = position;
                //Values are passing to activity & to fragment as well
            }

            @Override
            public void onLongClick(View view, int position) {
                lastItemSelected = position;
            }
        }));

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
//                if (dy > 0 && .getVisibility() == View.VISIBLE) {
//                    fab.hide();
//                } else if (dy < 0 && fab.getVisibility() != View.VISIBLE) {
//                    fab.show();
//                }
            }
        });
    }

    @Override
    public void onItemSelected(Product product) {
        selectedProduct = product;

        doChangeActivity(context, ProductDetailActivity.class);
    }

    @Override
    public void onDetails(Product product) {
        selectedProduct = product;
        doChangeActivity(context, ProductDetailActivity.class);
    }

    @Override
    public void onShare(Product product, MenuItem menuItem) {
        ShareActionProvider myShareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(menuItem);
        Intent myShareIntent = new Intent(Intent.ACTION_SEND);
        myShareIntent.setType("image/*");
        myShareIntent.putExtra(Intent.EXTRA_STREAM, product.getImage());

        if (myShareActionProvider != null) {
            myShareActionProvider.setShareIntent(myShareIntent);
        }
    }

    @Override
    public void onGallery(Product product) {
        if(product.getCategory().getId() == 1){
            doChangeActivity(context, MosaicActivity.class);
        }
    }

    protected void notifyData(SimpleCardItemAdapter simpleCardItemAdapter) {
        if (simpleCardItemAdapter != null)
            simpleCardItemAdapter.notifyDataSetChanged();
    }

    protected void makeToast(String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    private void whiteNotificationBar(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int flags = view.getSystemUiVisibility();
            flags |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            view.setSystemUiVisibility(flags);
            getActivity().getWindow().setStatusBarColor(Color.WHITE);
        }
    }
}
