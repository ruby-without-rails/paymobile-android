/**
 *
 * @author Felipe Rodrigues Michetti
 * @see http://portfolio-frmichetti.rhcloud.com
 * @see mailto:frmichetti@gmail.com
 * */
package br.com.frmichetti.carhollics.android.jobs;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;

import br.com.frmichetti.carhollics.android.dao.HTTP;
import br.com.frmichetti.carhollics.json.model.Cliente;
import br.com.frmichetti.carhollics.json.model.Pessoa;
import br.com.frmichetti.carhollics.json.model.Usuario;


public class TaskAtualizarLogin extends AsyncTask<Pessoa,String, String> {

    private ProgressDialog dialog;
    private Context context;
    private Intent intent ;

    private java.lang.reflect.Type collectionType;
    private final String URL = "";
    private Gson out;

    private Usuario u;

    public TaskAtualizarLogin(Context context){
        this.context = context;
    }


    @Override
    protected void onPreExecute() {

        super.onPreExecute();

        u = new Usuario();

        out = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .setPrettyPrinting()
                .setDateFormat("dd/MM/yyyy").create();

        collectionType = new TypeToken<Cliente>() {}.getType();
        dialog = new ProgressDialog(context);
        dialog.setTitle("Processando");
        dialog.setIndeterminate(true);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setMessage("Iniciando a Tarefa de Atualização");
        dialog.show();
    }

    @Override
    protected String doInBackground(Pessoa... params) {
        Pessoa p = params[0];
        String rsp = null;

        u.setLogin(p.getUsuario().getLogin());
        //u.setSenha(ActUsuario.getSn());

        p.setUsuario(u);

        try {
            publishProgress("Enviando Objeto para o Servidor");
            rsp = HTTP.sendPost(URL, out.toJson(p));

        } catch (IOException e) {
            publishProgress("Falha ao Receber Objeto");
            Log.i("Erro", e.toString());

        }

        return rsp;
    }

    @Override
    protected void onProgressUpdate(String... values) {
        super.onProgressUpdate(values);
        dialog.setMessage(String.valueOf(values[0]));
    }

    @Override
    protected void onPostExecute(String resposta) {
        super.onPostExecute(resposta);

        intent = new Intent();

        if(resposta.equals("true")){
/*
            ActUsuario.getCliente().setUsuario(u);
            dialog.setMessage("Dados atualizados com sucesso !");
            Toast.makeText(context, "Dados atualizados com sucesso !", Toast.LENGTH_LONG).show();
            intent.setClass(context, ActPrincipal.class);
            intent.putExtra("Cliente", ActUsuario.getCliente());
            context.startActivity(intent);
            ((Activity) context).finish();
*/

        }else
        {
            dialog.setMessage("Erro ao processar a atualização dos dados !");
            Toast.makeText(context,"Erro ao processar a atualização dos dados !",Toast.LENGTH_LONG).show();
        }
        dialog.setMessage("Tarefa de Atualização Finalizada!");
        dialog.dismiss();
    }
}
