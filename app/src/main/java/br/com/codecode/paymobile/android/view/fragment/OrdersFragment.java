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

import br.com.codecode.paymobile.android.MyApplication;
import br.com.codecode.paymobile.android.R;
import br.com.codecode.paymobile.android.model.compatibility.Order;
import br.com.codecode.paymobile.android.tasks.AsyncResponse;
import br.com.codecode.paymobile.android.tasks.TaskDownloadOrders;
import br.com.codecode.paymobile.android.view.activity.OrderDetailActivity;

import static br.com.codecode.paymobile.android.model.IntentKeys.SELECTED_ORDER_KEY;

public class OrdersFragment extends BaseFragment {

    private Order selectedOrder;

    private List<Order> orders;

    private ListView listView;

    public OrdersFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_orders, container, false);

        doCastComponents(rootView);

        doCreateListeners();

        doLoadOrders();

        return rootView;
    }

    @Override
    public void doCastComponents(View rootView) {
        listView = rootView.findViewById(R.id.listViewCheckouts);
    }

    private void doLoadOrders() {

        if (orders == null) {

            Log.d("INFO", "Load Orders from webservice");

            TaskDownloadOrders taskDownloadOrders = new TaskDownloadOrders(context,
                    new AsyncResponse<List<Order>>() {

                        @Override
                        public void onSuccess(List<Order> output) {

                            orders = output;

                            doFillData(orders);
                        }

                        @Override
                        public void onFails(Exception e) {
                            Toast.makeText(context, e.toString(), Toast.LENGTH_LONG).show();
                            Log.d("Error", e.toString());
                        }
                    });

            taskDownloadOrders.execute(MyApplication.getInstance().getSessionToken().getKey());
        }


    }

    private void doFillData(List<Order> checkouts) {

        ArrayAdapter<Order> adpItem = new ArrayAdapter<>(context,
                android.R.layout.simple_list_item_1, checkouts);

        listView.setAdapter(adpItem);
    }

    @Override
    public void doCreateListeners() {

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Object itemValue = listView.getItemAtPosition(position);

                selectedOrder = (Order) itemValue;

                startActivity(new Intent(context, OrderDetailActivity.class)
                        .putExtra(SELECTED_ORDER_KEY, selectedOrder));


            }
        });

    }

}
