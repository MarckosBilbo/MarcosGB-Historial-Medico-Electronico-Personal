package com.example.marcosgb_historial_mdico_electrnico_personal.modelo;

import com.example.marcosgb_historial_mdico_electrnico_personal.util.Encriptacion;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Firebase {
    private final DatabaseReference database;
    private static final String CLAVE_ENCRIPTACION = "tu-clave-de-encriptacion";

    public Firebase() {
        database = FirebaseDatabase.getInstance("https://historial-medico-1f036-default-rtdb.europe-west1.firebasedatabase.app/").getReference();
    }

    public void saveHistorial(HistorialMedico historial, String userId) {
        try {
            String encryptedNombre = Encriptacion.encriptar(historial.getNombre(), CLAVE_ENCRIPTACION);
            String encryptedFechaNacimiento = Encriptacion.encriptar(historial.getFechaNacimiento(), CLAVE_ENCRIPTACION);
            String encryptedEdad = Encriptacion.encriptar(historial.getEdad(), CLAVE_ENCRIPTACION);
            String encryptedEstadoSalud = Encriptacion.encriptar(historial.getEstadoSalud(), CLAVE_ENCRIPTACION);
            String encryptedEspecificaciones = Encriptacion.encriptar(historial.getEspecificaciones(), CLAVE_ENCRIPTACION);
            String encryptedUserId = Encriptacion.encriptar(historial.getUserId(), CLAVE_ENCRIPTACION);

            HistorialMedico encryptedHistorial = new HistorialMedico(
                    historial.getId(),
                    encryptedNombre,
                    encryptedFechaNacimiento,
                    encryptedEdad,
                    encryptedEstadoSalud,
                    encryptedEspecificaciones,
                    encryptedUserId
            );

            database.child("historiales").child(userId).setValue(encryptedHistorial);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateHistorial(HistorialMedico historial, String userId) {
        saveHistorial(historial, userId);
    }

    public void obtenerHistoriales(final DataCallback callback) {
        database.child("historiales").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<HistorialMedico> historiales = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    try {
                        HistorialMedico historial = snapshot.getValue(HistorialMedico.class);

                        String decryptedNombre = Encriptacion.desencriptar(historial.getNombre(), CLAVE_ENCRIPTACION);
                        String decryptedFechaNacimiento = Encriptacion.desencriptar(historial.getFechaNacimiento(), CLAVE_ENCRIPTACION);
                        String decryptedEdad = Encriptacion.desencriptar(historial.getEdad(), CLAVE_ENCRIPTACION);
                        String decryptedEstadoSalud = Encriptacion.desencriptar(historial.getEstadoSalud(), CLAVE_ENCRIPTACION);
                        String decryptedEspecificaciones = Encriptacion.desencriptar(historial.getEspecificaciones(), CLAVE_ENCRIPTACION);
                        String decryptedUserId = Encriptacion.desencriptar(historial.getUserId(), CLAVE_ENCRIPTACION);

                        HistorialMedico decryptedHistorial = new HistorialMedico(
                                historial.getId(),
                                decryptedNombre,
                                decryptedFechaNacimiento,
                                decryptedEdad,
                                decryptedEstadoSalud,
                                decryptedEspecificaciones,
                                decryptedUserId
                        );

                        historiales.add(decryptedHistorial);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                callback.onSuccess(historiales);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                callback.onFailure(databaseError.toException());
            }
        });
    }

    public interface DataCallback {
        void onSuccess(List<HistorialMedico> historiales);
        void onFailure(Exception e);
    }
}