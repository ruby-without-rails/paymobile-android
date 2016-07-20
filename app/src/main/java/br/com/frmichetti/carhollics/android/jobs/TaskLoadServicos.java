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
import br.com.frmichetti.carhollics.json.model.Servico;


public class TaskLoadServicos extends AsyncTask<Void,String,List<Servico>> {

    public AsyncResponse delegate = null;

    private String url ;

    private Gson in;

    private java.lang.reflect.Type collectionType;

    private String json;

    private List<Servico> servicos;

    private ProgressDialog dialog;

    private Context context;

    public TaskLoadServicos(Context context, AsyncResponse<List<Servico>> delegate){
        this(context);
        this.delegate = delegate;
    }

    private TaskLoadServicos(Context context){
        this();
        this.context = context;
    }

    private TaskLoadServicos(){
        Log.d("DEBUG-TASK","create TaskLoadServicos");

    }

    @Override
    protected void onPreExecute() {

        super.onPreExecute();

        url = context.getResources().getString(R.string.local_server) + "/services/servico/list";

        Log.d("DEBUG-TASK","server config -> " + url);

        in = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .setPrettyPrinting()
                .setDateFormat("dd/MM/yyyy").create();

        collectionType = new TypeToken<List<Servico>>() {
        }.getType();

        dialog = new ProgressDialog(context);

        dialog.setTitle("Processando");

        dialog.setIndeterminate(true);

        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        dialog.setMessage("Iniciando a Tarefa Obter Lista de Serviços");

        dialog.show();


    }


    @Override
    protected List<Servico> doInBackground(Void... params) {

        try {

            publishProgress("Enviando Requisição para o Servidor");

            json = HTTP.sendGet(url);

        } catch (IOException e) {

            publishProgress("Falha ao Obter Resposta");

            Log.e("Erro", e.getMessage());
        }

        publishProgress("Itens recebidos !");

        servicos = in.fromJson(json, collectionType);

        return servicos;
    }


    @Override
    protected void onProgressUpdate(String... values) {

        super.onProgressUpdate(values);

        dialog.setMessage(String.valueOf(values[0]));
    }


    @Override
    protected void onPostExecute(List<Servico> result) {

        dialog.setMessage("Tarefa Finalizada!");

        dialog.dismiss();

        delegate.processFinish(result);

    }


}
