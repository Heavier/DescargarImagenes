package com.example.dam.descargarimagenes;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private Adaptador adaptador;
    private ArrayList<String> lista;
    private ListView lv;
    private android.widget.ProgressBar progressBar;
    private int progress;
    private android.widget.TextView textView;
    private android.widget.ImageButton ibBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.ibBack = (ImageButton) findViewById(R.id.ibBack);
        this.textView = (TextView) findViewById(R.id.tvCarga);
        this.progressBar = (ProgressBar) findViewById(R.id.progressBar);

        Tarea tarea = new Tarea();
        tarea.execute();
    }

    public void back(View view) {
        Intent i = new Intent(getApplicationContext(), ListaURL.class);
        Bundle b = new Bundle();
        b.putStringArrayList("lista", lista);
        i.putExtras(b);
        startActivity(i);
    }

    public class Tarea  extends AsyncTask<String, Integer, Boolean> {

        ArrayList<String> temp = new ArrayList<>();

        @Override
        protected Boolean doInBackground(String... params) {

            try {
                String url = "https://www.flickr.com/explore";

                URL uget = new URL(url);
                BufferedReader in = new BufferedReader(new InputStreamReader(uget.openStream()));
                String linea, out = "";
                while((linea = in.readLine()) != null){
                    if (getEtiqueta(linea).equals("img.src")){
                        if (linea.replace("\t", "").equals("img.src = imageUrl;")){
                            // No hacer nada
                        }else{
                            temp.add(linea.replace("\t", "") + "\n");
                        }
                    }
                }
                Log.v("ACCION: ", "Web descargada");
                in.close();


                for(progress = 0; progress < 100; progress++){
                    Thread.sleep(50);
                    publishProgress(progress);
                }
                return true;
            } catch (IOException ex) {
                return false;
            } catch (InterruptedException e) {
                e.printStackTrace();
                return false;
            }
        }

        private String getEtiqueta(String text) {
            if (text.indexOf('=') > -1) {
                return text.substring(0, text.indexOf('=')).trim();
            } else {
                return text;
            }
        }


        @Override
        protected void onProgressUpdate(Integer... values) {
            progressBar.setProgress(progress);
        }
        @Override
        protected void onPreExecute() {
            progressBar.setProgress(0);
            progressBar.setMax(100);
            textView.setText(R.string.downTags);
        }
        @Override
        protected void onPostExecute(Boolean result) {
            textView.setText(R.string.processOff);
            ibBack.setVisibility(View.VISIBLE);
            lista = temp;
            Intent i = new Intent(getApplicationContext(), ListaURL.class);
            Bundle b = new Bundle();
            b.putStringArrayList("lista", temp);
            i.putExtras(b);
            startActivity(i);
        }
        @Override
        protected void onCancelled() {
        }
    }
}
