package br.com.frmichetti.carhollics.android.view;

import android.os.Bundle;

import br.com.frmichetti.carhollics.android.R;

public class WhenActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_when);

        doCastComponents();

        doCreateListeners();


    }

    @Override
    public void doCastComponents() {

    }

    @Override
    public void doCreateListeners() {

    }

}