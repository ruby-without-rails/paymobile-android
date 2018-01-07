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

import br.com.frmichetti.paymobile.android.R;
import br.com.frmichetti.paymobile.android.dao.HTTP;
import br.com.frmichetti.paymobile.android.model.compatibility.Address;
import br.com.frmichetti.paymobile.android.model.compatibility.Customer;


public class TaskDownloadAddress extends AsyncTask<Customer, String, ArrayList<Address>> {

    public AsyncResponse delegate = null;

    private String url;

    private ArrayList<Address> addresses;

    private ProgressDialog dialog;

    private Context context;

    public TaskDownloadAddress(Context context, AsyncResponse<ArrayList<Address>> delegate) {
        this(context);
        this.delegate = delegate;
    }

    private TaskDownloadAddress(Context context) {
        this();
        this.context = context;
    }

    private TaskDownloadAddress() {
        Log.d("DEBUG-TASK", "create TaskDownloadAddresses");

    }

    @Override
    protected void onPreExecute() {

        super.onPreExecute();

        url = context.getResources().getString(R.string.server) + "find/addrbycus";

        Log.d("DEBUG-TASK", "server config -> " + url);

        dialog = new ProgressDialog(context);

        dialog.setTitle("Processando");

        dialog.setIndeterminate(true);

        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        dialog.setMessage("Iniciando a Tarefa Obter Lista de Endereços");

        dialog.show();


    }


    @Override
    protected ArrayList<Address> doInBackground(Customer... params) {

        String response = "";

        try {

            publishProgress("Enviando Requisição para o Servidor");

            response = HTTP.sendRequest(url, "POST",
                    new Gson().toJson(params[0]));

        } catch (IOException e) {

            publishProgress("Falha ao Obter Resposta");

            Log.e("Erro", e.getMessage());
        }

        publishProgress("Itens recebidos !");

        //TODO FIXME Receive a JSON ARRAy

        addresses = new Gson().fromJson(response, new TypeToken<ArrayList<Address>>() {
        }.getType());

        return (addresses != null) ? addresses : new ArrayList<Address>();
    }


    @Override
    protected void onProgressUpdate(String... values) {

        super.onProgressUpdate(values);

        dialog.setMessage(String.valueOf(values[0]));
    }


    @Override
    protected void onPostExecute(ArrayList<Address> result) {

        dialog.setMessage("Tarefa Finalizada!");

        dialog.dismiss();

        delegate.onSuccess(result);

    }


}
