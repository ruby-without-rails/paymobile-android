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

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.List;

import br.com.frmichetti.carhollics.android.R;
import br.com.frmichetti.carhollics.android.dao.HTTP;
import br.com.frmichetti.carhollics.json.model.Cliente;
import br.com.frmichetti.carhollics.json.model.Pedido;


public class TaskLoadPedidos extends AsyncTask<Cliente,String,List<Pedido>> {

    public AsyncResponse delegate = null;

    private String url ;

    private Gson in,out;

    private java.lang.reflect.Type collectionType;

    private String json;

    private List<Pedido> pedidos;

    private ProgressDialog dialog;

    private Context context;

    public TaskLoadPedidos(Context context, AsyncResponse<List<Pedido>> delegate){
        this(context);
        this.delegate = delegate;
    }

    private TaskLoadPedidos(Context context){
        this();
        this.context = context;
    }

    private TaskLoadPedidos(){
        Log.d("DEBUG-TASK","create TaskLoadServicos");
    }

    @Override
    protected void onPreExecute() {

        super.onPreExecute();

        url = context.getResources().getString(R.string.local_server) + "/services/pedido/list";

        Log.d("DEBUG-TASK","server config -> " + url);

        in = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .setPrettyPrinting()
                .setDateFormat("dd/MM/yyyy").create();

        collectionType = new TypeToken<List<Pedido>>() {}.getType();

        dialog = new ProgressDialog(context);

        dialog.setTitle("Processando");

        dialog.setIndeterminate(true);

        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        dialog.setMessage("Iniciando a Tarefa Obter Lista de Pedidos");

        dialog.show();


    }


    @Override
    protected List<Pedido> doInBackground(Cliente ... params) {

        try {

            publishProgress("Enviando Requisição para o Servidor");

            out = new GsonBuilder()
                    .excludeFieldsWithoutExposeAnnotation()
                    .setPrettyPrinting()
                    .setDateFormat("dd/MM/yyyy").create();

            json = HTTP.sendPost(url,out.toJson(params[0]));

        } catch (IOException e) {

            publishProgress("Falha ao Obter Resposta");

            Log.e("Erro", e.getMessage());
        }

        publishProgress("Itens recebidos !");

        pedidos = in.fromJson(json, collectionType);

        return pedidos;
    }


    @Override
    protected void onProgressUpdate(String... values) {

        super.onProgressUpdate(values);

        dialog.setMessage(String.valueOf(values[0]));
    }


    @Override
    protected void onPostExecute(List<Pedido> result) {

        dialog.setMessage("Tarefa Finalizada!");

        dialog.dismiss();

        delegate.processFinish(result);

    }


}
