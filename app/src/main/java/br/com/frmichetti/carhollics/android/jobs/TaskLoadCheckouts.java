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
import java.util.List;

import br.com.frmichetti.carhollics.android.R;
import br.com.frmichetti.carhollics.android.dao.HTTP;
import br.com.frmichetti.carhollics.android.model.Checkout;
import br.com.frmichetti.carhollics.android.model.Customer;


public class TaskLoadCheckouts extends AsyncTask<Customer,String,List<Checkout>> {

    public AsyncResponse delegate = null;

    private String url ;

    private String json;

    private List<Checkout> checkouts;

    private ProgressDialog dialog;

    private Context context;

    public TaskLoadCheckouts(Context context, AsyncResponse<List<Checkout>> delegate){
        this(context);
        this.delegate = delegate;
    }

    private TaskLoadCheckouts(Context context){
        this();
        this.context = context;
    }

    private TaskLoadCheckouts(){
        Log.d("DEBUG-TASK","create TaskLoadServicos");
    }

    @Override
    protected void onPreExecute() {

        super.onPreExecute();

        url = context.getResources().getString(R.string.remote_server) + "/services/pedido/list";

        Log.d("DEBUG-TASK","server config -> " + url);

        /*

        in = new GsonBuilder()
                .serializeSpecialFloatingPointValues()
                .enableComplexMapKeySerialization()
                .excludeFieldsWithoutExposeAnnotation()
                .setPrettyPrinting()
                .setDateFormat("dd/MM/yyyy").create();

        collectionType = new TypeToken<List<Pedido>>() {}.getType();*/

        //TODO FIXME Receive a JSON Array

        dialog = new ProgressDialog(context);

        dialog.setTitle("Processando");

        dialog.setIndeterminate(true);

        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        dialog.setMessage("Iniciando a Tarefa Obter Lista de Pedidos");

        dialog.show();


    }


    @Override
    protected List<Checkout> doInBackground(Customer ... params) {

        try {

            publishProgress("Enviando Requisição para o Servidor");

            /*

            out = new GsonBuilder()
                    .serializeSpecialFloatingPointValues()
                    .enableComplexMapKeySerialization()
                    .excludeFieldsWithoutExposeAnnotation()
                    .setPrettyPrinting()
                    .setDateFormat("dd/MM/yyyy").create();*/

            //TODO FIXME Receive a JSONArray

            json = HTTP.sendPost(url,params[0].toString());

        } catch (IOException e) {

            publishProgress("Falha ao Obter Resposta");

            Log.e("Erro", e.getMessage());
        }

        publishProgress("Itens recebidos !");

        //TODO FIXME Receive a Json Array

        checkouts = null;

        return checkouts;
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
