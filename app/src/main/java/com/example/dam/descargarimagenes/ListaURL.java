package com.example.dam.descargarimagenes;

import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class ListaURL extends AppCompatActivity {

    private ArrayList<String> lista;
    private Adaptador adaptador;
    private ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_url);

        Intent intent = getIntent();
        lista = intent.getStringArrayListExtra("lista");

        adaptador = new Adaptador(this, lista);

        lv = (ListView)findViewById(R.id.lvURLs);
        lv.setAdapter(adaptador);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String url = lista.get(position);
                Intent i = new Intent(getApplicationContext(), Imagen.class);
                Bundle b = new Bundle();
                b.putString("url", url);
                i.putExtras(b);
                startActivity(i);
            }
        });
    }

}
