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
import br.com.frmichetti.carhollics.json.model.Veiculo;

public class TaskCreateVeiculo extends AsyncTask<Veiculo,String,Veiculo> {

    public AsyncResponse delegate = null;

    private String url ;

    private java.lang.reflect.Type collectionType;

    private String json;

    private Veiculo veiculo;

    private ProgressDialog dialog;

    private Context context;


    private TaskCreateVeiculo(){

        Log.d("DEBUG-TASK","create TaskCreateUsuario");


    }

    private TaskCreateVeiculo(Context context) {
        this();
        this.context = context;
    }

    public TaskCreateVeiculo(Context context, AsyncResponse<Veiculo> delegate){
        this(context);
        this.delegate = delegate;
    }

    @Override
    protected void onPreExecute() {

        super.onPreExecute();

        url = context.getResources().getString(R.string.local_server) + "/services/veiculo/save";

        Log.d("DEBUG-TASK","server config -> " + url);

        dialog = new ProgressDialog(context);

        dialog.setTitle("Processando");

        dialog.setIndeterminate(true);

        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        dialog.setMessage("Iniciando a Tarefa Criar Novo Veículo");

        dialog.show();

    }

    @Override
    protected Veiculo doInBackground(Veiculo ... params) {

        try {

            publishProgress("Enviando Requisição para o Servidor");

            json = HTTP.sendPost(url,params[0].toGson());

        } catch (IOException e) {

            publishProgress("Falha ao Obter Resposta");

            Log.e("Erro", e.getMessage());
        }

        publishProgress("Item recebido !");

        Veiculo v = Veiculo.fromGson(json);

        return v;

    }

    @Override
    protected void onProgressUpdate(String ... values) {

        super.onProgressUpdate(values);

        dialog.setMessage(String.valueOf(values[0]));
    }


    @Override
    protected void onPostExecute(Veiculo result) {

        dialog.setMessage("Tarefa Finalizada!");

        dialog.dismiss();

        delegate.processFinish(result);

    }
}
