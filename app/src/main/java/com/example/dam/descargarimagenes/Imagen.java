package com.example.dam.descargarimagenes;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class Imagen extends AppCompatActivity {

    private android.widget.TextView tvCarga2;
    private android.widget.ProgressBar progressBar2;
    private android.widget.ImageView ivImagen;
    private int progress;
    private String url;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imagen);
        this.ivImagen = (ImageView) findViewById(R.id.ivImagen);
        this.progressBar2 = (ProgressBar) findViewById(R.id.progressBar2);
        this.tvCarga2 = (TextView) findViewById(R.id.tvCarga2);

        Intent intent = getIntent();
        url = intent.getStringExtra("url");
        url = "http:" + url.substring(url.indexOf("/"), url.lastIndexOf("'"));

        TareaImagen t = new TareaImagen();
        t.execute(url);
    }

    public class TareaImagen extends AsyncTask<String, Integer, Boolean> {

        @Override
        protected Boolean doInBackground(String... params) {
            try{
                URL url = new URL(params[0]);
                InputStream in = url.openStream();
                OutputStream out = new FileOutputStream(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/foto.jpg");
                byte[] buffer = new byte[2048];
                int longitud;
                while ((longitud = in.read(buffer)) != -1){
                    out.write(buffer, 0, longitud);
                }
                in.close();
                out.close();

                for(progress = 0; progress < 100; progress++){
                    Thread.sleep(10);
                    publishProgress(progress);
                }
                return true;
            }catch(MalformedURLException ex){
                Log.v("ERROR: ", ex.toString());
                return false;
            }catch(IOException e){
                Log.v("ERROR: ", e.toString());
                return false;
            } catch (InterruptedException e) {
                Log.v("ERROR: ", e.toString());
                return false;
            }
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            progressBar2.setProgress(progress);
        }
        @Override
        protected void onPreExecute() {
            progressBar2.setProgress(0);
            progressBar2.setMax(100);
            tvCarga2.setText(R.string.downImage);
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            tvCarga2.setText(R.string.processOff);

            tvCarga2.setVisibility(View.GONE);
            progressBar2.setVisibility(View.GONE);
            ivImagen.setVisibility(View.VISIBLE);


            File f = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/foto.jpg");
            Uri uri = Uri.fromFile(f);
            ivImagen.setImageURI(uri);
        }
    }
}
