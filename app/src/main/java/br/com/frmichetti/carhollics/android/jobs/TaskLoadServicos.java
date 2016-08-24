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
import br.com.frmichetti.carhollics.android.model.Service;


public class TaskLoadServicos extends AsyncTask<Void,String,List<Service>> {

    public AsyncResponse delegate = null;

    private String url ;

    private List<Service> servicos;

    private ProgressDialog dialog;

    private Context context;

    public TaskLoadServicos(Context context, AsyncResponse<List<Service>> delegate){
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

        url = context.getResources().getString(R.string.remote_server) + "/services/servico/list";

        Log.d("DEBUG-TASK","server config -> " + url);

        dialog = new ProgressDialog(context);

        dialog.setTitle("Processando");

        dialog.setIndeterminate(true);

        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        dialog.setMessage("Iniciando a Tarefa Obter Lista de Serviços");

        dialog.show();


    }


    @Override
    protected List<Service> doInBackground(Void... params) {

        String response = "";

        try {

            publishProgress("Enviando Requisição para o Servidor");

            //TODO FIXME Receive JSON

            response = HTTP.sendGet(url);

        } catch (IOException e) {

            publishProgress("Falha ao Obter Resposta");

            Log.e("Erro", e.getMessage());
        }

        publishProgress("Itens recebidos !");

        //TODO FIXME Response Json

        servicos = null;

        return servicos;
    }


    @Override
    protected void onProgressUpdate(String... values) {

        super.onProgressUpdate(values);

        dialog.setMessage(String.valueOf(values[0]));
    }


    @Override
    protected void onPostExecute(List<Service> result) {

        dialog.setMessage("Tarefa Finalizada!");

        dialog.dismiss();

        delegate.processFinish(result);

    }


}
