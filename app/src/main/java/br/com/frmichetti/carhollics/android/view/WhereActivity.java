package br.com.frmichetti.carhollics.android.view;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import br.com.frmichetti.carhollics.android.R;

public class WhereActivity extends BaseActivity{


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_where);

        doConfigure();

    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {

        super.onPostCreate(savedInstanceState);

        Toast.makeText(context,"Load Map from webservice",Toast.LENGTH_LONG).show();

        Log.i("INFO","Load Map from webservice");
    }

    @Override
    public void doCastComponents() {


    }

    @Override
    public void doCreateListeners() {

    }

    @Override
    public void doConfigure() {

        super.doConfigure();

        actionBar.setSubtitle("Where Activity");

    }

    @Override
    public void getExtras(Intent intent) {

    }


}
