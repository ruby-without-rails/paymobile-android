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

import br.com.frmichetti.carhollics.android.dao.HTTP;
import br.com.frmichetti.carhollics.android.model.Cliente;
import br.com.frmichetti.carhollics.android.model.Usuario;


public class TaskLoginFirebase extends AsyncTask<String,String, Cliente> {

    public AsyncResponse delegate = null;

    private ProgressDialog dialog;

    private final String URL = "http://192.168.1.32:8080/carhollics-webservice/services/usuario/firebaselogin";

    private String json;

    private Cliente cliente;

    private Gson in,out ;

    private java.lang.reflect.Type collectionType;

    private Context context;

    public TaskLoginFirebase(Context context, AsyncResponse<Cliente> delegate){
        this(context);
        this.delegate = delegate;
    }

    private TaskLoginFirebase(Context context){
        this.context = context;
    }

    private TaskLoginFirebase(){

    }


    @Override
    protected void onPreExecute() {

        super.onPreExecute();

        out = new Gson();

        in = new GsonBuilder()
                .serializeNulls()
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
    protected Cliente doInBackground(String ... params) {

        try {

            publishProgress("Enviando Objeto para o Servidor");

            json = HTTP.sendPost(URL, out.toJson(params[0]));


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
