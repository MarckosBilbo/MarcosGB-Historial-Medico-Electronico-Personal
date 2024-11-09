package com.example.marcosgb_historial_mdico_electrnico_personal.util;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreference {
    private static final String NOMBRE_PREF = "preferencias_app";
    private static final String CLAVE_COLOR_FONDO = "color_fondo";

    public static void guardarColorFondo(Context context, int color) {
        SharedPreferences preferencias = context.getSharedPreferences(NOMBRE_PREF, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferencias.edit();
        editor.putInt(CLAVE_COLOR_FONDO, color);
        editor.apply();
    }

    public static int obtenerColorFondo(Context context) {
        SharedPreferences preferencias = context.getSharedPreferences(NOMBRE_PREF, Context.MODE_PRIVATE);
        return preferencias.getInt(CLAVE_COLOR_FONDO, 0xFF808080); // Color predeterminado: Gris
    }
}