/**
 * @author Felipe Rodrigues Michetti
 * @see http://portfolio-frmichetti.rhcloud.com
 * @see mailto:frmichetti@gmail.com
 */
package br.com.frmichetti.paymobile.android.jobs;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import br.com.frmichetti.paymobile.android.R;
import br.com.frmichetti.paymobile.android.dao.GsonRequest;
import br.com.frmichetti.paymobile.android.dto.CustomerDTO;
import br.com.frmichetti.paymobile.android.model.MySingleton;
import br.com.frmichetti.paymobile.android.model.compatibility.Customer;

public class TaskLoginFirebase extends AsyncTask<JSONObject, String, Customer> {
    public AsyncResponse delegate = null;
    protected Customer customer;
    private ProgressDialog dialog;
    private String url;
    private Context context;
    private boolean removeOnFail;

    public TaskLoginFirebase(Context context, AsyncResponse<Customer> delegate, boolean removeOnFail) {
        this(context);
        this.delegate = delegate;
        this.removeOnFail = removeOnFail;
    }

    private TaskLoginFirebase(Context context) {
        this();
        this.context = context;
    }

    private TaskLoginFirebase() {
        Log.d("DEBUG-TASK", "create TaskLoginFirebase");
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
    }

    @Override
    protected Customer doInBackground(JSONObject... params) {
        // Get a RequestQueue
        RequestQueue queue = MySingleton.getInstance(context).getRequestQueue();
        try {
            final JSONObject jsonBody = params[0];

            Log.d("DEBUG", "PAYLOAD");
            Log.d("DEBUG", jsonBody.toString());

            final JsonObjectRequest requestToken = new JsonObjectRequest(url, jsonBody, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject jsonObject) {
                    publishProgress("Token recebido !");
                    String token = "";

                    if (jsonObject.has("token")) {
                        try {
                            token = jsonObject.getString("token");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        String urlLogin = context.getString(R.string.server) + "/api/auth/user_data";

                        HashMap<String, String> headers = new HashMap<>();
                        headers.put("PayWithRuby-Auth-Token", token);
                        GsonRequest<CustomerDTO> requestCustomer = new GsonRequest<>(urlLogin, CustomerDTO.class, headers, new Response.Listener<CustomerDTO>() {
                            @Override
                            public void onResponse(CustomerDTO response) {
                                publishProgress("Criando Objeto Cliente");
                                customer = response.customer;

                                publishProgress("Login Validado!");
                                publishProgress("Entrando...");
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                publishProgress("Item não recebido !");
                                publishProgress("Ocorreu uma falha ao contactar o servidor !");
                                NetworkResponse networkResponse = error.networkResponse;

                                if (removeOnFail) {
                                    FirebaseAuth.getInstance().getCurrentUser().delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (!(task.isSuccessful())) {
                                                Toast.makeText(context, context.getString(R.string.delete_account_failed), Toast.LENGTH_LONG).show();
                                                Toast.makeText(context, context.getString(R.string.could_not_authorize), Toast.LENGTH_LONG).show();
                                            } else {
                                                Toast.makeText(context, context.getString(R.string.delete_account_success), Toast.LENGTH_LONG).show();
                                                Toast.makeText(context, context.getString(R.string.repeat_register_operation), Toast.LENGTH_LONG).show();
                                            }
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Log.d("DEBUG", "Falha ao desvincular objeto no Firebase contate o Administrador do sistema.");
                                            Log.d("DEBUG", e.getMessage());
                                            publishProgress("Falha ao desvincular objeto no Firebase contate o Administrador do sistema.");
                                        }
                                    });
                                }
                            }
                        });

                        // Add a request (in this example, called stringRequest) to your RequestQueue.
                        MySingleton.getInstance(context).addToRequestQueue(requestCustomer);
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
                }
            });

            // Add the request to the RequestQueue.
            queue.add(requestToken);

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
        dialog.setMessage(context.getString(R.string.process_finish));

        dialog.dismiss();

        delegate.processFinish(result);

    }
}
