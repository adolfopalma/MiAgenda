package com.example.adolfo.miagenda;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class Nuevo_Contacto extends AppCompatActivity {
    private SqliteControl admin;
    SQLiteDatabase bd;
    EditText nombre;
    EditText telefono;
    EditText direccion;
    EditText email;
    EditText web;
    EditText foto;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo__contacto);

        nombre = (EditText)findViewById(R.id.tfNombreNuevo);
        telefono = (EditText)findViewById(R.id.tfTelefonoNuevo);
        direccion = (EditText)findViewById(R.id.tfDireccionNuevo);
        email = (EditText)findViewById(R.id.tfEmailNuevo);
        web = (EditText)findViewById(R.id.tfWebNuevo);
        foto = (EditText)findViewById(R.id.tfFotoNuevo);



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.bAlta);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alta(view);
            }
        });
    }

    public long alta(View view){
        long resultado = -1;
        admin = new SqliteControl(this);
        bd = admin.getWritableDatabase();
        ContentValues valor = new ContentValues();
        bd = admin.getReadableDatabase();

        String[]campos = {"idcontacto","nombre","direccion","email","webblog"};
        Cursor cursor = bd.query("contactos",campos,null,null,null,null,null,null);
        int id;

        if(cursor.moveToLast()){
            id=cursor.getInt(0)+1;
        }else{
            id=1;
        }
                valor.put("idcontacto",id);
                valor.put("nombre", nombre.getText().toString());
                valor.put("direccion", direccion.getText().toString());
                valor.put("email", email.getText().toString());
                valor.put("Webblog", web.getText().toString());
                bd.insert("contactos", null, valor);

                valor.clear();


                valor.put("telefono", telefono.getText().toString());
                valor.put("idcontacto",id);
                bd.insert("telefonos", null, valor);

                valor.clear();


                valor.put("nomFichero", foto.getText().toString());
                valor.put("idcontacto",id);
                bd.insert("fotos", null, valor);




        nombre.setText("");
        direccion.setText("");
        email.setText("");
        web.setText("");
        telefono.setText("");
        foto.setText("");

        Toast toast1 = Toast.makeText(getApplicationContext(),"Contacto añadido correctamente", Toast.LENGTH_SHORT);
        toast1.show();


        admin.close();
        bd.close();
        return resultado;
    }


}
