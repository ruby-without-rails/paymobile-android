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
import br.com.frmichetti.carhollics.android.model.compatibility.User;

public class TaskCreateUser extends AsyncTask<User,String,User> {

    public AsyncResponse delegate = null;

    private String url ;

    private ProgressDialog dialog;

    private Context context;


    private TaskCreateUser(){

        Log.d("DEBUG-TASK","create TaskCreateUser");

    }

    private TaskCreateUser(Context context) {
        this();
        this.context = context;
    }

    public TaskCreateUser(Context context, AsyncResponse<User> delegate){
        this(context);
        this.delegate = delegate;
    }

    @Override
    protected void onPreExecute() {

        super.onPreExecute();

        url = context.getResources().getString(R.string.local_server) + "users";

        Log.d("DEBUG-TASK","server config -> " + url);

        dialog = new ProgressDialog(context);

        dialog.setTitle(context.getString(R.string.processing));

        dialog.setIndeterminate(true);

        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        dialog.setMessage("Iniciando a Tarefa Criar Novo Login");

        dialog.show();

    }

    @Override
    protected User doInBackground(User ... params) {

        try {

            publishProgress("Enviando Requisição para o Servidor");

            String response = "";

            //TODO FIXME Make a Json

            response = HTTP.sendPost(url,params[0].toString());

        } catch (IOException e) {

            publishProgress("Falha ao Obter Resposta");

            Log.e("Erro", e.getMessage());
        }

        publishProgress("Item recebido !");

        //TODO FIXME Receive a Json

        User u = null;

        return u;

    }

    @Override
    protected void onProgressUpdate(String ... values) {

        super.onProgressUpdate(values);

        dialog.setMessage(String.valueOf(values[0]));
    }


    @Override
    protected void onPostExecute(User result) {

        dialog.setMessage(context.getString(R.string.process_finish));

        dialog.dismiss();

        delegate.processFinish(result);

    }
}
