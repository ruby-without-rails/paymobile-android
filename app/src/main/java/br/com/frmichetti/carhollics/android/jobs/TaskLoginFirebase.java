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
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;

import br.com.frmichetti.carhollics.android.R;
import br.com.frmichetti.carhollics.android.dao.HTTP;
import br.com.frmichetti.carhollics.android.model.Cliente;
import br.com.frmichetti.carhollics.android.model.Usuario;


public class TaskLoginFirebase extends AsyncTask<String,String, Cliente> {

    public AsyncResponse delegate = null;

    private ProgressDialog dialog;

    private String url ;

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

        Log.d("DEBUG-TASK","create TaskLoginFirebase");

    }


    @Override
    protected void onPreExecute() {

        super.onPreExecute();

        url = context.getResources().getString(R.string.local_server) + "/services/usuario/firebaselogin";

        Log.d("DEBUG-TASK","server config -> " + url);

        out = new Gson();

        in = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .setPrettyPrinting()
                .setDateFormat("dd/MM/yyyy").create();

        collectionType = new TypeToken<Cliente>() {}.getType();

        dialog = new ProgressDialog(context);

        dialog.setTitle(context.getString(R.string.processing));

        dialog.setIndeterminate(true);

        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        dialog.setMessage("Iniciando a Tarefa de Login");

        dialog.show();


    }

    @Override
    protected Cliente doInBackground(String ... params) {

        try {

            publishProgress("Enviando Objeto para o Servidor");

            json = HTTP.sendPost(url, out.toJson(params[0]));

            publishProgress("Objeto recebido");

            if ((json == null) || (json.equals(""))){

                publishProgress("Servidor Respondeu Null");


            }else if (json.equals("{}")){

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
    protected void onProgressUpdate(String ... values) {

        super.onProgressUpdate(values);

        dialog.setMessage(String.valueOf(values[0]));
    }


    @Override
    protected void onPostExecute(Cliente result) {

        dialog.setMessage(context.getString(R.string.process_finish));

        dialog.dismiss();

        delegate.processFinish(result);

    }



}
