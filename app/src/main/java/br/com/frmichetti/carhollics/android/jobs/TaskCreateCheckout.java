/**
 *
 * @author Felipe Rodrigues Michetti
 * @see http://portfolio-frmichetti.rhcloud.com
 * @see http://www.codecode.com.br
 * @see mailto:frmichetti@gmail.com
 * */
package br.com.frmichetti.carhollics.android.jobs;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;

import br.com.frmichetti.carhollics.android.R;
import br.com.frmichetti.carhollics.android.dao.HTTP;
import br.com.frmichetti.carhollics.android.model.Checkout;


public class TaskCreateCheckout extends AsyncTask<Checkout,String, Boolean>  {

    public AsyncResponse delegate = null;

    private String url ;

    private Context context;

    private TaskCreateCheckout(){

        Log.d("DEBUG-TASK","create TaskCreateCheckout");

    }

    private TaskCreateCheckout(Context context){
        this();
        this.context = context;
    }

    public TaskCreateCheckout(Context context, AsyncResponse<Boolean> delegate){
        this(context);
        this.delegate = delegate;
    }

    private ProgressDialog dialog;

    @Override
    protected void onPreExecute() {

        super.onPreExecute();

        url = context.getResources().getString(R.string.remote_server) + "/services/pedido/save";

        Log.d("DEBUG-TASK","server config -> " + url);

        dialog = new ProgressDialog(context);

        dialog.setTitle("Processando");

        dialog.setIndeterminate(true);

        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        dialog.setMessage("Iniciando a Tarefa Fazer Pedido");

        dialog.show();
    }

    @Override
    protected Boolean doInBackground(Checkout ... param) {

        String response = "";

        try {

            publishProgress("Enviando Objeto para o Servidor");

            //TODO FIXME Create a JSON

            response = HTTP.sendPost(url, param[0].toString());

        } catch (IOException e) {

            publishProgress("Falha no Envio do Objeto");

            Log.e("Erro", e.toString());

            return null;

        }

        Log.i("Resposta",response);

        //TODO FIXME SEND a Json
/*
        Gson in = new GsonBuilder()
                .setPrettyPrinting()
                .setDateFormat("dd/MM/yyyy").create();


                in.fromJson(response,Boolean.class)


                */

        return true;

    }

    @Override
    protected void onProgressUpdate(String ... values) {

        super.onProgressUpdate(values);

        dialog.setMessage(String.valueOf(values[0]));
    }



    @Override
    protected void onPostExecute(Boolean param) {

        super.onPostExecute(param);

        dialog.setMessage("Tarefa Fazer Pedido Finalizada!");

        dialog.dismiss();

        delegate.processFinish(param);



    }


}
