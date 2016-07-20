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


public abstract class AbstractTask<T> extends AsyncTask<T, Object , T>{

    private AsyncResponse delegate = null;

    private Context context;

    private ProgressDialog dialog;

    @Override
    protected T doInBackground(T ... input) {

        publishProgress("...");

        return null;
    }

    @Override
    protected void onPreExecute() {

        super.onPreExecute();

        dialog = new ProgressDialog(context);

        dialog.setTitle("Processando");

        dialog.setIndeterminate(true);

        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        dialog.show();

    }

    @Override
    protected void onPostExecute(T output) {

        super.onPostExecute(output);

        delegate.processFinish(output);
    }

    @Override
    protected void onProgressUpdate(Object ... values) {

        super.onProgressUpdate(values);



    }

    private AbstractTask(){

        Log.d("DEBUG-TASK","create AbstractTask");
    }

    private AbstractTask(Context context) {

        this();

        this.context = context;

    }

    public AbstractTask(Context context, AsyncResponse<T> delegate){

        this(context);

        this.delegate = delegate;

    }
}
