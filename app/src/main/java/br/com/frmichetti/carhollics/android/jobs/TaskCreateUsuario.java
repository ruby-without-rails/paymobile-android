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
import br.com.frmichetti.carhollics.android.model.Usuario;

/**
 * Created by Felipe on 05/07/2016.
 */
public class TaskCreateUsuario extends AsyncTask<Usuario,String,Usuario> {

    public AsyncResponse delegate = null;

    private String url ;

    private Gson in,out;

    private java.lang.reflect.Type collectionType;

    private String json;

    private Usuario usuario;

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

        in = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .setPrettyPrinting()
                .setDateFormat("dd/MM/yyyy").create();

        collectionType = new TypeToken<Usuario>() {
        }.getType();

        dialog = new ProgressDialog(context);

        dialog.setTitle("Processando");

        dialog.setIndeterminate(true);

        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        dialog.setMessage("Iniciando a Tarefa Criar Novo Login");

        dialog.show();

    }

    @Override
    protected Usuario doInBackground(Usuario ... params) {

        out = new GsonBuilder()
                .setPrettyPrinting()
                .setDateFormat("dd/MM/yyyy").create();

        collectionType = new TypeToken<Usuario>() {
        }.getType();

        try {

            publishProgress("Enviando Requisição para o Servidor");

            json = HTTP.sendPost(url,out.toJson(params[0]));

        } catch (IOException e) {

            publishProgress("Falha ao Obter Resposta");

            Log.e("Erro", e.getMessage());
        }

        publishProgress("Item recebido !");

        Usuario u = in.fromJson(json, collectionType);

        return u;

    }

    @Override
    protected void onProgressUpdate(String ... values) {

        super.onProgressUpdate(values);

        dialog.setMessage(String.valueOf(values[0]));
    }


    @Override
    protected void onPostExecute(Usuario result) {

        dialog.setMessage("Tarefa Finalizada!");

        dialog.dismiss();

        delegate.processFinish(result);

    }
}
