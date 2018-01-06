package br.com.frmichetti.paymobile.android.tasks;

public interface MyGenericCallBack<T> {

    void onStart();

    void onProgressUpdate(String... values);

    void onResult(T result);

}
