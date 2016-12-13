package com.example.adolfo.miagenda;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceActivity;

/**
 * Created by adolf on 11/12/2016.
 */

public class Preferencias extends PreferenceActivity{
  //  SharedPreferences prefe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferencias);
       // prefe = getSharedPreferences("com.example.adolfo.miagenda", MODE_PRIVATE);
    }
}
