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
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.frmichetti.carhollics.android.R;
import br.com.frmichetti.carhollics.android.dao.HTTP;
import br.com.frmichetti.carhollics.android.model.compatibility.Checkout;
import br.com.frmichetti.carhollics.android.model.compatibility.Customer;
import br.com.frmichetti.carhollics.android.util.GsonDateDeserializer;


public class TaskDownloadCheckouts extends AsyncTask<Customer, String, List<Checkout>> {

    public AsyncResponse delegate = null;

    private String url;

    private String response;

    private List<Checkout> checkouts;

    private ProgressDialog dialog;

    private Context context;

    public TaskDownloadCheckouts(Context context, AsyncResponse<List<Checkout>> delegate) {
        this(context);
        this.delegate = delegate;
    }

    private TaskDownloadCheckouts(Context context) {
        this();
        this.context = context;
    }

    private TaskDownloadCheckouts() {
        Log.d("DEBUG-TASK", "create TaskDownloadServices");
    }

    @Override
    protected void onPreExecute() {

        super.onPreExecute();

        url = context.getResources().getString(R.string.server) + "find/chkbycus";

        Log.d("DEBUG-TASK", "server config -> " + url);


        dialog = new ProgressDialog(context);

        dialog.setTitle("Processando");

        dialog.setIndeterminate(true);

        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        dialog.setMessage("Iniciando a Tarefa Obter Lista de Pedidos");

        dialog.show();


    }


    @Override
    protected List<Checkout> doInBackground(Customer... params) {

        try {

            publishProgress("Enviando Requisição para o Servidor");

            response = HTTP.sendRequest(url,"POST",
                    new Gson().toJson(params[0]));

        } catch (IOException e) {

            publishProgress("Falha ao Obter Resposta");

            Log.e("Erro", e.getMessage());
        }

        publishProgress("Itens recebidos !");

        //TODO FIXME Receive a Json Array

        checkouts = new GsonBuilder()
                .registerTypeAdapter(Date.class, new GsonDateDeserializer())
                .setDateFormat("MM-dd-yyyy'T'HH:mm:ss")
                .create()
                .fromJson(response, new TypeToken<List<Checkout>>(){}.getType());

        return (checkouts != null) ? (checkouts) : (new ArrayList<Checkout>());
    }


    @Override
    protected void onProgressUpdate(String... values) {

        super.onProgressUpdate(values);

        dialog.setMessage(String.valueOf(values[0]));
    }


    @Override
    protected void onPostExecute(List<Checkout> result) {

        dialog.setMessage("Tarefa Finalizada!");

        dialog.dismiss();

        delegate.processFinish(result);

    }


}
