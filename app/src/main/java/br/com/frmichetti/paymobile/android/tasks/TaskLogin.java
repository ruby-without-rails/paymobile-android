/**
 * @author Felipe Rodrigues Michetti
 * @see http://portfolio-frmichetti.rhcloud.com
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
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import br.com.frmichetti.paymobile.android.MyApplication;
import br.com.frmichetti.paymobile.android.R;
import br.com.frmichetti.paymobile.android.dao.GsonRequest;
import br.com.frmichetti.paymobile.android.dto.CustomerDTO;
import br.com.frmichetti.paymobile.android.model.MySingleton;
import br.com.frmichetti.paymobile.android.model.compatibility.Customer;

public class TaskLogin extends AsyncTask<String, String, String> {
    public AsyncResponse delegate = null;
    protected String token;
    private ProgressDialog dialog;
    private String url;
    private Context context;
    private RequestQueue requestQueue;

    public TaskLogin(Context context, AsyncResponse<String> delegate) {
        this(context);
        this.delegate = delegate;
    }

    private TaskLogin(Context context) {
        this();
        this.context = context;
    }

    private TaskLogin() {
        Log.d("DEBUG-TASK", "create TaskLogin");
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        url = context.getResources().getString(R.string.server) + "/api/auth/login";

        Log.d("DEBUG-TASK", "server config -> " + url);

        dialog = new ProgressDialog(context);

        dialog.setTitle(context.getString(R.string.processing));

        dialog.setIndeterminate(true);

        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        dialog.setMessage("Iniciando a Tarefa de Login");

        dialog.show();

        // Get a RequestQueue
        requestQueue = MySingleton.getInstance(context).getRequestQueue();
    }

    @Override
    protected String doInBackground(String... params) {

        JSONObject payload = new JSONObject();

        try {
            payload.put("fcm_id", params[0]);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("DEBUG", "PAYLOAD");
        Log.d("DEBUG", payload.toString());

        JsonObjectRequest requestToken = new JsonObjectRequest(url, payload, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                if (jsonObject.has("token")) {
                    try {
                        publishProgress("Token recebido !");
                        token = jsonObject.getString("token");
                        MyApplication.setSessionToken(token);
                        delegate.processFinish(token);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                publishProgress("Item não recebido !");
                publishProgress("Ocorreu uma falha ao contactar o servidor !");

                Log.d("DEBUG", "ID Existente no Firebase");
                Log.d("DEBUG", "Cliente Vazio");
                NetworkResponse networkResponse = error.networkResponse;
                delegate.processFinish(token);
            }
        });

        // Add the request to the RequestQueue.
        requestQueue.add(requestToken);

        return token;
    }

    @Override
    protected void onProgressUpdate(String... values) {
        super.onProgressUpdate(values);

        dialog.setMessage(String.valueOf(values[0]));
    }

    @Override
    protected void onPostExecute(String result) {
        dialog.setMessage(context.getString(R.string.process_finish));
        dialog.dismiss();
    }
}
