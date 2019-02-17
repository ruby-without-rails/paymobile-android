/**
 * @author Felipe Rodrigues Michetti
 * @see http://portfolio-frmichetti.rhcloud.com
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
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import br.com.codecode.paymobile.android.MyApplication;
import br.com.codecode.paymobile.android.R;
import br.com.codecode.paymobile.android.dao.GsonGetRequest;
import br.com.codecode.paymobile.android.dao.GsonPostRequest;
import br.com.codecode.paymobile.android.model.RequestQueuer;
import br.com.codecode.paymobile.android.model.Token;
import br.com.codecode.paymobile.android.rest.dto.ProductDTO;

public class TaskLogout extends AsyncTask<String, String, Void> {
    public AsyncResponse asyncResponse = null;
    protected String token;
    private ProgressDialog dialog;
    private String url;
    private Context context;
    private RequestQueue requestQueue;

    public TaskLogout(Context context, AsyncResponse<String> asyncResponse) {
        this(context);
        this.asyncResponse = asyncResponse;
    }

    private TaskLogout(Context context) {
        this();
        this.context = context;
    }

    private TaskLogout() {
        Log.d("DEBUG-TASK", "create TaskLogout");
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        url = context.getResources().getString(R.string.server) + "/api/auth/logout";

        Log.d("DEBUG-TASK", "server config -> " + url);

        dialog = new ProgressDialog(context);

        dialog.setTitle(context.getString(R.string.processing));

        dialog.setIndeterminate(true);

        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        dialog.setMessage("Iniciando a Tarefa de Logout");

        if (context.getClass() != MyApplication.class) {
            dialog.show();
        }


        // Get a RequestQueue
        requestQueue = RequestQueuer.getInstance(context).getRequestQueue();
    }

    @Override
    protected Void doInBackground(String... params) {

        publishProgress("Enviando Requisição para o Servidor");

        HashMap<String, String> headers = new HashMap<>();
        headers.put("PayWithRuby-Auth-Token", params[0]);
        HashMap<String, String> parameters = new HashMap<>();
        GsonPostRequest<Object> requestlogout = new GsonPostRequest<>(url, Object.class, headers, parameters, new Response.Listener<Object>() {
            @Override
            public void onResponse(Object response) {
                asyncResponse.onSuccess("Logout realizado com sucesso!");
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                publishProgress("Falha ao Obter Resposta");
                Log.e("Erro", error.toString());

                publishProgress("Ocorreu uma falha ao contactar o servidor !");
                NetworkResponse networkResponse = error.networkResponse;
                asyncResponse.onFails(error);

            }
        });

        // Add the request to the RequestQueue.
        requestQueue.add(requestlogout);
        return null;
    }

    @Override
    protected void onProgressUpdate(String... values) {
        super.onProgressUpdate(values);

        dialog.setMessage(String.valueOf(values[0]));
    }

    @Override
    protected void onPostExecute(Void result) {
        dialog.setMessage(context.getString(R.string.process_finish));
        dialog.dismiss();
    }
}
