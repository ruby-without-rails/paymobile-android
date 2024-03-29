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

import br.com.codecode.paymobile.android.R;
import br.com.codecode.paymobile.android.dao.GsonGetRequest;
import br.com.codecode.paymobile.android.rest.dto.ProductDTO;
import br.com.codecode.paymobile.android.model.RequestQueuer;
import br.com.codecode.paymobile.android.model.compatibility.Product;

public class TaskDownloadProducts extends AsyncTask<String, String, ArrayList<Product>> {
    public AsyncResponse delegate = null;
    private String url;
    private ArrayList<Product> products;
    private ProgressDialog dialog;
    private Context context;
    private RequestQueue requestQueue;

    public TaskDownloadProducts(Context context, AsyncResponse<ArrayList<Product>> delegate) {
        this(context);
        this.delegate = delegate;
    }

    private TaskDownloadProducts(Context context) {
        this();
        this.context = context;
    }

    private TaskDownloadProducts() {
        Log.d("DEBUG-TASK", "create TaskDownloadProducts");
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        url = context.getResources().getString(R.string.server) + "/api/products";

        Log.d("DEBUG-TASK", "server config -> " + url);

        dialog = new ProgressDialog(context);

        dialog.setTitle("Processando");

        dialog.setIndeterminate(true);

        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        dialog.setMessage("Iniciando a Tarefa Obter Lista de Produtos");

        dialog.show();

        // Get a RequestQueue
        requestQueue = RequestQueuer.getInstance(context).getRequestQueue();

    }


    @Override
    protected ArrayList<Product> doInBackground(String... params) {
        publishProgress("Enviando Requisição para o Servidor");

        HashMap<String, String> headers = new HashMap<>();
        headers.put("PayWithRuby-Auth-Token", params[0]);
        GsonGetRequest<ProductDTO> requestCustomer = new GsonGetRequest<>(url, ProductDTO.class, headers, new Response.Listener<ProductDTO>() {
            @Override
            public void onResponse(ProductDTO response) {
                publishProgress("Criando Objetos Produto");
                publishProgress("Itens recebidos !");
                products = response.products;
                delegate.onSuccess(products);
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
        requestQueue.add(requestCustomer);

        return (products != null) ? products : new ArrayList<Product>();
    }

    @Override
    protected void onProgressUpdate(String... values) {
        super.onProgressUpdate(values);

        dialog.setMessage(String.valueOf(values[0]));
    }

    @Override
    protected void onPostExecute(ArrayList<Product> result) {
        dialog.setMessage("Tarefa Finalizada!");

        dialog.dismiss();

        delegate.onSuccess(result);
    }
}
