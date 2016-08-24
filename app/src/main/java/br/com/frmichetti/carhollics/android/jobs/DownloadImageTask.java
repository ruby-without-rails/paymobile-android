/**
 * @author Felipe Rodrigues Michetti
 * @see http://portfolio-frmichetti.rhcloud.com
 * @see http://www.codecode.com.br
 * @see mailto:frmichetti@gmail.com
 */
package br.com.frmichetti.carhollics.android.jobs;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.InputStream;
import java.net.URL;


public class DownloadImageTask extends AsyncTask<URL,Void,Bitmap> {

    private ImageView imageView;

    public DownloadImageTask(ImageView imageView){

        this.imageView = imageView;

    }


    @Override
    protected Bitmap doInBackground(URL ... urls){

        URL urlOfImage = urls[0];

        Bitmap logo = null;

        try{

            InputStream is = new URL(urlOfImage.toString()).openStream();

            logo = BitmapFactory.decodeStream(is);

        }catch(Exception e){

            e.printStackTrace();
        }
        return logo;
    }

    @Override
    protected void onPostExecute(Bitmap result){

        imageView.setImageBitmap(result);

    }
}

