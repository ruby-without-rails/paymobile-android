package br.com.frmichetti.paymobile.android.view.fragment;


import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import br.com.frmichetti.paymobile.android.R;
import br.com.frmichetti.paymobile.android.adapter.StoreAdapter;
import br.com.frmichetti.paymobile.android.helper.GridSpacingItemDecoration;
import br.com.frmichetti.paymobile.android.listener.ItemAdapterListener;
import br.com.frmichetti.paymobile.android.listener.SimpleItemSelectionListener;
import br.com.frmichetti.paymobile.android.model.compatibility.Product;
import br.com.frmichetti.paymobile.android.tasks.AsyncResponse;
import br.com.frmichetti.paymobile.android.tasks.TaskDownloadProducts;

import static br.com.frmichetti.paymobile.android.MyApplication.getSessionToken;

public class StoreFragment extends Fragment implements ItemAdapterListener {

    private static final String TAG = StoreFragment.class.getSimpleName();

    private RecyclerView recyclerView;
    private List<Product> productList;
    private StoreAdapter storeAdapter;
    private Context context;

    public StoreFragment() {
        // Required empty public constructor
    }

    public static StoreFragment newInstance(String param1, String param2) {
        StoreFragment fragment = new StoreFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_store, container, false);

        recyclerView = view.findViewById(R.id.recycler_view);
        productList = new ArrayList<>();
        storeAdapter = new StoreAdapter(context, productList, this);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(context, 3);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(8), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(storeAdapter);
        recyclerView.setNestedScrollingEnabled(false);

        fetchStoreItems(getSessionToken().getKey());

        return view;
    }

    /**
     * fetching shopping item by making http call
     */
    private void fetchStoreItems(String sessionToken) {
        if (productList == null || productList.isEmpty()) {
            Log.d("[DOWNLOAD-PRODUCTS]", "Load Products from webservice");

            new TaskDownloadProducts(context,
                    new AsyncResponse<ArrayList<Product>>() {
                        @Override
                        public void onSuccess(ArrayList<Product> output) {
                            productList.clear();
                            productList.addAll(output);

                            // refreshing recycler view
                            storeAdapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onFails(Exception e) {
                            Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
                            Log.d("Error", e.getMessage());
                        }
                    }).execute(sessionToken);
        }

    }

    /**
     * Converting dp to pixel
     */
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

    @Override
    public void onItemSelected(Product product) {
        Toast.makeText(context, "Click on Product " + product.getName(), Toast.LENGTH_LONG).show();
    }
}
