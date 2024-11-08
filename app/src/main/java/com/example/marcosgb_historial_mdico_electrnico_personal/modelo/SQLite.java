package com.example.marcosgb_historial_mdico_electrnico_personal.modelo;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class SQLite extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "historial_medico.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_HISTORIAL = "historial";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NOMBRE = "nombre";
    private static final String COLUMN_FECHA_NACIMIENTO = "fecha_nacimiento";
    private static final String COLUMN_HISTORIAL = "historial";

    public SQLite(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_HISTORIAL + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NOMBRE + " TEXT, " +
                COLUMN_FECHA_NACIMIENTO + " TEXT, " +
                COLUMN_HISTORIAL + " TEXT)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_HISTORIAL);
        onCreate(db);
    }

    public void insertarHistorial(HistorialMedico historial) {
        SQLiteDatabase db = this.getWritableDatabase();
        String insert = "INSERT INTO " + TABLE_HISTORIAL + " (" + COLUMN_NOMBRE + ", " + COLUMN_FECHA_NACIMIENTO + ", " + COLUMN_HISTORIAL + ") VALUES ('" +
                historial.getNombre() + "', '" + historial.getFechaNacimiento() + "', '" + historial.getHistorial() + "')";
        db.execSQL(insert);
    }

    public List<HistorialMedico> obtenerHistoriales() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_HISTORIAL, null);
        List<HistorialMedico> historiales = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                HistorialMedico historial = new HistorialMedico(
                        cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NOMBRE)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_FECHA_NACIMIENTO)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_HISTORIAL))
                );
                historiales.add(historial);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return historiales;
    }
}