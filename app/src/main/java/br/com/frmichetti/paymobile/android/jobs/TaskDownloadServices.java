/**
 * @author Felipe Rodrigues Michetti
 * @see http://portfolio-frmichetti.rhcloud.com
 * @see http://www.codecode.com.br
 * @see mailto:frmichetti@gmail.com
 */
package br.com.frmichetti.paymobile.android.jobs;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import br.com.frmichetti.paymobile.android.R;
import br.com.frmichetti.paymobile.android.dao.HTTP;
import br.com.frmichetti.paymobile.android.model.compatibility.Service;


public class TaskDownloadServices extends AsyncTask<Void, String, ArrayList<Service>> {

    public AsyncResponse delegate = null;

    private String url;

    private ArrayList<Service> services;

    private ProgressDialog dialog;

    private Context context;

    public TaskDownloadServices(Context context, AsyncResponse<ArrayList<Service>> delegate) {
        this(context);
        this.delegate = delegate;
    }

    private TaskDownloadServices(Context context) {
        this();
        this.context = context;
    }

    private TaskDownloadServices() {
        Log.d("DEBUG-TASK", "create TaskDownloadServices");

    }

    @Override
    protected void onPreExecute() {

        super.onPreExecute();

        url = context.getResources().getString(R.string.server) + "services";

        Log.d("DEBUG-TASK", "server config -> " + url);

        dialog = new ProgressDialog(context);

        dialog.setTitle("Processando");

        dialog.setIndeterminate(true);

        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        dialog.setMessage("Iniciando a Tarefa Obter Lista de Serviços");

        dialog.show();


    }


    @Override
    protected ArrayList<Service> doInBackground(Void... params) {

        String response = "";

        try {

            publishProgress("Enviando Requisição para o Servidor");

            //TODO FIXME Receive JSON

            response = HTTP.sendGet(url);

        } catch (IOException e) {

            publishProgress("Falha ao Obter Resposta");

            Log.e("Erro", e.getMessage());
        }

        publishProgress("Itens recebidos !");

        //TODO FIXME Response Json

        services = new Gson().fromJson(response, new TypeToken<List<Service>>() {
        }.getType());

        return (services != null) ? services : new ArrayList<Service>();
    }


    @Override
    protected void onProgressUpdate(String... values) {

        super.onProgressUpdate(values);

        dialog.setMessage(String.valueOf(values[0]));
    }


    @Override
    protected void onPostExecute(ArrayList<Service> result) {

        dialog.setMessage("Tarefa Finalizada!");

        dialog.dismiss();

        delegate.processFinish(result);

    }


}