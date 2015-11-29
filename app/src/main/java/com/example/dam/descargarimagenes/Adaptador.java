package com.example.dam.descargarimagenes;


import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Adaptador extends ArrayAdapter<String> {

    ArrayList<String> lista;

    public Adaptador(Context context, ArrayList<String> objects) {
        super(context, R.layout.item_url, objects);
        this.lista = objects;
    }

    static class ViewHolder {
        public TextView tvURL;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        String actual = lista.get(position);
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.item_url, parent, false);
            viewHolder.tvURL = (TextView) convertView.findViewById(R.id.tvURL);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.tvURL.setText(actual);

        return convertView;
    }
}