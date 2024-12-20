package model;

public class Personal {

    private int id;
    private String nombre;
    private String apellidos;
    private String dni;
    private String edad;
    private String fechaInicio;
    private String fechaFinContrato;
    private String horasSemanales;
    private String rol;

    @Override
    public String toString() {
        return "Personal [id=" + id + ", nombre=" + nombre + ", apellidos=" + apellidos + ", dni=" + dni + ", edad="
                + edad + ", fechaInicio=" + fechaInicio + ", fechaFinContrato=" + fechaFinContrato + ", horasSemanales="
                + horasSemanales + ", rol=" + rol + "]";
    }

    public Personal(int id, String nombre, String apellidos, String dni, String edad, String fechaInicio,
            String fechaFinContrato, String horasSemanales, String rol) {
        this.id = id;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.dni = dni;
        this.edad = edad;
        this.fechaInicio = fechaInicio;
        this.fechaFinContrato = fechaFinContrato;
        this.horasSemanales = horasSemanales;
        this.rol = rol;
    }

    public Personal(String nombre, String apellidos, String dni, String edad, String fechaInicio,
            String fechaFinContrato, String horasSemanales, String rol) {

        this.nombre = nombre;
        this.apellidos = apellidos;
        this.dni = dni;
        this.edad = edad;
        this.fechaInicio = fechaInicio;
        this.fechaFinContrato = fechaFinContrato;
        this.horasSemanales = horasSemanales;
        this.rol = rol;
    }

    public String[] getParametros() {
        return new String[] { this.nombre, this.apellidos, this.dni, this.edad, this.fechaInicio, this.fechaFinContrato,
                this.horasSemanales, this.rol };
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getEdad() {
        return edad;
    }

    public void setEdad(String edad) {
        this.edad = edad;
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public String getFechaFinContrato() {
        return fechaFinContrato;
    }

    public void setFechaFinContrato(String fechaFinContrato) {
        this.fechaFinContrato = fechaFinContrato;
    }

    public String getHorasSemanales() {
        return horasSemanales;
    }

    public void setHorasSemanales(String horasSemanales) {
        this.horasSemanales = horasSemanales;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
