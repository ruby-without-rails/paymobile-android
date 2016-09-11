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
import java.util.ArrayList;
import java.util.List;

import br.com.frmichetti.carhollics.android.R;
import br.com.frmichetti.carhollics.android.dao.HTTP;
import br.com.frmichetti.carhollics.android.model.compatibility.Address;
import br.com.frmichetti.carhollics.android.model.compatibility.Customer;


public class TaskDownloadAddress extends AsyncTask<Customer, String, List<Address>> {

    public AsyncResponse delegate = null;

    private String url;

    private List<Address> addresses;

    private ProgressDialog dialog;

    private Context context;

    public TaskDownloadAddress(Context context, AsyncResponse<List<Address>> delegate) {
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

        url = context.getResources().getString(R.string.local_server) + "find/addrbycus";

        Log.d("DEBUG-TASK", "server config -> " + url);

        dialog = new ProgressDialog(context);

        dialog.setTitle("Processando");

        dialog.setIndeterminate(true);

        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        dialog.setMessage("Iniciando a Tarefa Obter Lista de Endereços");

        dialog.show();


    }


    @Override
    protected List<Address> doInBackground(Customer ... params) {

        String response = "";

        try {

            publishProgress("Enviando Requisição para o Servidor");

            response = HTTP.sendPost(url,
                    new Gson().toJson(params[0]));

        } catch (IOException e) {

            publishProgress("Falha ao Obter Resposta");

            Log.e("Erro", e.getMessage());
        }

        publishProgress("Itens recebidos !");

        //TODO FIXME Receive a JSON ARRAy

        addresses = new Gson().fromJson(response, new TypeToken<List<Address>>(){}.getType());

        return (addresses != null) ? addresses : new ArrayList<Address>();
    }


    @Override
    protected void onProgressUpdate(String... values) {

        super.onProgressUpdate(values);

        dialog.setMessage(String.valueOf(values[0]));
    }


    @Override
    protected void onPostExecute(List<Address> result) {

        dialog.setMessage("Tarefa Finalizada!");

        dialog.dismiss();

        delegate.processFinish(result);

    }


}
