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
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import java.io.IOException;

import br.com.frmichetti.carhollics.android.R;
import br.com.frmichetti.carhollics.android.dao.HTTP;
import br.com.frmichetti.carhollics.android.model.Customer;


public class TaskLoginFirebase extends AsyncTask<String,String, Customer> {

    public AsyncResponse delegate = null;

    private ProgressDialog dialog;

    private String url ;

    private Customer cliente;

    private Context context;

    public TaskLoginFirebase(Context context, AsyncResponse<Customer> delegate){
        this(context);
        this.delegate = delegate;
    }

    private TaskLoginFirebase(Context context){
        this.context = context;
    }

    private TaskLoginFirebase(){

        Log.d("DEBUG-TASK","create TaskLoginFirebase");

    }


    @Override
    protected void onPreExecute() {

        super.onPreExecute();

        url = context.getResources().getString(R.string.remote_server) + "/services/usuario/firebaselogin";

        Log.d("DEBUG-TASK","server config -> " + url);

      /*  out = new Gson();

        in = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .setPrettyPrinting()
                .setDateFormat("dd/MM/yyyy").create();

        collectionType = new TypeToken<Cliente>() {}.getType();*/

        dialog = new ProgressDialog(context);

        dialog.setTitle(context.getString(R.string.processing));

        dialog.setIndeterminate(true);

        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        dialog.setMessage("Iniciando a Tarefa de Login");

        dialog.show();


    }

    @Override
    protected Customer doInBackground(String ... params) {

        String response = "";

        try {

            publishProgress("Enviando Objeto para o Servidor");

            //Todo FIXME Send a Json to Login

            response = HTTP.sendPost(url, params[0].toString());

            publishProgress("Objeto recebido");

            if ((response == null) || (response.equals(""))){

                publishProgress("Servidor Respondeu Null");


            }else if (response.equals("{}")){

                Log.d("DEBUG","ID Existente no Firebase");

                Log.d("DEBUG","Cliente Vazio");

                FirebaseAuth.getInstance().getCurrentUser().delete().addOnCompleteListener(new OnCompleteListener<Void>() {

                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if(!(task.isSuccessful())){

                            Toast.makeText(context, context.getString(R.string.delete_account_failed), Toast.LENGTH_LONG).show();

                            Toast.makeText(context, context.getString(R.string.could_not_authorize), Toast.LENGTH_LONG).show();

                        }else{

                            Toast.makeText(context, context.getString(R.string.delete_account_success), Toast.LENGTH_LONG).show();

                            Toast.makeText(context, context.getString(R.string.repeat_register_operation), Toast.LENGTH_LONG).show();
                        }

                    }
                });


            }else{

                publishProgress("Criando Objeto Cliente");

                //TODO FIXME Receibe a json

                //customer = in.fromJson(response, collectionType);
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
    protected void onProgressUpdate(String ... values) {

        super.onProgressUpdate(values);

        dialog.setMessage(String.valueOf(values[0]));
    }


    @Override
    protected void onPostExecute(Customer result) {

        dialog.setMessage(context.getString(R.string.process_finish));

        dialog.dismiss();

        delegate.processFinish(result);

    }



}
