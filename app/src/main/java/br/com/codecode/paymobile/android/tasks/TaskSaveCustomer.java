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

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONObject;

import java.util.HashMap;

import br.com.codecode.paymobile.android.MyApplication;
import br.com.codecode.paymobile.android.R;
import br.com.codecode.paymobile.android.dao.GsonPostRequest;
import br.com.codecode.paymobile.android.rest.dto.CustomerDTO;
import br.com.codecode.paymobile.android.model.RequestQueuer;
import br.com.codecode.paymobile.android.model.compatibility.Customer;

public class TaskSaveCustomer extends AsyncTask<JSONObject, String, Customer> {
    public AsyncResponse asyncResponse = null;
    private String url;
    private ProgressDialog dialog;
    private Context context;
    private Customer customer;

    private TaskSaveCustomer() {
        Log.d("DEBUG-TASK", "create TaskSaveCustomer");
    }

    private TaskSaveCustomer(Context context) {
        this();
        this.context = context;
    }

    public TaskSaveCustomer(Context context, AsyncResponse<Customer> asyncResponse) {
        this(context);
        this.asyncResponse = asyncResponse;
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
        RequestQueue queue = RequestQueuer.getInstance(context).getRequestQueue();

        try {
            JSONObject jsonBody = params[0];

            Log.d("DEBUG", "PAYLOAD");
            Log.d("DEBUG", jsonBody.toString());

            HashMap<String, String> headers = new HashMap<>();
            headers.put("PayWithRuby-Auth-Token", MyApplication.getSessionToken().getKey());
            headers.put("Content-type", "application/json; charset=utf-8");

            HashMap<String, String> parameters = new HashMap<>();
            parameters.put("id", jsonBody.getString("id"));
            parameters.put("cpf", jsonBody.getString("cpf"));
            parameters.put("name", jsonBody.getString("name"));
            parameters.put("fcm_id", jsonBody.getString("fcm_id"));
            parameters.put("fcm_message_token", jsonBody.getString("fcm_message_token"));
            parameters.put("email", jsonBody.getString("email"));
            parameters.put("mobile_phone_number", jsonBody.getString("mobile_phone_number"));

            GsonPostRequest<CustomerDTO> gsonPostRequest = new GsonPostRequest<>(url, CustomerDTO.class, headers, parameters,
                    new Response.Listener<CustomerDTO>() {
                        @Override
                        public void onResponse(CustomerDTO customerDTO) {
                            publishProgress("Item recebido !");
                            if (customerDTO.validationErrors != null && !customerDTO.validationErrors.isEmpty()) {
                                throw new RuntimeException("Invalid response from server - Incorrect Payload -> \n" + customerDTO.toString());
                            }
                            if (customerDTO.token != null) {
                                publishProgress("Token recebido !");
                                publishProgress("Atualizando Token...");
                                MyApplication.setSessionToken(customerDTO.token);
                            }
                            customer = customerDTO.customer;
                            asyncResponse.onSuccess(customer);

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    publishProgress("Item não recebido !");
                    publishProgress("Ocorreu uma falha ao contactar o servidor !");
                    customer = null;
                    asyncResponse.onFails(error);
                }
            });

            // Add the request to the RequestQueue.
            queue.add(gsonPostRequest);

        } catch (Exception e) {
            e.printStackTrace();
            publishProgress("Falha ao Obter Resposta");
            Log.e("Erro", e.toString());
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

        asyncResponse.onSuccess(result);
    }
}
