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
import java.util.List;

import br.com.frmichetti.carhollics.android.R;
import br.com.frmichetti.carhollics.android.dao.HTTP;
import br.com.frmichetti.carhollics.json.model.Veiculo;


public class TaskLoadVeiculos extends AsyncTask<Void,String,List<Veiculo>> {

    public AsyncResponse delegate = null;

    private String url ;

    private Gson in;

    private java.lang.reflect.Type collectionType;

    private String json;

    private List<Veiculo> veiculos;

    private ProgressDialog dialog;

    private Context context;

    public TaskLoadVeiculos(Context context, AsyncResponse<List<Veiculo>> delegate){
        this(context);
        this.delegate = delegate;
    }

    private TaskLoadVeiculos(Context context){
        this();
        this.context = context;
    }

    private TaskLoadVeiculos(){
        Log.d("DEBUG-TASK","create TaskLoadVeiculos");

    }

    @Override
    protected void onPreExecute() {

        super.onPreExecute();

        url = context.getResources().getString(R.string.local_server) + "/services/veiculo/list";

        Log.d("DEBUG-TASK","server config -> " + url);

        in = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .setPrettyPrinting()
                .setDateFormat("dd/MM/yyyy").create();

        collectionType = new TypeToken<List<Veiculo>>() {
        }.getType();

        dialog = new ProgressDialog(context);

        dialog.setTitle("Processando");

        dialog.setIndeterminate(true);

        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        dialog.setMessage("Iniciando a Tarefa Obter Lista de Veiculos");

        dialog.show();


    }


    @Override
    protected List<Veiculo> doInBackground(Void ... params) {

        try {

            publishProgress("Enviando Requisição para o Servidor");

            json = HTTP.sendGet(url);

        } catch (IOException e) {

            publishProgress("Falha ao Obter Resposta");

            Log.e("Erro", e.getMessage());
        }

        publishProgress("Itens recebidos !");

        veiculos = in.fromJson(json, collectionType);

        return veiculos;
    }


    @Override
    protected void onProgressUpdate(String ... values) {

        super.onProgressUpdate(values);

        dialog.setMessage(String.valueOf(values[0]));
    }


    @Override
    protected void onPostExecute(List<Veiculo> result) {

        dialog.setMessage("Tarefa Finalizada!");

        dialog.dismiss();

        delegate.processFinish(result);

    }


}
