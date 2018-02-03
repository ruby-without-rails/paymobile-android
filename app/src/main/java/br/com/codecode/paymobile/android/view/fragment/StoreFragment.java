package br.com.codecode.paymobile.android.view.fragment;


import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
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

import br.com.codecode.paymobile.android.R;
import br.com.codecode.paymobile.android.adapter.StoreAdapter;
import br.com.codecode.paymobile.android.helper.GridSpacingItemDecoration;
import br.com.codecode.paymobile.android.listener.CardItemClickListener;
import br.com.codecode.paymobile.android.listener.ItemAdapterListener;
import br.com.codecode.paymobile.android.listener.RecyclerTouchListener;
import br.com.codecode.paymobile.android.model.compatibility.Product;
import br.com.codecode.paymobile.android.tasks.AsyncResponse;
import br.com.codecode.paymobile.android.tasks.TaskDownloadProducts;
import br.com.codecode.paymobile.android.view.activity.ProductDetailActivity;

import static br.com.codecode.paymobile.android.MyApplication.getSessionToken;
import static br.com.codecode.paymobile.android.model.IntentKeys.PRODUCTS_BUNDLE_KEY;

public class StoreFragment extends BaseFragment implements ItemAdapterListener {

    private static final String TAG = StoreFragment.class.getSimpleName();

    private RecyclerView recyclerView;
    private List<Product> products;
    private StoreAdapter adapter;
    private Context context;
    private int lastItemSelected;

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
    protected void doCastComponents(View rootView) {

    }

    @Override
    protected void doCreateListeners() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (savedInstanceState != null) {
            products = (ArrayList<Product>) savedInstanceState.getSerializable(PRODUCTS_BUNDLE_KEY);
        }

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_store, container, false);

        recyclerView = view.findViewById(R.id.recycler_view);
        products = new ArrayList<>();
        adapter = new StoreAdapter(products,getActivity(), this);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(context, 3);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(8), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(context,
                recyclerView, new CardItemClickListener() {
            @Override
            public void onClick(View view, final int position) {
                lastItemSelected = position;
                //Values are passing to activity & to fragment as well

                selectedProduct = products.get(lastItemSelected);

                doChangeActivity(context, ProductDetailActivity.class);
            }

            @Override
            public void onLongClick(View view, int position) {
                lastItemSelected = position;
            }
        }));

        doLoadProducts(getSessionToken().getKey());

        return view;
    }

    /**
     * fetching shopping item by making http call
     */
    private void doLoadProducts(String sessionToken) {
        if (products == null || products.isEmpty()) {
            Log.d("[DOWNLOAD-PRODUCTS]", "Load Products from webservice");

            new TaskDownloadProducts(context,
                    new AsyncResponse<ArrayList<Product>>() {
                        @Override
                        public void onSuccess(ArrayList<Product> output) {
                            products.clear();
                            products.addAll(output);

                            // refreshing recycler view
                            adapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onFails(Exception e) {
                            Toast.makeText(context, e.toString(), Toast.LENGTH_LONG).show();
                            Log.d("Error", e.toString());
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

        selectedProduct = product;

     //   doChangeActivity(context, ProductDetailActivity.class);

    }

    protected void makeToast(String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}
