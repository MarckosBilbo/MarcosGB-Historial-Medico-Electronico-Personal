package com.example.marcosgb_historial_mdico_electrnico_personal.util;

import android.content.Context;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class AlmacenamientoInterno {

    public static void guardarArchivo(Context context, String nombreArchivo, String contenido) {
        try (FileOutputStream fos = context.openFileOutput(nombreArchivo, Context.MODE_PRIVATE)) {
            fos.write(contenido.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String leerArchivo(Context context, String nombreArchivo) {
        StringBuilder contenido = new StringBuilder();
        try (FileInputStream fis = context.openFileInput(nombreArchivo)) {
            int ch;
            while ((ch = fis.read()) != -1) {
                contenido.append((char) ch);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return contenido.toString();
    }
}