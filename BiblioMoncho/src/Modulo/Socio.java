package Modulo;



//*El objeto Socio con sus atributos,constructores Getters y Setters */

public class Socio {
    private int id;
    private String nombre;
    private String apellidos;
    private int edad;
    private String direccion;
    private String telefono;
    private String urlImagen;
    private Boolean activo;
    
   
    public Socio(){}
    public Socio(int id, String nombre, String apellidos, int edad, String direccion, String telefono,
            String urlImagen) {
        this.id = id;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.edad = edad;
        this.direccion = direccion;
        this.telefono = telefono;
        this.urlImagen = urlImagen;
    }
    public Socio(int id, String nombre, String apellidos, int edad, String direccion, String telefono,
            String urlImagen, Boolean activo) {
        this.id = id;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.edad = edad;
        this.direccion = direccion;
        this.telefono = telefono;
        this.urlImagen = urlImagen;
        this.activo = activo;
    }
    public Socio(String nombre, String apellidos, int edad, String direccion, String telefono,
            String urlImagen, Boolean activo) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.edad = edad;
        this.direccion = direccion;
        this.telefono = telefono;
        this.urlImagen = urlImagen;
        this.activo = activo;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
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
    public int getEdad() {
        return edad;
    }
    public void setEdad(int edad) {
        this.edad = edad;
    }
    public String getDireccion() {
        return direccion;
    }
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
    public String getTelefono() {
        return telefono;
    }
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
    public String getUrlImagen() {
        return urlImagen;
    }
    public void setUrlImagen(String urlImagen) {
        this.urlImagen = urlImagen;
    }
    public Boolean getActivo() {
        return activo;
    }
    public void setActivo(Boolean activo) {
        this.activo = activo;
    }
    @Override
    public String toString() {
        return getNombre()+" "+getApellidos();
    }
    
}
