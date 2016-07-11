package br.com.frmichetti.carhollics.android.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Felipe on 10/07/2016.
 */
public abstract class BaseFragment extends Fragment implements MyPattern {

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
