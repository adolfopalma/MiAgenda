package com.example.adolfo.miagenda;

import android.os.Bundle;
import android.preference.PreferenceActivity;

/**
 * Created by adolf on 11/12/2016.
 */

public class Preferencias extends PreferenceActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferencias);
    }
}
