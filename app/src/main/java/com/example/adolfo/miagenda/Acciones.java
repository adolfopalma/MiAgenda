package com.example.adolfo.miagenda;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

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

    private static final int CAMARA = 1;
    private static final int GALERIA = 2;

    public void foto(View v) {
        i = new Intent("android.media.action.IMAGE_CAPTURE");
        startActivityForResult(i, CAMARA);
    }

    public void galeria(View v) {
        i = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i, GALERIA);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMARA && resultCode == RESULT_OK && data != null) {
            Bitmap imagen = (Bitmap) data.getExtras().get("data");
            ImageView v = (ImageView)findViewById(R.id.imagenProv);
            v.setImageBitmap(imagen);
        }

    }

}
