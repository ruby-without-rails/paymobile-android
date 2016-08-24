/**
 *
 * @author Felipe Rodrigues Michetti
 * @see http://portfolio-frmichetti.rhcloud.com
 * @see http://www.codecode.com.br
 * @see mailto:frmichetti@gmail.com
 * */
package br.com.frmichetti.carhollics.android.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import br.com.frmichetti.carhollics.android.util.ConnectivityReceiver;

public abstract class BaseFragment extends Fragment implements MyPattern,
        ConnectivityReceiver.ConnectivityReceiverListener {

    private Context context;

    private Intent intent;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return super.onCreateView(inflater, container, savedInstanceState);

    }

    @Override
    public void doCastComponents() {

    }

    @Override
    public void doConfigure() {

    }

    @Override
    public void doCreateListeners() {

    }
}
