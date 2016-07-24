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
import br.com.frmichetti.carhollics.android.model.Usuario;

public class TaskCreateUsuario extends AsyncTask<Usuario,String,Usuario> {

    public AsyncResponse delegate = null;

    private String url ;

    private java.lang.reflect.Type collectionType;

    private String json;

    private ProgressDialog dialog;

    private Context context;


    private TaskCreateUsuario(){

        Log.d("DEBUG-TASK","create TaskCreateUsuario");

    }

    private TaskCreateUsuario(Context context) {
        this();
        this.context = context;
    }

    public TaskCreateUsuario(Context context, AsyncResponse<Usuario> delegate){
        this(context);
        this.delegate = delegate;
    }

    @Override
    protected void onPreExecute() {

        super.onPreExecute();

        url = context.getResources().getString(R.string.local_server) + "/services/usuario/save";

        Log.d("DEBUG-TASK","server config -> " + url);

        dialog = new ProgressDialog(context);

        dialog.setTitle(context.getString(R.string.processing));

        dialog.setIndeterminate(true);

        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        dialog.setMessage("Iniciando a Tarefa Criar Novo Login");

        dialog.show();

    }

    @Override
    protected Usuario doInBackground(Usuario ... params) {

        try {

            publishProgress("Enviando Requisição para o Servidor");

            json = HTTP.sendPost(url,params[0].toGson());

        } catch (IOException e) {

            publishProgress("Falha ao Obter Resposta");

            Log.e("Erro", e.getMessage());
        }

        publishProgress("Item recebido !");

        Usuario u = Usuario.fromGson(json);

        return u;

    }

    @Override
    protected void onProgressUpdate(String ... values) {

        super.onProgressUpdate(values);

        dialog.setMessage(String.valueOf(values[0]));
    }


    @Override
    protected void onPostExecute(Usuario result) {

        dialog.setMessage(context.getString(R.string.process_finish));

        dialog.dismiss();

        delegate.processFinish(result);

    }
}
