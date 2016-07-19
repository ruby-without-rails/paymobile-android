/**
 *
 * @author Felipe Rodrigues Michetti
 * @see http://portfolio-frmichetti.rhcloud.com
 * @see mailto:frmichetti@gmail.com
 * */
package br.com.frmichetti.carhollics.android.jobs;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;

import br.com.frmichetti.carhollics.android.R;
import br.com.frmichetti.carhollics.android.dao.HTTP;
import br.com.frmichetti.carhollics.android.model.Pedido;


public class TaskFazerPedido extends AsyncTask<Pedido,String, Boolean>  {

    public AsyncResponse delegate = null;

    private String url ;

    private Gson in,out;

    private java.lang.reflect.Type collectionType;

    private String json ;

    private Context context;

    private TaskFazerPedido(){

        Log.d("DEBUG-TASK","create TaskFazerPedido");

    }

    private TaskFazerPedido(Context context){
        this();
        this.context = context;
    }

    public TaskFazerPedido(Context context,AsyncResponse<Boolean> delegate){
        this(context);
        this.delegate = delegate;
    }

    private ProgressDialog dialog;

    @Override
    protected void onPreExecute() {

        super.onPreExecute();

        url = context.getResources().getString(R.string.local_server) + "/services/pedido/create";

        Log.d("DEBUG-TASK","server config -> " + url);

        out = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .setPrettyPrinting()
                .setDateFormat("dd/MM/yyyy").create();

        collectionType = new TypeToken<Boolean>() {}.getType();

        dialog = new ProgressDialog(context);

        dialog.setTitle("Processando");

        dialog.setIndeterminate(true);

        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        dialog.setMessage("Iniciando a Tarefa Fazer Pedido");

        dialog.show();
    }

    @Override
    protected Boolean doInBackground(Pedido ... param) {

        publishProgress("Criando Pedido");

        Pedido pedido = param[0];


        try {

            publishProgress("Enviando Objeto para o Servidor");

            json = HTTP.sendPost(url, out.toJson(pedido));

        } catch (IOException e) {

            publishProgress("Falha no Envio do Objeto");

            Log.e("Erro", e.toString());

            return null;

        }

        Log.i("Resposta",json);

        in = new GsonBuilder()
                .setPrettyPrinting()
                .setDateFormat("dd/MM/yyyy").create();

        return in.fromJson(json,Boolean.class);

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
