/**
 * @author Felipe Rodrigues Michetti
 * @see http://portfolio-frmichetti.rhcloud.com
 * @see http://www.codecode.com.br
 * @see mailto:frmichetti@gmail.com
 */
package br.com.frmichetti.paymobile.android.view.fragment;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import br.com.frmichetti.paymobile.android.R;
import br.com.frmichetti.paymobile.android.adapter.SimpleCardItemAdapter;
import br.com.frmichetti.paymobile.android.listener.CardItemClickListener;
import br.com.frmichetti.paymobile.android.listener.ItemAdapterListener;
import br.com.frmichetti.paymobile.android.listener.SimpleItemSelectionListener;
import br.com.frmichetti.paymobile.android.listener.RecyclerTouchListener;
import br.com.frmichetti.paymobile.android.tasks.AsyncResponse;
import br.com.frmichetti.paymobile.android.tasks.TaskDownloadProducts;
import br.com.frmichetti.paymobile.android.model.compatibility.Product;
import br.com.frmichetti.paymobile.android.view.activity.ProductDetailActivity;

import static br.com.frmichetti.paymobile.android.MyApplication.getSessionToken;
import static br.com.frmichetti.paymobile.android.model.IntentKeys.PRODUCTS_BUNDLE_KEY;

public class ProductsFragment extends BaseFragment implements SimpleItemSelectionListener, ItemAdapterListener {
    private TextView textView;
    private ArrayList<Product> products;
    private RecyclerView recyclerView;
    private SimpleCardItemAdapter simpleCardItemAdapter;
    private int lastItemSelected;

    public ProductsFragment() {
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

        doLoadServices(getSessionToken().getKey());

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

    private void doLoadServices(String sessionToken) {
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
                            Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
                            Log.d("Error", e.getMessage());
                        }
                    }).execute(sessionToken);
        }
    }

    private void doFillData(ArrayList<Product> products) {
        if (products != null) {

            createFileAdapter(products);

            notifyData(simpleCardItemAdapter);

            configureRecyclerView(context, simpleCardItemAdapter);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(PRODUCTS_BUNDLE_KEY, products);
    }

    protected void createFileAdapter(List<Product> productList) {
        simpleCardItemAdapter = new SimpleCardItemAdapter(productList, getActivity(), this, this) {
            public Filter getFilter() {
                return new Filter() {
                    @Override
                    protected FilterResults performFiltering(CharSequence constraint) {
                        makeToast("filter for " + constraint.toString());
                        FilterResults filterResults = new FilterResults();
                        filterResults.values = new ArrayList<>();
                        return filterResults;
                    }

                    @Override
                    protected void publishResults(CharSequence constraint, FilterResults results) {
                        //  fileListFiltered = (ArrayList<JsonFile>) results.values;
                        notifyDataSetChanged();
                    }
                };
            }
        };

    }

    protected void configureRecyclerView(final Context context, final SimpleCardItemAdapter fileFolderAdapter) {
        // white background notification bar
        whiteNotificationBar(recyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(context, LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(fileFolderAdapter);
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(context,
                recyclerView, new CardItemClickListener() {
            @Override
            public void onClick(View view, final int position) {
                lastItemSelected = position;
                //Values are passing to activity & to fragment as well
                makeToast("Single Click on position :" + position);
            }

            @Override
            public void onLongClick(View view, int position) {
                lastItemSelected = position;
                makeToast("Long press on position :" + position);
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
        Toast.makeText(context, "Selected: " + product.getName(), Toast.LENGTH_LONG).show();

        selectedService = product;

        doChangeActivity(context, ProductDetailActivity.class);
    }

    @Override
    public void onDownload(Product product) {
        makeToast("Click on Download Button : " + product.getName());
    }

    @Override
    public void onShare(Product product) {
        makeToast("Click on Share Button : " + product.getName());
    }

    @Override
    public void onRename(Product product) {
        makeToast("Click on Rename Button : " + product.getName());
    }

    @Override
    public void onDelete(Product product) {
        makeToast("Click on Delete Button : " + product.getName());
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
