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

import java.io.IOException;

import br.com.frmichetti.carhollics.android.R;
import br.com.frmichetti.carhollics.android.dao.HTTP;
import br.com.frmichetti.carhollics.android.model.compatibility.Address;

public class TaskDeleteAddress extends AsyncTask<Address, String, Void> {

    private String url;

    private ProgressDialog dialog;

    private Context context;

    private String response;

    private TaskDeleteAddress() {

        Log.d("DEBUG-TASK", "create TaskDeleteAddress");
    }

    public TaskDeleteAddress(Context context) {
        this();
        this.context = context;
    }

    @Override
    protected void onPreExecute() {

        super.onPreExecute();

        url = context.getResources().getString(R.string.remote_server) + "delete/address";

        Log.d("DEBUG-TASK", "server config -> " + url);

        dialog = new ProgressDialog(context);

        dialog.setTitle("Processando");

        dialog.setIndeterminate(true);

        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        dialog.setMessage("Iniciando a Tarefa Deletar Endereço");

        dialog.show();

    }

    @Override
    protected Void doInBackground(Address ... params) {

        try {

            publishProgress("Enviando Requisição para o Servidor");

            response = HTTP.sendRequest(url,"DELETE",new Gson().toJson(params[0]));

        } catch (IOException e) {

            publishProgress("Falha ao Obter Resposta");

            Log.e("Erro", e.getMessage());
        }

        return null;

    }

    @Override
    protected void onProgressUpdate(String ... values) {

        super.onProgressUpdate(values);

        dialog.setMessage(String.valueOf(values[0]));
    }


    @Override
    protected void onPostExecute(Void result) {

        dialog.setMessage("Tarefa Finalizada!");

        dialog.dismiss();


    }
}
