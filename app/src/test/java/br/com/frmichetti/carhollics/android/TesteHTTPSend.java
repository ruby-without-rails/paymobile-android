package br.com.frmichetti.carhollics.android;

import android.util.Log;

import org.junit.Test;

import java.io.IOException;

import br.com.frmichetti.carhollics.android.dao.HTTP;

import static org.junit.Assert.assertNotNull;

/**
 * Created by Felipe on 04/07/2016.
 */
public class TesteHTTPSend {

    @Test
    public void respond(){

        String r = null;

        try {

            r = HTTP.sendGet("http://callcenter-carhollics.rhcloud.com/services/teste");

            Log.d("TEST-CASE",r);

        } catch (IOException e) {
            Log.d("TEST-CASE",e.toString()) ;
        }

        assertNotNull(r);
    }
}
