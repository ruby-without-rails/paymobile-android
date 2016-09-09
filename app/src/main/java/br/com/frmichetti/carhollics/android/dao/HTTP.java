/**
 *
 * @author Felipe Rodrigues Michetti
 * @see http://portfolio-frmichetti.rhcloud.com
 * @see http://www.codecode.com.br
 * @see mailto:frmichetti@gmail.com
 * */
package br.com.frmichetti.carhollics.android.dao;

import android.util.Log;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public final class HTTP {

    private static final String USER_AGENT = "Mozilla/5.0";

    private static final Integer TIME_OUT = 1000;

    // HTTP GET request
    public static String sendGet(String url) throws IOException {

        URL obj = new URL(url);

        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        //add request header
        con.setRequestMethod("GET");

        con.setRequestProperty("Accept-Language", "UTF-8");

        con.setRequestProperty("Content-Type", "application/json");

        con.setRequestProperty("User-Agent", USER_AGENT);

        int responseCode = con.getResponseCode();

        Log.i("Resposta","Enviando requisicao 'GET' para a URL : " + url);

        Log.i("Resposta","Codigo de Resposta : " + responseCode);

        String resp;

        if(responseCode == 200 || responseCode == 201){

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));

            StringBuffer response = new StringBuffer();

            String inputLine;

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }

            Log.i("Resposta","Resposta do Servidor");

            Log.i("Resposta",response.toString());

            resp = response.toString();

        }else{
            resp = null;
        }

        return resp;
    }

    // HTTP POST request
    public static String sendPost(String url, String params) throws IOException {

        URL obj = new URL(url);

        params = new String(params.getBytes(), "ISO_8859_1");

        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        //add request header
        con.setRequestMethod("POST");

        con.setRequestProperty("User-Agent", USER_AGENT);

        con.setRequestProperty("Accept-Language", "UTF-8");

        con.setRequestProperty("Content-Type", "application/json");

        // Send post request
        con.setDoOutput(true);

       DataOutputStream wr = new DataOutputStream(con.getOutputStream());

            wr.writeBytes(params);

            wr.flush();


        int responseCode = con.getResponseCode();

        Log.i("Resposta","Enviando Requisição 'POST' para URL : " + url);

        Log.i("Resposta","Parametros do Post : " + params);

        Log.i("Resposta","Codigo da Resposta : " + responseCode);

        String resp;

        if(responseCode == 200 || responseCode == 201){

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));

            StringBuffer response = new StringBuffer();

            String inputLine;

            while ((inputLine = in.readLine()) != null) {

                response.append(inputLine);

            }

            Log.i("Resposta","Resposta do Servidor");

            Log.i("Resposta",response.toString());

            resp = response.toString();

        }else{

            resp = null;
        }

        return resp;

    }

}
