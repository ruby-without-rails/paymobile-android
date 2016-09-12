/**
 * @author Felipe Rodrigues Michetti
 * @see http://portfolio-frmichetti.rhcloud.com
 * @see http://www.codecode.com.br
 * @see mailto:frmichetti@gmail.com
 */
package br.com.frmichetti.carhollics.android.jobs;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;

import br.com.frmichetti.carhollics.android.R;
import br.com.frmichetti.carhollics.android.dao.HTTP;
import br.com.frmichetti.carhollics.android.model.compatibility.Customer;

public class TaskCreateCustomer extends AsyncTask<Customer, String, Customer> {

    public AsyncResponse delegate = null;

    private String url;

    private ProgressDialog dialog;

    private Context context;

    private String response;


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

        url = context.getResources().getString(R.string.local_server) + "save/customer";

        Log.d("DEBUG-TASK", "server config -> " + url);

        dialog = new ProgressDialog(context);

        dialog.setTitle("Processando");

        dialog.setIndeterminate(true);

        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        dialog.setMessage("Iniciando a Tarefa Criar Novo Login");

        dialog.show();

    }

    @Override
    protected Customer doInBackground(Customer... params) {

        try {

            publishProgress("Enviando Requisição para o Servidor");

            //TODO FIXME Create a JSON

            response = HTTP.sendRequest(url, "POST" ,new Gson().toJson(params[0]));

        } catch (IOException e) {

            publishProgress("Falha ao Obter Resposta");

            Log.e("Erro", e.getMessage());
        }

        publishProgress("Item recebido !");

        //TODO FIXME Receive a JSON

        Customer c = new Gson().fromJson(response, new TypeToken<Customer>(){}.getType());

        return c;

    }

    @Override
    protected void onProgressUpdate(String ... values) {

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
