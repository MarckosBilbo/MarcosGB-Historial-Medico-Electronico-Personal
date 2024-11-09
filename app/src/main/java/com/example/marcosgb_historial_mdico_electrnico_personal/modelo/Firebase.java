package com.example.marcosgb_historial_mdico_electrnico_personal.modelo;

import com.example.marcosgb_historial_mdico_electrnico_personal.util.Encriptacion;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

            DatabaseReference userRef = database.child("historiales").child(userId);
            userRef.child("nombre").setValue(encryptedNombre);
            userRef.child("fechaNacimiento").setValue(encryptedFechaNacimiento);
            userRef.child("edad").setValue(encryptedEdad);
            userRef.child("estadoSalud").setValue(encryptedEstadoSalud);
            userRef.child("especificaciones").setValue(encryptedEspecificaciones);
            userRef.child("userId").setValue(encryptedUserId);
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

    public void obtenerNumeroUsuarios(final NumeroUsuariosCallback callback) {
        database.child("usuarios").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int numeroUsuarios = (int) dataSnapshot.getChildrenCount();
                callback.onSuccess(numeroUsuarios);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                callback.onFailure(databaseError.toException());
            }
        });
    }

    public void guardarResultadosEncuesta(String userId, String satisfaccionServicio, String satisfaccionSeguridad, String satisfaccionUtilidad, String tipoProblema, String gravedadProblema, String longevidadProblema, String perfilMedico) {
        try {
            String encryptedSatisfaccionServicio = Encriptacion.encriptar(satisfaccionServicio, CLAVE_ENCRIPTACION);
            String encryptedSatisfaccionSeguridad = Encriptacion.encriptar(satisfaccionSeguridad, CLAVE_ENCRIPTACION);
            String encryptedSatisfaccionUtilidad = Encriptacion.encriptar(satisfaccionUtilidad, CLAVE_ENCRIPTACION);
            String encryptedTipoProblema = Encriptacion.encriptar(tipoProblema, CLAVE_ENCRIPTACION);
            String encryptedGravedadProblema = Encriptacion.encriptar(gravedadProblema, CLAVE_ENCRIPTACION);
            String encryptedLongevidadProblema = Encriptacion.encriptar(longevidadProblema, CLAVE_ENCRIPTACION);
            String encryptedPerfilMedico = Encriptacion.encriptar(perfilMedico, CLAVE_ENCRIPTACION);

            DatabaseReference userRef = database.child("usuarios").child(userId).child("resultadosEncuesta");
            userRef.child("satisfaccionServicio").setValue(encryptedSatisfaccionServicio);
            userRef.child("satisfaccionSeguridad").setValue(encryptedSatisfaccionSeguridad);
            userRef.child("satisfaccionUtilidad").setValue(encryptedSatisfaccionUtilidad);
            userRef.child("tipoProblema").setValue(encryptedTipoProblema);
            userRef.child("gravedadProblema").setValue(encryptedGravedadProblema);
            userRef.child("longevidadProblema").setValue(encryptedLongevidadProblema);
            userRef.child("perfilMedico").setValue(encryptedPerfilMedico);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void obtenerResultadosEncuesta(String userId, final ResultadosEncuestaCallback callback) {
        database.child("usuarios").child(userId).child("resultadosEncuesta").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try {
                    String satisfaccionServicio = Encriptacion.desencriptar(dataSnapshot.child("satisfaccionServicio").getValue(String.class), CLAVE_ENCRIPTACION);
                    String satisfaccionSeguridad = Encriptacion.desencriptar(dataSnapshot.child("satisfaccionSeguridad").getValue(String.class), CLAVE_ENCRIPTACION);
                    String satisfaccionUtilidad = Encriptacion.desencriptar(dataSnapshot.child("satisfaccionUtilidad").getValue(String.class), CLAVE_ENCRIPTACION);
                    String tipoProblema = Encriptacion.desencriptar(dataSnapshot.child("tipoProblema").getValue(String.class), CLAVE_ENCRIPTACION);
                    String gravedadProblema = Encriptacion.desencriptar(dataSnapshot.child("gravedadProblema").getValue(String.class), CLAVE_ENCRIPTACION);
                    String longevidadProblema = Encriptacion.desencriptar(dataSnapshot.child("longevidadProblema").getValue(String.class), CLAVE_ENCRIPTACION);
                    String perfilMedico = Encriptacion.desencriptar(dataSnapshot.child("perfilMedico").getValue(String.class), CLAVE_ENCRIPTACION);

                    Map<String, String> resultados = new HashMap<>();
                    resultados.put("satisfaccionServicio", satisfaccionServicio);
                    resultados.put("satisfaccionSeguridad", satisfaccionSeguridad);
                    resultados.put("satisfaccionUtilidad", satisfaccionUtilidad);
                    resultados.put("tipoProblema", tipoProblema);
                    resultados.put("gravedadProblema", gravedadProblema);
                    resultados.put("longevidadProblema", longevidadProblema);
                    resultados.put("perfilMedico", perfilMedico);

                    callback.onSuccess(resultados);
                } catch (Exception e) {
                    callback.onFailure(e);
                }
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

    public interface NumeroUsuariosCallback {
        void onSuccess(int numeroUsuarios);
        void onFailure(Exception e);
    }

    public interface ResultadosEncuestaCallback {
        void onSuccess(Map<String, String> resultados);
        void onFailure(Exception e);
    }
}