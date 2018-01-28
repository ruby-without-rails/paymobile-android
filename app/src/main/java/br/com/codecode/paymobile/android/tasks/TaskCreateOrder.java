package br.com.codecode.paymobile.android.tasks;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONObject;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import br.com.codecode.paymobile.android.MyApplication;
import br.com.codecode.paymobile.android.R;
import br.com.codecode.paymobile.android.dao.GsonPostRequest;
import br.com.codecode.paymobile.android.model.ShoppingItem;
import br.com.codecode.paymobile.android.rest.RestApi;
import br.com.codecode.paymobile.android.rest.RestApiFactory;
import br.com.codecode.paymobile.android.rest.dto.OrderDTO;
import br.com.codecode.paymobile.android.model.RequestQueuer;
import br.com.codecode.paymobile.android.model.ShoppingCart;
import br.com.codecode.paymobile.android.model.compatibility.Order;
import br.com.codecode.paymobile.android.rest.payloads.OrderPayload;
import retrofit2.Call;

/**
 * Created by felipe on 28/01/18.
 */

public class TaskCreateOrder extends AsyncTask<ShoppingCart, String, OrderDTO> {

    private RestApiFactory restApiFactory;
    private RestApi restApi;
    private ProgressDialog dialog;
    private Context context;
    private OrderDTO order;

    private TaskCreateOrder(){}

    public TaskCreateOrder(Context context) {
        this();
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        restApiFactory = new RestApiFactory(context, MyApplication.getSessionToken().getKey());
        restApi = restApiFactory.create(RestApi.class);

        dialog = new ProgressDialog(context);

        dialog.setTitle("Processando");

        dialog.setIndeterminate(true);

        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        dialog.setMessage("Iniciando a Tarefa Criar Novo Pedido");

        // TODO - FIXME
       // dialog.show();

    }

    @Override
    protected OrderDTO doInBackground(ShoppingCart... shoppingCarts) {

        OrderPayload payload = prepareOrderPayload(shoppingCarts[0]);

        Call<OrderDTO> responseBodyCall = restApi.createOrder(payload);
        retrofit2.Response<OrderDTO> bodyResponse = null;

        try {
            bodyResponse = responseBodyCall.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }

        order = bodyResponse.body();

        return order;
    }

    @Override
    protected void onProgressUpdate(String... values) {
        super.onProgressUpdate(values);

        dialog.setMessage(String.valueOf(values[0]));
    }

    @Override
    protected void onPostExecute(OrderDTO result) {
        dialog.setMessage("Tarefa Finalizada!");

        dialog.dismiss();
    }

    protected OrderPayload prepareOrderPayload(ShoppingCart shoppingCart) {

        List<OrderPayload.PayloadProduct> products = new ArrayList<>();
        for (ShoppingItem shoppingItem : shoppingCart.getList()) {
            OrderPayload.PayloadProduct payProduct = new OrderPayload.PayloadProduct();
            payProduct.description = shoppingItem.getProduct().getDescription();
            payProduct.name = shoppingItem.getProduct().getName();
            payProduct.quantity = shoppingCart.getQuantityOfItens(shoppingItem);
            payProduct.value = shoppingItem.getPrice();
            products.add(payProduct);
        }


        OrderPayload.PayloadCart payCart = new OrderPayload.PayloadCart();
        payCart.products = products;

        OrderPayload payload = new OrderPayload();
        payload.discount = shoppingCart.getDiscountTotal();
        payload.total = shoppingCart.getTotal();
        payload.cart = payCart;

        return payload;

    }
}
