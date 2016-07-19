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
import br.com.frmichetti.carhollics.android.model.Cliente;
import br.com.frmichetti.carhollics.android.model.Usuario;


public class TaskLogin extends AsyncTask<Usuario,String, Cliente> {

    public AsyncResponse delegate = null;

    private ProgressDialog dialog;

    private String url;

    private String json;

    private Cliente cliente;

    private Gson in,out ;

    private java.lang.reflect.Type collectionType;

    private Context context;

    public TaskLogin(Context context,AsyncResponse<Cliente> delegate){
        this(context);
        this.delegate = delegate;
    }

    private TaskLogin(Context context){
        this.context = context;
    }

    private TaskLogin(){

        Log.d("DEBUG-TASK","create TaskLogin");


    }


    @Override
    protected void onPreExecute() {

        super.onPreExecute();

        url = context.getResources().getString(R.string.local_server) + "/services/usuario/login";

        Log.d("DEBUG-TASK","server config -> " + url);

        out = new Gson();

        in = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .setPrettyPrinting()
                .setDateFormat("dd/MM/yyyy").create();

        collectionType = new TypeToken<Cliente>() {}.getType();

        dialog = new ProgressDialog(context);

        dialog.setTitle("Processando");

        dialog.setIndeterminate(true);

        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        dialog.setMessage("Iniciando a Tarefa de Login");

        dialog.show();


    }

    @Override
    protected Cliente doInBackground(Usuario... params) {

        try {

            publishProgress("Enviando Objeto para o Servidor");

            json = HTTP.sendPost(url, out.toJson(params[0]));


            publishProgress("Objeto recebido");

            if (in==null || in.equals("")){

                publishProgress("Servidor Respondeu Null");

            }else

            {
                publishProgress("Criando Objeto Cliente");

                cliente = in.fromJson(json, collectionType);
            }


        } catch (IOException e) {

            publishProgress("Falha ao Receber Objeto");

            Log.e("Erro", e.getMessage());

        }
            publishProgress("Login Validado!");

            publishProgress("Entrando...");

        return cliente;
    }


    @Override
    protected void onProgressUpdate(String... values) {

        super.onProgressUpdate(values);

        dialog.setMessage(String.valueOf(values[0]));
    }


    @Override
    protected void onPostExecute(Cliente result) {

        dialog.setMessage("Tarefa Finalizada!");

        dialog.dismiss();

        delegate.processFinish(result);

    }



}
