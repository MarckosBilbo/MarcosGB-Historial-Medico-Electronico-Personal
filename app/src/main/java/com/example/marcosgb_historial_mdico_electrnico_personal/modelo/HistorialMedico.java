package com.example.marcosgb_historial_mdico_electrnico_personal.modelo;

public class HistorialMedico {
    private int id;
    private String nombre;
    private String fechaNacimiento;
    private String historial;

    public HistorialMedico(int id, String nombre, String fechaNacimiento, String historial) {
        this.id = id;
        this.nombre = nombre;
        this.fechaNacimiento = fechaNacimiento;
        this.historial = historial;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public String getHistorial() {
        return historial;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public void setHistorial(String historial) {
        this.historial = historial;
    }
}