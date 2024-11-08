package  com.example.marcosgb_historial_mdico_electrnico_personal.util;

import android.content.Context;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class AlmacenamientoExterno {

    public static void guardarArchivo(Context context, String nombreArchivo, String contenido) {
        File archivo = new File(context.getExternalFilesDir(null), nombreArchivo);
        try (FileOutputStream fos = new FileOutputStream(archivo)) {
            fos.write(contenido.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String leerArchivo(Context context, String nombreArchivo) {
        File archivo = new File(context.getExternalFilesDir(null), nombreArchivo);
        StringBuilder contenido = new StringBuilder();
        try (FileInputStream fis = new FileInputStream(archivo)) {
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