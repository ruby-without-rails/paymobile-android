package com.frmichetti.carhollicsandroid;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

public class WhereActivity extends AppCompatActivity implements MyPattern {

    private Context context;

    private Intent intent;

    private ActionBar actionBar;

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

        context = this;

        actionBar = getSupportActionBar();

        actionBar.setDisplayHomeAsUpEnabled(true);

        actionBar.setTitle(R.string.app_name);

        actionBar.setSubtitle("Where Activity");

    }

    @Override
    public void getExtras(Intent intent) {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if(id == android.R.id.home){
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
