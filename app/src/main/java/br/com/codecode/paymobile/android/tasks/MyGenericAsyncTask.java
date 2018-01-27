package br.com.codecode.paymobile.android.tasks;

import android.os.AsyncTask;


/**
 * Created by felipe on 05/01/18.
 */

public abstract class MyGenericAsyncTask<ReturnType, ParamType> extends AsyncTask<ParamType, String, ReturnType> {
    private MyGenericCallBack<ReturnType> myGenericCallBack;

    private MyGenericAsyncTask() {}

    public MyGenericAsyncTask(MyGenericCallBack<ReturnType> myGenericCallBack) {
        this();
        this.myGenericCallBack = myGenericCallBack;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        myGenericCallBack.onStart();
    }

    @Override
    protected void onPostExecute(ReturnType result) {
        super.onPostExecute(result);
        publishProgress("Data Received...");
        myGenericCallBack.onResult(result);
    }

    @Override
    protected void onProgressUpdate(String... values) {
        super.onProgressUpdate(values);
        myGenericCallBack.onProgressUpdate(values);
    }
}
