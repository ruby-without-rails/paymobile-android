package com.frmichetti.carhollicsandroid.jobs;

/**
 * Created by Felipe on 02/07/2016.
 */
public interface  AsyncResponse<T> {
    void processFinish(T output);
}
