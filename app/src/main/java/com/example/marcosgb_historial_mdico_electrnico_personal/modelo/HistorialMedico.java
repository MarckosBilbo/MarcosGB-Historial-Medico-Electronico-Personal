package com.example.marcosgb_historial_mdico_electrnico_personal.modelo;

public class HistorialMedico {
    private int id;
    private String nombre;
    private String fechaNacimiento;
    private String edad;
    private String estadoSalud;
    private String especificaciones;
    private String userId;

    // No-argument constructor
    public HistorialMedico() {
    }

    // Constructor with all fields
    public HistorialMedico(int id, String nombre, String fechaNacimiento, String edad, String estadoSalud, String especificaciones, String userId) {
        this.id = id;
        this.nombre = nombre;
        this.fechaNacimiento = fechaNacimiento;
        this.edad = edad;
        this.estadoSalud = estadoSalud;
        this.especificaciones = especificaciones;
        this.userId = userId;
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public String getEdad() {
        return edad;
    }

    public String getEstadoSalud() {
        return estadoSalud;
    }

    public String getEspecificaciones() {
        return especificaciones;
    }

    public String getUserId() {
        return userId;
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

    public void setEdad(String edad) {
        this.edad = edad;
    }

    public void setEstadoSalud(String estadoSalud) {
        this.estadoSalud = estadoSalud;
    }

    public void setEspecificaciones(String especificaciones) {
        this.especificaciones = especificaciones;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}