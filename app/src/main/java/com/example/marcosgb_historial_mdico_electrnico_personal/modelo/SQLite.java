package com.example.marcosgb_historial_mdico_electrnico_personal.modelo;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.marcosgb_historial_mdico_electrnico_personal.util.Encriptacion;

import java.util.ArrayList;
import java.util.List;

public class SQLite extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "historial_medico.db";
    private static final int DATABASE_VERSION = 2;
    private static final String TABLE_HISTORIAL = "historial";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NOMBRE = "nombre";
    private static final String COLUMN_FECHA_NACIMIENTO = "fecha_nacimiento";
    private static final String COLUMN_EDAD = "edad";
    private static final String COLUMN_ESTADO_SALUD = "estado_salud";
    private static final String COLUMN_ESPECIFICACIONES = "especificaciones";
    private static final String COLUMN_USER_ID = "user_id";
    private static final String CLAVE_ENCRIPTACION = "tu-clave-de-encriptacion";

    public SQLite(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_HISTORIAL + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NOMBRE + " TEXT, " +
                COLUMN_FECHA_NACIMIENTO + " TEXT, " +
                COLUMN_EDAD + " TEXT, " +
                COLUMN_ESTADO_SALUD + " TEXT, " +
                COLUMN_ESPECIFICACIONES + " TEXT, " +
                COLUMN_USER_ID + " TEXT)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 2) {
            db.execSQL("ALTER TABLE " + TABLE_HISTORIAL + " ADD COLUMN " + COLUMN_EDAD + " TEXT");
            db.execSQL("ALTER TABLE " + TABLE_HISTORIAL + " ADD COLUMN " + COLUMN_ESTADO_SALUD + " TEXT");
            db.execSQL("ALTER TABLE " + TABLE_HISTORIAL + " ADD COLUMN " + COLUMN_ESPECIFICACIONES + " TEXT");
        }
    }

    public void insertarHistorial(HistorialMedico historial) {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            String encryptedNombre = Encriptacion.encriptar(historial.getNombre(), CLAVE_ENCRIPTACION);
            String encryptedFechaNacimiento = Encriptacion.encriptar(historial.getFechaNacimiento(), CLAVE_ENCRIPTACION);
            String encryptedEdad = Encriptacion.encriptar(historial.getEdad(), CLAVE_ENCRIPTACION);
            String encryptedEstadoSalud = Encriptacion.encriptar(historial.getEstadoSalud(), CLAVE_ENCRIPTACION);
            String encryptedEspecificaciones = Encriptacion.encriptar(historial.getEspecificaciones(), CLAVE_ENCRIPTACION);
            String encryptedUserId = Encriptacion.encriptar(historial.getUserId(), CLAVE_ENCRIPTACION);

            String insert = "INSERT INTO " + TABLE_HISTORIAL + " (" + COLUMN_NOMBRE + ", " + COLUMN_FECHA_NACIMIENTO + ", " + COLUMN_EDAD + ", " + COLUMN_ESTADO_SALUD + ", " + COLUMN_ESPECIFICACIONES + ", " + COLUMN_USER_ID + ") VALUES ('" +
                    encryptedNombre + "', '" + encryptedFechaNacimiento + "', '" + encryptedEdad + "', '" + encryptedEstadoSalud + "', '" + encryptedEspecificaciones + "', '" + encryptedUserId + "')";
            db.execSQL(insert);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void actualizarHistorial(HistorialMedico historial) {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            String encryptedNombre = Encriptacion.encriptar(historial.getNombre(), CLAVE_ENCRIPTACION);
            String encryptedFechaNacimiento = Encriptacion.encriptar(historial.getFechaNacimiento(), CLAVE_ENCRIPTACION);
            String encryptedEdad = Encriptacion.encriptar(historial.getEdad(), CLAVE_ENCRIPTACION);
            String encryptedEstadoSalud = Encriptacion.encriptar(historial.getEstadoSalud(), CLAVE_ENCRIPTACION);
            String encryptedEspecificaciones = Encriptacion.encriptar(historial.getEspecificaciones(), CLAVE_ENCRIPTACION);
            String encryptedUserId = Encriptacion.encriptar(historial.getUserId(), CLAVE_ENCRIPTACION);

            String update = "UPDATE " + TABLE_HISTORIAL + " SET " +
                    COLUMN_NOMBRE + " = '" + encryptedNombre + "', " +
                    COLUMN_FECHA_NACIMIENTO + " = '" + encryptedFechaNacimiento + "', " +
                    COLUMN_EDAD + " = '" + encryptedEdad + "', " +
                    COLUMN_ESTADO_SALUD + " = '" + encryptedEstadoSalud + "', " +
                    COLUMN_ESPECIFICACIONES + " = '" + encryptedEspecificaciones + "', " +
                    COLUMN_USER_ID + " = '" + encryptedUserId + "' WHERE " +
                    COLUMN_ID + " = " + historial.getId();
            db.execSQL(update);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<HistorialMedico> obtenerHistoriales() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_HISTORIAL, null);
        List<HistorialMedico> historiales = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                try {
                    int id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID));
                    String nombre = Encriptacion.desencriptar(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NOMBRE)), CLAVE_ENCRIPTACION);
                    String fechaNacimiento = Encriptacion.desencriptar(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_FECHA_NACIMIENTO)), CLAVE_ENCRIPTACION);
                    String edad = Encriptacion.desencriptar(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_EDAD)), CLAVE_ENCRIPTACION);
                    String estadoSalud = Encriptacion.desencriptar(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ESTADO_SALUD)), CLAVE_ENCRIPTACION);
                    String especificaciones = Encriptacion.desencriptar(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ESPECIFICACIONES)), CLAVE_ENCRIPTACION);
                    String userId = Encriptacion.desencriptar(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_USER_ID)), CLAVE_ENCRIPTACION);

                    HistorialMedico historial = new HistorialMedico(id, nombre, fechaNacimiento, edad, estadoSalud, especificaciones, userId);
                    historiales.add(historial);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } while (cursor.moveToNext());
        }
        cursor.close();
        return historiales;
    }
}