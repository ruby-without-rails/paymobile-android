/**
 * @author Felipe Rodrigues Michetti
 * @see http://portfolio-frmichetti.rhcloud.com
 * @see mailto:frmichetti@gmail.com
 */
package br.com.codecode.paymobile.android.tasks;

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

import java.util.HashMap;

import br.com.codecode.paymobile.android.MyApplication;
import br.com.codecode.paymobile.android.R;
import br.com.codecode.paymobile.android.dao.GsonGetRequest;
import br.com.codecode.paymobile.android.rest.dto.CustomerDTO;
import br.com.codecode.paymobile.android.model.RequestQueuer;
import br.com.codecode.paymobile.android.model.compatibility.Customer;

public class TaskRequestCustomerData extends AsyncTask<String, String, Customer> {
    public AsyncResponse asyncResponse = null;
    protected Customer customer;
    private ProgressDialog dialog;
    private String url;
    private Context context;
    private boolean removeOnFail;
    private RequestQueue requestQueue;

    public TaskRequestCustomerData(Context context, boolean removeOnFail, AsyncResponse<Customer> asyncResponse) {
        this(context);
        this.removeOnFail = removeOnFail;
        this.asyncResponse = asyncResponse;
    }

    private TaskRequestCustomerData(Context context) {
        this();
        this.context = context;
    }

    private TaskRequestCustomerData() {
        Log.d("DEBUG-TASK", "create TaskLogin");
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        url = context.getResources().getString(R.string.server) + "/api/auth/user_data";

        Log.d("DEBUG-TASK", "server config -> " + url);

        dialog = new ProgressDialog(context);

        dialog.setTitle(context.getString(R.string.processing));

        dialog.setIndeterminate(true);

        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        dialog.setMessage("Iniciando a Tarefa de Login");

        if (context.getClass() != MyApplication.class) {
            dialog.show();
        }


        // Get a RequestQueue
        requestQueue = RequestQueuer.getInstance(context).getRequestQueue();

    }

    @Override
    protected Customer doInBackground(String... params) {
        Log.d("DEBUG", "PAYLOAD");
        Log.d("DEBUG", params[0]);

        HashMap<String, String> headers = new HashMap<>();
        headers.put("PayWithRuby-Auth-Token", params[0]);
        GsonGetRequest<CustomerDTO> requestCustomer = new GsonGetRequest<>(url, CustomerDTO.class, headers, new Response.Listener<CustomerDTO>() {
            @Override
            public void onResponse(CustomerDTO response) {
                publishProgress("Criando Objeto Cliente");
                customer = response.customer;

                publishProgress("Login Validado!");
                publishProgress("Entrando...");

                asyncResponse.onSuccess(customer);
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
                    asyncResponse.onFails(error);
                }
            }
        });

        // Add the request to the RequestQueue.
        requestQueue.add(requestCustomer);

        return customer;
    }

    @Override
    protected void onProgressUpdate(String... values) {
        super.onProgressUpdate(values);

        dialog.setMessage(String.valueOf(values[0]));
    }

    @Override
    protected void onPostExecute(Customer customer) {
        dialog.setMessage(context.getString(R.string.process_finish));
        dialog.dismiss();
    }
}
