package com.example.marcosgb_historial_mdico_electrnico_personal.modelo;

import com.example.marcosgb_historial_mdico_electrnico_personal.modelo.HistorialMedico;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Firebase {
    private final DatabaseReference database;

    public Firebase() {
        database = FirebaseDatabase.getInstance("https://historial-medico-1f036-default-rtdb.europe-west1.firebasedatabase.app/").getReference();
    }

    public void saveHistorial(HistorialMedico historial) {
        String key = database.child("historiales").push().getKey();
        if (key != null) {
            database.child("historiales").child(key).setValue(historial);
        }
    }

    public void borrarTodosLosHistoriales() {
        database.child("historiales").removeValue();
    }

    public void obtenerHistoriales(final DataCallback callback) {
        database.child("historiales").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<HistorialMedico> historiales = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    HistorialMedico historial = snapshot.getValue(HistorialMedico.class);
                    historiales.add(historial);
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