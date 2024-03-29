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

import com.google.gson.GsonBuilder;

import java.io.IOException;

import br.com.codecode.paymobile.android.R;
import br.com.codecode.paymobile.android.dao.HTTP;
import br.com.codecode.paymobile.android.model.compatibility.Checkout;


public class TaskCreateCheckout extends AsyncTask<Checkout, String, Boolean> {

    public AsyncResponse delegate = null;

    private String url;

    private Context context;
    private ProgressDialog dialog;

    private TaskCreateCheckout() {
        Log.d("DEBUG-TASK", "create TaskCreateCheckout");
    }

    private TaskCreateCheckout(Context context) {
        this();
        this.context = context;
    }

    public TaskCreateCheckout(Context context, AsyncResponse<Boolean> delegate) {
        this(context);
        this.delegate = delegate;
    }

    @Override
    protected void onPreExecute() {

        super.onPreExecute();

        url = context.getResources().getString(R.string.server) + "checkouts";

        Log.d("DEBUG-TASK", "server config -> " + url);

        dialog = new ProgressDialog(context);

        dialog.setTitle("Processando");

        dialog.setIndeterminate(true);

        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        dialog.setMessage("Iniciando a Tarefa Fazer Pedido");

        dialog.show();
    }

    @Override
    protected Boolean doInBackground(Checkout... param) {

        String response = "";

        try {

            publishProgress("Enviando Objeto para o Servidor");

            response = HTTP.sendRequest(url,"POST", new GsonBuilder()
                    .setPrettyPrinting()
                    .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                    .enableComplexMapKeySerialization()
                    .create().toJson(param[0]));

        } catch (IOException e) {

            publishProgress("Falha no Envio do Objeto");

            Log.e("Erro", e.toString());

            return null;

        }

        Log.i("Resposta", response);

        return true;

    }

    @Override
    protected void onProgressUpdate(String... values) {

        super.onProgressUpdate(values);

        dialog.setMessage(String.valueOf(values[0]));
    }

    @Override
    protected void onPostExecute(Boolean param) {

        super.onPostExecute(param);

        dialog.setMessage("Tarefa Fazer Pedido Finalizada!");

        dialog.dismiss();

        delegate.onSuccess(param);

    }


}
