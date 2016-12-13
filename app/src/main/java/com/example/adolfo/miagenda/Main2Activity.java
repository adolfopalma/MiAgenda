package com.example.adolfo.miagenda;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class Main2Activity extends AppCompatActivity {
    EditText nombre;
    EditText foto;
    EditText telefono;
    EditText direccion;
    EditText email;
    EditText web;
    ImageView v;
    Intent i;
    private SqliteControl admin;
    SQLiteDatabase bd;
    Bundle extras;
    private static final int CAMARA = 1;
    private static final int GALERIA = 2;
    private static Uri fotoGaleria;
    private static InputStream is;
    private static BufferedInputStream bis;
    private static Bitmap bm;
    private static OutputStream os;
    private static File path;
    private static File fich_salida;
    Calendar calendario = new GregorianCalendar();
    int hora, minutos, segundos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);
        extras = getIntent().getExtras();

        v = (ImageView) findViewById(R.id.ivNuevo);

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


        File img = new File(extras.getString("foto"));

        if (img.exists()) {
            try {
                v.setImageBitmap(BitmapFactory.decodeFile(img.toString()));
                v.setAdjustViewBounds(true);
            } catch (Exception E) {
            }

        } else {
            v.setImageResource(R.drawable.silueta);

        }


    }
    public void nuevaFoto(View v){
        if(MainActivity.prefe.getBoolean("prefeFoto",true)){
            i = new Intent("android.media.action.IMAGE_CAPTURE");
            startActivityForResult(i, CAMARA);
        }else{
            i = new Intent(Intent.ACTION_PICK,
            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(i, GALERIA);
        }
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMARA && resultCode == RESULT_OK && data != null) {
            Bitmap imagen = (Bitmap) data.getExtras().get("data");
            v.setImageBitmap(imagen);
            ConvertirFotoYGuardar();
        }
        if (requestCode == GALERIA && resultCode == RESULT_OK) {
            fotoGaleria = data.getData();
            try {
                is = getContentResolver().openInputStream(fotoGaleria);
                bis = new BufferedInputStream(is);
                bm = BitmapFactory.decodeStream(bis);
                v.setImageBitmap(bm);


            } catch (FileNotFoundException e) {}

            ConvertirFotoYGuardar();
        }
    }





    public void irAcciones(View v) {
        Intent i = new Intent(getApplicationContext(), Acciones.class);
        i.putExtra("telefono",telefono.getText().toString());
        i.putExtra("email",email.getText().toString());
        i.putExtra("web",web.getText().toString());
        startActivity(i);
    }

    public long modificarContacto(View view){
        long resultado = -1;
        admin = new SqliteControl(this);
        bd = admin.getWritableDatabase();
        int idContacto = extras.getInt("id");


        ContentValues valores = new ContentValues();
        valores.put("nombre", nombre.getText().toString());
        valores.put("direccion", direccion.getText().toString());
        valores.put("email", email.getText().toString());
        valores.put("Webblog", web.getText().toString());
        bd.update("contactos", valores, "idcontacto = "+idContacto, null);

        valores.clear();


        valores.put("telefono", telefono.getText().toString());
        bd.update("telefonos", valores, "idcontacto = "+idContacto, null);

        valores.clear();


        valores.put("nomFichero", foto.getText().toString());
        bd.update("fotos", valores, "idcontacto = "+idContacto, null);


        Toast toast1 = Toast.makeText(getApplicationContext(),getString(R.string.contactoModificado), Toast.LENGTH_SHORT);
        toast1.show();


        admin.close();
        bd.close();
        return resultado;
    }

    public long borrarContacto(View v){
        long resultado = -1;
        admin = new SqliteControl(this);
        bd = admin.getWritableDatabase();


        bd.delete("contactos", "idcontacto = "+extras.getInt("id"), null);

        bd.delete("telefonos", "idContacto = "+extras.getInt("id"), null);

        bd.delete("fotos",  "idContacto = "+extras.getInt("id"), null);


        Toast toast1 = Toast.makeText(getApplicationContext(),getString(R.string.contactoBorrado), Toast.LENGTH_SHORT);
        toast1.show();


        admin.close();
        bd.close();
        return resultado;


    }

    public void ConvertirFotoYGuardar(){
        if(v.getDrawable() == null) {
            Toast.makeText(getApplicationContext(),"No hemos seleccionado imagen",
                    Toast.LENGTH_LONG).show();
        } else {
            hora =calendario.get(Calendar.HOUR_OF_DAY);
            minutos = calendario.get(Calendar.MINUTE);
            segundos = calendario.get(Calendar.SECOND);
            path = getExternalFilesDir(null);
            fich_salida= new File(path,hora +""+ minutos+"" + segundos+".jpg");
            try {
                os = new FileOutputStream(fich_salida);
            } catch (FileNotFoundException e) {}
            v.setDrawingCacheEnabled(true);
            bm = v.getDrawingCache();
            try {
                bm.compress(Bitmap.CompressFormat.PNG, 100, os);
                os.flush();
                os.close();
            } catch (IOException e) {}
        }

        foto.setText(fich_salida.toString());


        admin = new SqliteControl(this);
        bd = admin.getWritableDatabase();
        int idContacto = extras.getInt("id");


        ContentValues valores = new ContentValues();
        valores.put("nomFichero", foto.getText().toString());
        valores.put("idContacto", idContacto);
        bd.insert("Fotos",null,valores);

        valores.clear();
        Toast toast1 = Toast.makeText(getApplicationContext(),getString(R.string.fotoGuardada), Toast.LENGTH_SHORT);
        toast1.show();
    }

    public void masTelefonos(View v){
        admin = new SqliteControl(this);
        bd = admin.getWritableDatabase();
        int idContacto = extras.getInt("id");


        ContentValues valores = new ContentValues();
        valores.put("telefono", telefono.getText().toString());
        valores.put("idContacto", idContacto);
        bd.insert("telefonos",null,valores);

        valores.clear();
        Toast toast1 = Toast.makeText(getApplicationContext(),getString(R.string.telefonoGuardado), Toast.LENGTH_SHORT);
        toast1.show();

    }


}
