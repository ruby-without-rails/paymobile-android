/**
 * @author Felipe Rodrigues Michetti
 * @see http://portfolio-frmichetti.rhcloud.com
 * @see http://www.codecode.com.br
 * @see mailto:frmichetti@gmail.com
 */
package br.com.frmichetti.carhollics.android.view.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import br.com.frmichetti.carhollics.android.R;

public class WhereActivity extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_where);

        doCastComponents();

        doCreateListeners();

    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {

        super.onPostCreate(savedInstanceState);

        doConfigure();

        Toast.makeText(context, "Load Map from webservice", Toast.LENGTH_LONG).show();

        Log.i("INFO", "Load Map from webservice");
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

        if (actionBar != null) {
            actionBar.setSubtitle("Where Activity");
        }


    }


    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {

    }
}
