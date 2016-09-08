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
import br.com.frmichetti.carhollics.android.model.compatibility.Vehicle;

public class TaskCreateVehicle extends AsyncTask<Vehicle,String,Vehicle> {

    public AsyncResponse delegate = null;

    private String url ;

    private ProgressDialog dialog;

    private Context context;


    private TaskCreateVehicle(){

        Log.d("DEBUG-TASK","create TaskCreateUser");
    }

    private TaskCreateVehicle(Context context) {
        this();
        this.context = context;
    }

    public TaskCreateVehicle(Context context, AsyncResponse<Vehicle> delegate){
        this(context);
        this.delegate = delegate;
    }

    @Override
    protected void onPreExecute() {

        super.onPreExecute();

        url = context.getResources().getString(R.string.remote_server) + "/services/vehicle/save";

        Log.d("DEBUG-TASK","server config -> " + url);

        dialog = new ProgressDialog(context);

        dialog.setTitle("Processando");

        dialog.setIndeterminate(true);

        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        dialog.setMessage("Iniciando a Tarefa Criar Novo Veículo");

        dialog.show();

    }

    @Override
    protected Vehicle doInBackground(Vehicle ... params) {

        try {

            publishProgress("Enviando Requisição para o Servidor");

            String response = "";

            //TODO FIXME Create a JSON

            response = HTTP.sendPost(url,params[0].toString());

        } catch (IOException e) {

            publishProgress("Falha ao Obter Resposta");

            Log.e("Erro", e.getMessage());
        }

        publishProgress("Item recebido !");

        //TODO FIXME Make a JSOn

        Vehicle v = null;

        return v;

    }

    @Override
    protected void onProgressUpdate(String ... values) {

        super.onProgressUpdate(values);

        dialog.setMessage(String.valueOf(values[0]));
    }


    @Override
    protected void onPostExecute(Vehicle result) {

        dialog.setMessage("Tarefa Finalizada!");

        dialog.dismiss();

        delegate.processFinish(result);

    }
}
