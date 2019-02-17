/**
 * @author Felipe Rodrigues Michetti
 * @see http://portfolio-frmichetti.rhcloud.com
 * @see http://www.codecode.com.br
 * @see mailto:frmichetti@gmail.com
 */
package br.com.codecode.paymobile.android.tasks;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.android.volley.NetworkResponse;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import br.com.codecode.paymobile.android.R;
import br.com.codecode.paymobile.android.dao.GsonGetRequest;
import br.com.codecode.paymobile.android.model.RequestQueuer;
import br.com.codecode.paymobile.android.model.compatibility.Customer;
import br.com.codecode.paymobile.android.model.compatibility.Order;
import br.com.codecode.paymobile.android.rest.dto.OrderDTO;


public class TaskDownloadOrders extends AsyncTask<String, String, List<Order>> {

    public AsyncResponse delegate = null;

    private String url;

    private String response;

    private List<Order> orders;

    private ProgressDialog dialog;

    private Context context;
    private RequestQueue requestQueue;

    public TaskDownloadOrders(Context context, AsyncResponse<List<Order>> delegate) {
        this(context);
        this.delegate = delegate;
    }

    private TaskDownloadOrders(Context context) {
        this();
        this.context = context;
    }

    private TaskDownloadOrders() {
        Log.d("DEBUG-TASK", "create TaskDownloadOrders");
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

        dialog.setMessage("Iniciando a Tarefa Obter Lista de Pedidos");

        dialog.show();

        // Get a RequestQueue
        requestQueue = RequestQueuer.getInstance(context).getRequestQueue();


    }


    @Override
    protected ArrayList<Order> doInBackground(String... params) {
        publishProgress("Enviando Requisição para o Servidor");

        HashMap<String, String> headers = new HashMap<>();
        headers.put("PayWithRuby-Auth-Token", params[0]);
        GsonGetRequest<OrderDTO> requestOrders = new GsonGetRequest<>(url, OrderDTO.class, headers, new Response.Listener<OrderDTO>() {
            @Override
            public void onResponse(OrderDTO response) {
                publishProgress("Criando Objetos Checkout");
                publishProgress("Itens recebidos !");
                orders = response.orders;
                delegate.onSuccess(orders);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                publishProgress("Falha ao Obter Resposta");
                Log.e("Erro", error.toString());

                publishProgress("Itens não recebidos !");
                publishProgress("Ocorreu uma falha ao contactar o servidor !");
                NetworkResponse networkResponse = error.networkResponse;
                delegate.onFails(error);

            }
        });

        // Add the request to the RequestQueue.
        requestQueue.add(requestOrders);

        return (orders != null) ? (ArrayList<Order>) orders : new ArrayList<Order>();
    }


    @Override
    protected void onProgressUpdate(String... values) {

        super.onProgressUpdate(values);

        dialog.setMessage(String.valueOf(values[0]));
    }


    @Override
    protected void onPostExecute(List<Order> result) {

        dialog.setMessage("Tarefa Finalizada!");

        dialog.dismiss();

        delegate.onSuccess(result);

    }


}
