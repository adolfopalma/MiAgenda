package com.example.adolfo.miagenda;

import android.app.Activity;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by adolf on 02/12/2016.
 */

public class Adaptador extends BaseAdapter {
    private ArrayList<Elemento> lista;
    private final Activity actividad;


    public Adaptador(Activity a, ArrayList<Elemento> v) {
        super();
        this.lista = v;
        this.actividad = a;
    }

    // En el constructor de la clase se indica la actividad donde se ejecutará
    // la lista de datos a visualizar.
    @Override
    public int getCount() {
        return lista.size();
    }

    @Override
    public Object getItem(int arg0) {
        return lista.get(arg0);
    }

    @Override
    public long getItemId(int arg0) {
        return lista.get(arg0).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater ly = actividad.getLayoutInflater();
        View view = ly.inflate(R.layout.item, null, true);

        TextView tvTitulo= (TextView) view.findViewById(R.id.tvTitulo);
        tvTitulo.setText((CharSequence) lista.get(position).getNombre());

        TextView tvTelefono= (TextView) view.findViewById(R.id.tvTelefono);
        tvTelefono.setText((CharSequence) lista.get(position).getTelefono());

        TextView tvEmail= (TextView) view.findViewById(R.id.tvEmail);
        tvEmail.setText((CharSequence) lista.get(position).getEmail());

        TextView tvDireccion= (TextView) view.findViewById(R.id.tvDireccion);
        tvDireccion.setText((CharSequence) lista.get(position).getDireccion());

        TextView tvWeb= (TextView) view.findViewById(R.id.tvWeb);
        tvWeb.setText((CharSequence) lista.get(position).getWeb());


        ImageView im = (ImageView) view.findViewById(R.id.imagen);
        File img = new File(Environment.getExternalStorageDirectory() + lista.get(position).getFoto());

        if (img.exists()) {
            try{
                im.setImageBitmap(BitmapFactory.decodeFile(img.toString()));
                im.setAdjustViewBounds(true);
            }catch(Exception E){}

        } else {
            im.setImageResource(R.drawable.silueta);
            //Toast.makeText(actividad, "No hay imagen para cargar", Toast.LENGTH_SHORT).show();
        }

        return view;
    }

}
