package br.com.frmichetti.carhollics.android;

import org.junit.Test;

import java.io.IOException;

import br.com.frmichetti.carhollics.android.dao.HTTP;

import static org.junit.Assert.*;

/**
 * Created by Felipe on 04/07/2016.
 */
public class TesteHTTPSend {

    @Test
    public void respond(){

        String r = null;

        try {
             r = HTTP.sendPost("http://localhost:8080/carhollics-webservice/services/usuario/firebaselogin","Sg6wwENmztNGHcBRljYxlaPXHMt2");
        } catch (IOException e) {
            e.printStackTrace();
        }
        assertNotNull(r);
    }
}
