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
import br.com.frmichetti.carhollics.android.model.Customer;
import br.com.frmichetti.carhollics.android.model.User;

@Deprecated
public class TaskLogin extends AsyncTask<User,String, Customer> {

    public AsyncResponse delegate = null;

    private ProgressDialog dialog;

    private String url;

    private Customer customer;

    private Context context;

    public TaskLogin(Context context,AsyncResponse<Customer> delegate){
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

        url = context.getResources().getString(R.string.remote_server) + "/services/usuario/login";

        Log.d("DEBUG-TASK","server config -> " + url);

        dialog = new ProgressDialog(context);

        dialog.setTitle("Processando");

        dialog.setIndeterminate(true);

        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        dialog.setMessage("Iniciando a Tarefa de Login");

        dialog.show();


    }

    @Override
    protected Customer doInBackground(User ... params) {

        String response = "";

        try {

            publishProgress("Enviando Objeto para o Servidor");

            //TODO FIXME Create a Json to Login

            response = HTTP.sendPost(url, params[0].toString());

            publishProgress("Objeto recebido");

            if (response == null || response.equals("")){

                publishProgress("Servidor Respondeu Null");

                throw new RuntimeException("Servidor Respondeu Null");

            }else

            {
                publishProgress("Criando Objeto Cliente");

                //TODO FIXME Receive Json to Login

               // customer = Cliente.fromGson(response);
            }


        } catch (IOException e) {

            publishProgress("Falha ao Receber Objeto");

            Log.e("Erro", e.getMessage());

        }
            publishProgress("Login Validado!");

            publishProgress("Entrando...");

        return customer;
    }


    @Override
    protected void onProgressUpdate(String ... values) {

        super.onProgressUpdate(values);

        dialog.setMessage(String.valueOf(values[0]));
    }


    @Override
    protected void onPostExecute(Customer result) {

        dialog.setMessage("Tarefa Finalizada!");

        dialog.dismiss();

        delegate.processFinish(result);

    }



}
