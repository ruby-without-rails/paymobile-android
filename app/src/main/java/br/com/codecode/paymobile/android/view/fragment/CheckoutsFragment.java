/**
 * @author Felipe Rodrigues Michetti
 * @see http://portfolio-frmichetti.rhcloud.com
 * @see http://www.codecode.com.br
 * @see mailto:frmichetti@gmail.com
 */
package br.com.codecode.paymobile.android.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import br.com.codecode.paymobile.android.R;
import br.com.codecode.paymobile.android.tasks.AsyncResponse;
import br.com.codecode.paymobile.android.tasks.TaskDownloadCheckouts;
import br.com.codecode.paymobile.android.model.compatibility.Checkout;
import br.com.codecode.paymobile.android.view.activity.CheckoutDetailActivity;

import static br.com.codecode.paymobile.android.model.IntentKeys.SELECTED_CHECKOUT_KEY;

public class CheckoutsFragment extends BaseFragment {

    private Checkout selectedCheckout;

    private ArrayList<Checkout> checkouts;

    private ListView listView;

    public CheckoutsFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_checkouts, container, false);

        doCastComponents(rootView);

        doCreateListeners();

        doLoadCheckouts();

        return rootView;
    }

    @Override
    public void doCastComponents(View rootView) {
        listView = rootView.findViewById(R.id.listViewCheckouts);
    }

    private void doLoadCheckouts() {

        if (checkouts == null) {

            Log.d("INFO", "Load Checkouts from webservice");

            TaskDownloadCheckouts taskDownloadCheckouts = new TaskDownloadCheckouts(context,
                    new AsyncResponse<ArrayList<Checkout>>() {

                        @Override
                        public void onSuccess(ArrayList<Checkout> output) {

                            checkouts = output;

                            doFillData(checkouts);
                        }

                        @Override
                        public void onFails(Exception e) {
                            Toast.makeText(context, e.getMessage(),Toast.LENGTH_LONG).show();
                            Log.d("Error", e.getMessage());
                        }
                    });

            taskDownloadCheckouts.execute(customer);
        }


    }

    private void doFillData(List<Checkout> checkouts) {

        ArrayAdapter<Checkout> adpItem = new ArrayAdapter<>(context,
                android.R.layout.simple_list_item_1, checkouts);

        listView.setAdapter(adpItem);
    }

    @Override
    public void doCreateListeners() {

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Object itemValue = listView.getItemAtPosition(position);

                selectedCheckout = (Checkout) itemValue;

                startActivity(new Intent(context, CheckoutDetailActivity.class)
                        .putExtra(SELECTED_CHECKOUT_KEY, selectedCheckout));


            }
        });

    }

}
