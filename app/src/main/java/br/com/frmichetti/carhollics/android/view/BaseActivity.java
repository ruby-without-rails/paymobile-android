package br.com.frmichetti.carhollics.android.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import br.com.frmichetti.carhollics.android.R;

/**
 * Created by Felipe on 08/07/2016.
 */
public abstract class BaseActivity extends AppCompatActivity implements MyPattern {

    protected ActionBar actionBar;

    protected Context context;

    protected Intent intent;

    private FirebaseAuth.AuthStateListener authListener;

    protected FirebaseAuth auth;

    protected FirebaseUser user;

    protected BaseActivity(){
        Log.i("[INFO-INSTANCE]","Create instance of " + this.getClass().getSimpleName());
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        auth = FirebaseAuth.getInstance();

        user = auth.getCurrentUser();

        doCreateFirebaseListener();

    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {

        super.onPostCreate(savedInstanceState);

        doConfigure();

    }

    @Override
    protected void onStart() {

        super.onStart();

        auth.addAuthStateListener(authListener);

        Log.d("[DEBUG-AUTH-LISTENER]","Registered Authentication Listener");

    }


    @Override
    protected void onStop() {

        super.onStop();

        if (authListener != null) {

            auth.removeAuthStateListener(authListener);

            Log.d("[DEBUG-AUTH-LISTENER]","Removed Authentication Listener");

        }

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {

        super.onSaveInstanceState(outState);

        Log.i("[INFO-SAVE-BUNDLE]","Save State");

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if(id == android.R.id.home){


            if(getClass().getSimpleName().equals("SimpleMainActivity")){

                signOut();
                finish();
            }

            finish();

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {

        Log.i("[INFO-KEY-UP]","KeyUp " + String.valueOf(event.getKeyCode()));

        if(event.getKeyCode() == KeyEvent.KEYCODE_BACK){

            Log.i("Info","KeyUp Back Button");

            if(getClass().getSimpleName().equals("SimpleMainActivity")){

                signOut();
                finish();
            }

            finish();

        }

        if(event.getKeyCode() == KeyEvent.KEYCODE_HOME){

            Log.i("Info","KeyUp Home Button");
        }

        if(event.getKeyCode() == KeyEvent.KEYCODE_SEARCH){

            Log.i("Info","KeyUp Search Button");
        }

        return true;
    }

    private void doCreateFirebaseListener() {

        authListener = new FirebaseAuth.AuthStateListener() {

            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                FirebaseUser user = firebaseAuth.getCurrentUser();

                if (user == null) {

                    startActivity(new Intent(context, LoginActivity.class));

                    finish();

                    Log.d("[DEBUG-INFO]","User Null, Send to LoginActivity");

                }
            }
        };
    }

    @Override
    public void doConfigure() {

        context = this;

        intent = getIntent();

        actionBar = getSupportActionBar();

        actionBar.setTitle(R.string.app_name);

        actionBar.setDisplayHomeAsUpEnabled(true);

/*
   Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

   toolbar.setTitle(getString(R.string.app_name));

   setSupportActionBar(toolbar);

*/
    }

    public void signOut() {

        auth.signOut();

        Log.i("[INFO-SIGNOUT]","SignOut");

    }


}
