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

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;

import br.com.codecode.paymobile.android.R;
import br.com.codecode.paymobile.android.dao.HTTP;
import br.com.codecode.paymobile.android.model.compatibility.Address;

public class TaskCreateAddress extends AsyncTask<Address, String, Address> {

    public AsyncResponse delegate = null;

    private String url;

    private ProgressDialog dialog;

    private Context context;

    private String response;

    private TaskCreateAddress() {

        Log.d("DEBUG-TASK", "create TaskCreateUser");
    }

    private TaskCreateAddress(Context context) {
        this();
        this.context = context;
    }

    public TaskCreateAddress(Context context, AsyncResponse<Address> delegate) {
        this(context);
        this.delegate = delegate;
    }

    @Override
    protected void onPreExecute() {

        super.onPreExecute();

        url = context.getResources().getString(R.string.server) + "save/address";

        Log.d("DEBUG-TASK", "server config -> " + url);

        dialog = new ProgressDialog(context);

        dialog.setTitle("Processando");

        dialog.setIndeterminate(true);

        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        dialog.setMessage("Iniciando a Tarefa Criar Novo Endereço");

        dialog.show();

    }

    @Override
    protected Address doInBackground(Address ... params) {

        try {

            publishProgress("Enviando Requisição para o Servidor");

            response = HTTP.sendRequest(url,"POST",new Gson().toJson(params[0]));

        } catch (IOException e) {

            publishProgress("Falha ao Obter Resposta");

            Log.e("Erro", e.toString());
        }

        publishProgress("Item recebido !");

        Address a = new Gson().fromJson(response, new TypeToken<Address>(){}.getType());

        return (a != null) ? (a) : (new Address());

    }

    @Override
    protected void onProgressUpdate(String ... values) {

        super.onProgressUpdate(values);

        dialog.setMessage(String.valueOf(values[0]));
    }


    @Override
    protected void onPostExecute(Address result) {

        dialog.setMessage("Tarefa Finalizada!");

        dialog.dismiss();

        delegate.onSuccess(result);

    }
}
