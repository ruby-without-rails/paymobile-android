/**
 * @author Felipe Rodrigues Michetti
 * @see http://portfolio-frmichetti.rhcloud.com
 * @see http://www.codecode.com.br
 * @see mailto:frmichetti@gmail.com
 */
package br.com.frmichetti.paymobile.android.view.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import br.com.frmichetti.paymobile.android.MyApplication;
import br.com.frmichetti.paymobile.android.R;
import br.com.frmichetti.paymobile.android.tasks.AsyncResponse;
import br.com.frmichetti.paymobile.android.tasks.TaskDownloadProducts;
import br.com.frmichetti.paymobile.android.model.compatibility.Product;
import br.com.frmichetti.paymobile.android.view.activity.ServiceDetailActivity;

import static br.com.frmichetti.paymobile.android.MyApplication.getSessionToken;
import static br.com.frmichetti.paymobile.android.model.IntentKeys.PRODUCTS_BUNDLE_KEY;

public class ProductsFragment extends BaseFragment {
    private TextView textView;
    private ListView listView;
    private ArrayList<Product> products;

    public ProductsFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {

        if (savedInstanceState != null) {
            products = (ArrayList<Product>) savedInstanceState.getSerializable(PRODUCTS_BUNDLE_KEY);
        }

        View rootView = inflater.inflate(R.layout.fragment_services, container, false);

        doCastComponents(rootView);

        doCreateListeners();

        doLoadServices(getSessionToken());

        return rootView;
    }

    @Override
    protected void doCastComponents(View rootView) {
        textView = rootView.findViewById(R.id.tvSelectedService);
        listView = rootView.findViewById(R.id.listViewCheckouts);
    }

    @Override
    protected void doCreateListeners() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Object itemValue = listView.getItemAtPosition(position);

                selectedService = (Product) itemValue;

                doChangeActivity(context, ServiceDetailActivity.class);
            }
        });
    }

    private void doLoadServices(String sessionToken) {
        if (products == null) {
            Log.d("[DOWNLOAD-PRODUCTS]", "Load Products from webservice");

            new TaskDownloadProducts(context,
                    new AsyncResponse<ArrayList<Product>>() {

                        @Override
                        public void processFinish(ArrayList<Product> output) {
                            products = output;

                            doFillData(products);
                        }
                    }).execute(sessionToken);
        }
    }

    private void doFillData(ArrayList<Product> products) {
        ArrayAdapter<Product> adpItem = new ArrayAdapter<>(context,
                android.R.layout.simple_list_item_1, products);

        listView.setAdapter(adpItem);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(PRODUCTS_BUNDLE_KEY, products);
    }
}
