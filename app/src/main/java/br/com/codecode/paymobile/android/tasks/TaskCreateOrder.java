package br.com.codecode.paymobile.android.tasks;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONObject;

import java.util.HashMap;

import br.com.codecode.paymobile.android.MyApplication;
import br.com.codecode.paymobile.android.R;
import br.com.codecode.paymobile.android.dao.GsonPostRequest;
import br.com.codecode.paymobile.android.dto.OrderDTO;
import br.com.codecode.paymobile.android.model.RequestQueuer;
import br.com.codecode.paymobile.android.model.ShoppingCart;
import br.com.codecode.paymobile.android.model.compatibility.Order;

/**
 * Created by felipe on 28/01/18.
 */

public class TaskCreateOrder extends AsyncTask<ShoppingCart, String, Order> {

    public AsyncResponse asyncResponse = null;
    private String url;
    private ProgressDialog dialog;
    private Context context;
    private Order order;

    public TaskCreateOrder(Context context, AsyncResponse asyncResponse) {
        this.asyncResponse = asyncResponse;
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        url = context.getResources().getString(R.string.server) + "/api/orders";

        Log.d("DEBUG-TASK", "server config -> " + url);

        dialog = new ProgressDialog(context);

        dialog.setTitle("Processando");

        dialog.setIndeterminate(true);

        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        dialog.setMessage("Iniciando a Tarefa Criar Novo Pedido");

        dialog.show();

    }

    @Override
    protected Order doInBackground(ShoppingCart... shoppingCarts) {
        // Get a RequestQueue
        RequestQueue queue = RequestQueuer.getInstance(context).getRequestQueue();

        ShoppingCart shoppingCart = shoppingCarts[0];

        try {
            JSONObject jsonBody = new JSONObject();

            Log.d("DEBUG", "PAYLOAD");
            Log.d("DEBUG", jsonBody.toString());

            HashMap<String, String> headers = new HashMap<>();
            headers.put("PayWithRuby-Auth-Token", MyApplication.getSessionToken().getKey());
            headers.put("Content-type", "application/json; charset=utf-8");

            HashMap<String, String> parameters = new HashMap<>();
            parameters.put("discount", jsonBody.getString("discount"));
            parameters.put("total", jsonBody.getString("total"));
            parameters.put("cart", jsonBody.getString("cart"));

            GsonPostRequest<OrderDTO> gsonPostRequest = new GsonPostRequest<>(url, OrderDTO.class, headers, parameters,
                    new Response.Listener<OrderDTO>() {
                        @Override
                        public void onResponse(OrderDTO orderDTO) {
                            publishProgress("Item recebido !");
                            if (orderDTO.validationErrors != null && !orderDTO.validationErrors.isEmpty()) {
                                throw new RuntimeException("Invalid response from server - Incorrect Payload -> \n" + orderDTO.toString());
                            }
                            if (orderDTO.order != null) {
                                publishProgress("Pedido recebido !");

                            }
                            order = orderDTO.order;
                            asyncResponse.onSuccess(order);

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    publishProgress("Item não recebido !");
                    publishProgress("Ocorreu uma falha ao contactar o servidor !");
                    order = null;
                    asyncResponse.onFails(error);
                }
            });

            // Add the request to the RequestQueue.
            queue.add(gsonPostRequest);

        } catch (Exception e) {
            e.printStackTrace();
            publishProgress("Falha ao Obter Resposta");
            Log.e("Erro", e.getMessage());
        }


        return order;
    }

    @Override
    protected void onProgressUpdate(String... values) {
        super.onProgressUpdate(values);

        dialog.setMessage(String.valueOf(values[0]));
    }

    @Override
    protected void onPostExecute(Order result) {
        dialog.setMessage("Tarefa Finalizada!");

        dialog.dismiss();

        asyncResponse.onSuccess(result);
    }
}
