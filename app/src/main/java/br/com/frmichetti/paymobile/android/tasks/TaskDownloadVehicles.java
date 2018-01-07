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

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import br.com.frmichetti.paymobile.android.R;
import br.com.frmichetti.paymobile.android.dao.HTTP;
import br.com.frmichetti.paymobile.android.model.compatibility.Vehicle;


public class TaskDownloadVehicles extends AsyncTask<Void, String, ArrayList<Vehicle>> {

    public AsyncResponse delegate = null;

    private String url;

    private ArrayList<Vehicle> vehicles;

    private ProgressDialog dialog;

    private Context context;

    public TaskDownloadVehicles(Context context, AsyncResponse<ArrayList<Vehicle>> delegate) {
        this(context);
        this.delegate = delegate;
    }

    private TaskDownloadVehicles(Context context) {
        this();
        this.context = context;
    }

    private TaskDownloadVehicles() {
        Log.d("DEBUG-TASK", "create TaskDownloadVehicles");

    }

    @Override
    protected void onPreExecute() {

        super.onPreExecute();

        url = context.getResources().getString(R.string.server) + "vehicles";

        Log.d("DEBUG-TASK", "server config -> " + url);

        dialog = new ProgressDialog(context);

        dialog.setTitle("Processando");

        dialog.setIndeterminate(true);

        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        dialog.setMessage("Iniciando a Tarefa Obter Lista de Veiculos");

        dialog.show();


    }


    @Override
    protected ArrayList<Vehicle> doInBackground(Void... params) {

        String response = "";

        try {

            publishProgress("Enviando Requisição para o Servidor");

            response = HTTP.sendGet(url);

        } catch (IOException e) {

            publishProgress("Falha ao Obter Resposta");

            Log.e("Erro", e.getMessage());
        }

        publishProgress("Itens recebidos !");

        //TODO FIXME Receive a JSON ARRAy

        vehicles = new Gson().fromJson(response, new TypeToken<List<Vehicle>>() {
        }.getType());

        return (vehicles != null) ? vehicles : new ArrayList<Vehicle>();
    }


    @Override
    protected void onProgressUpdate(String... values) {

        super.onProgressUpdate(values);

        dialog.setMessage(String.valueOf(values[0]));
    }


    @Override
    protected void onPostExecute(ArrayList<Vehicle> result) {

        dialog.setMessage("Tarefa Finalizada!");

        dialog.dismiss();

        delegate.onSuccess(result);

    }


}
