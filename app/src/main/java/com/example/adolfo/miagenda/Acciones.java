package com.example.adolfo.miagenda;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.preference.SwitchPreference;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;

public class Acciones extends AppCompatActivity {
    Intent i;
    Bundle extras;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acciones);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        extras = getIntent().getExtras();


    }

    public void Llamada(View v) {
        i = new Intent(Intent.ACTION_DIAL);
        i.setData(Uri.parse("tel:"+extras.getString("telefono")));
        startActivity(i);
    }

    public void mandarEmail(View v) {
        i = new Intent(Intent.ACTION_SEND);
        i.setType("text/plain");
        i.putExtra(Intent.EXTRA_SUBJECT, "Asunto del Correo");
        i.putExtra(Intent.EXTRA_TEXT, "Hola que hases guapi?");
        i.putExtra(Intent.EXTRA_EMAIL, new String[]{extras.getString("email")});
        startActivity(i);
    }

    public void irWeb(View v) {
        try{
            i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse("http://"+extras.getString("web")));
            startActivity(i);
        }catch(Exception e){
            Toast t =  Toast.makeText(getApplicationContext(),"La web no existe o no se encuentra", Toast.LENGTH_SHORT);
        }

    }




}
