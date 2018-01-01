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
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import br.com.frmichetti.paymobile.android.R;
import br.com.frmichetti.paymobile.android.model.MySingleton;
import br.com.frmichetti.paymobile.android.model.compatibility.Customer;

public class TaskCreateCustomer extends AsyncTask<JSONObject, String, Customer> {
    public AsyncResponse delegate = null;
    private String url;
    private ProgressDialog dialog;
    private Context context;
    private Customer customer;

    private TaskCreateCustomer() {
        Log.d("DEBUG-TASK", "create TaskCreateCustomer");
    }

    private TaskCreateCustomer(Context context) {
        this();
        this.context = context;
    }

    public TaskCreateCustomer(Context context, AsyncResponse<Customer> delegate) {
        this(context);
        this.delegate = delegate;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        url = context.getResources().getString(R.string.server) + "/api/customer";

        Log.d("DEBUG-TASK", "server config -> " + url);

        dialog = new ProgressDialog(context);

        dialog.setTitle("Processando");

        dialog.setIndeterminate(true);

        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        dialog.setMessage("Iniciando a Tarefa Criar Novo Login");

        dialog.show();

    }

    @Override
    protected Customer doInBackground(JSONObject... params) {
        publishProgress("Enviando Requisição para o Servidor");

        // Get a RequestQueue
        RequestQueue queue = MySingleton.getInstance(context).getRequestQueue();

        try {
            JSONObject jsonBody = params[0];

            Log.d("DEBUG", "PAYLOAD");
            Log.d("DEBUG", jsonBody.toString());

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, jsonBody, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject jsonObject) {
                    publishProgress("Item recebido !");
                    if (jsonObject.has("validation_errors")) {
                        throw new RuntimeException("Invalid response from server - Incorrect Payload -> \n" + jsonObject.toString());
                    }
                    customer = new Customer(jsonObject);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    publishProgress("Item não recebido !");
                    publishProgress("Ocorreu uma falha ao contactar o servidor !");
                    customer = null;
                }
            });

            // Add the request to the RequestQueue.
            queue.add(jsonObjectRequest);

        } catch (Exception e) {
            e.printStackTrace();
            publishProgress("Falha ao Obter Resposta");
            Log.e("Erro", e.getMessage());
        }
        return customer;
    }

    @Override
    protected void onProgressUpdate(String... values) {
        super.onProgressUpdate(values);

        dialog.setMessage(String.valueOf(values[0]));
    }

    @Override
    protected void onPostExecute(Customer result) {
        dialog.setMessage("Tarefa Finalizada!");

        dialog.dismiss();

        delegate.processFinish(result);
    }
}
