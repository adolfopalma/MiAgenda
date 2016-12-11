package com.example.adolfo.miagenda;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.File;

public class Main2Activity extends AppCompatActivity {
    EditText nombre;
    EditText foto;
    EditText telefono;
    EditText direccion;
    EditText email;
    EditText web;
    ImageView v;
    Intent i;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);

        Bundle extras = getIntent().getExtras();
        v = (ImageView) findViewById(R.id.ivModificar);

        nombre = (EditText) findViewById(R.id.tfNombreNuevo);
        telefono = (EditText) findViewById(R.id.tfTelefonoNuevo);
        foto = (EditText) findViewById(R.id.tfFotoNuevo);
        direccion = (EditText) findViewById(R.id.tfDireccionNuevo);
        email = (EditText) findViewById(R.id.tfEmailNuevo);
        web = (EditText) findViewById(R.id.tfWebNuevo);

        nombre.setText(extras.getString("nombre"));
        telefono.setText(extras.getString("telefono"));
        foto.setText(extras.getString("foto"));
        direccion.setText(extras.getString("direccion"));
        email.setText(extras.getString("email"));
        web.setText(extras.getString("web"));


        File img = new File(Environment.getExternalStorageDirectory() + extras.getString("foto"));

        if (img.exists()) {
            try {
                v.setImageBitmap(BitmapFactory.decodeFile(img.toString()));
                v.setAdjustViewBounds(true);
            } catch (Exception E) {
            }

        } else {
            v.setImageResource(R.drawable.silueta);
            //Toast.makeText(actividad, "No hay imagen para cargar", Toast.LENGTH_SHORT).show();
        }


    }


    public void irAcciones(View v) {
        Intent i = new Intent(getApplicationContext(), Acciones.class);
        i.putExtra("telefono",telefono.getText().toString());
        i.putExtra("email",email.getText().toString());
        i.putExtra("web",web.getText().toString());
        startActivity(i);
    }


}
