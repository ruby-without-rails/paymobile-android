/**
 * @author Felipe Rodrigues Michetti
 * @see http://portfolio-frmichetti.rhcloud.com
 * @see http://www.codecode.com.br
 * @see mailto:frmichetti@gmail.com
 */
package br.com.frmichetti.paymobile.android.tasks;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import br.com.frmichetti.paymobile.android.R;
import br.com.frmichetti.paymobile.android.dao.GsonRequest;
import br.com.frmichetti.paymobile.android.dao.HTTP;
import br.com.frmichetti.paymobile.android.dto.CustomerDTO;
import br.com.frmichetti.paymobile.android.dto.ProductDTO;
import br.com.frmichetti.paymobile.android.model.MySingleton;
import br.com.frmichetti.paymobile.android.model.compatibility.Product;

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
        requestQueue = MySingleton.getInstance(context).getRequestQueue();

    }


    @Override
    protected ArrayList<Product> doInBackground(String... params) {
        publishProgress("Enviando Requisição para o Servidor");

        HashMap<String, String> headers = new HashMap<>();
        headers.put("PayWithRuby-Auth-Token", params[0]);
        GsonRequest<ProductDTO> requestCustomer = new GsonRequest<>(url, ProductDTO.class, headers, new Response.Listener<ProductDTO>() {
            @Override
            public void onResponse(ProductDTO response) {
                publishProgress("Criando Objeto Cliente");
                publishProgress("Itens recebidos !");
                products = response.products;
                delegate.processFinish(products);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                publishProgress("Falha ao Obter Resposta");
                Log.e("Erro", error.getMessage());

                publishProgress("Itens não recebidos !");
                publishProgress("Ocorreu uma falha ao contactar o servidor !");
                NetworkResponse networkResponse = error.networkResponse;

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

        delegate.processFinish(result);
    }
}
