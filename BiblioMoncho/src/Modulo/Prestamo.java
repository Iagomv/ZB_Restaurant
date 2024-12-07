package Modulo;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import Manager.LibroManager;
import Manager.SocioManager;



//*El objeto Prestamo con sus atributos,constructores Getters y Setters */

public class Prestamo {
    private int id;
    private List<Libro> listaLibros;
    private Libro libro;
    private Socio socio;
    private Date fechaInicio;
    private Date fechaFin;
    private int libro_id;
    private int socio_id;
    private Boolean devuelto;
    
    
    
    

    
    public Prestamo(){};
    public Prestamo(int id, int libro_id, int socio_id, Date fechaInicio, Date fechaFin) {
        this.id = id;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.libro = obtenerObjetoLibro(libro_id);
        this.socio = obtenerObjetoSocio(socio_id);
    }
    public Prestamo(int id, int libro_id, int socio_id, Date fechaInicio, Date fechaFin, Boolean Devuelto) {
        this.id = id;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.libro = obtenerObjetoLibro(libro_id);
        this.socio = obtenerObjetoSocio(socio_id);
        this.devuelto = Devuelto;
    }
    public Prestamo(int id, List<Libro> listaLibros, Socio socio, Date fechaInicio, Date fechaFin) {
        this.id = id;
        this.listaLibros = listaLibros;
        this.socio = socio;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
    }
    public Prestamo(int libro_id, int socio_id, Date fechaInicio, Date fechaFin) {
        this.libro_id = libro_id;
        this.socio_id = socio_id;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.libro = obtenerObjetoLibro(libro_id);
        this.socio = obtenerObjetoSocio(socio_id);
    }
    
    

    // Métodos para obtener Objetos Libro y Socio a partir de los respectivos _id
    private Libro obtenerObjetoLibro(int libro_id){
        LibroManager libroManager = new LibroManager();
        Libro libro = new Libro();
        try {
            libro = libroManager.obtenerLibroPorId(libro_id);
        } catch (SQLException e) {
            System.out.println("Error al obtener el libro en el metodo obtenerObjetoLibro");
            e.printStackTrace();
        }
        return libro;
    }
    private Socio obtenerObjetoSocio(int socio_id){
        SocioManager socioManager = new SocioManager();
        Socio socio = new Socio();
        try {
            socio = socioManager.obtenerSocioPorId(socio_id);
        } catch (SQLException e) {
            System.out.println("Error al obtener el socio en el metodo obtenerObjetoSocio");
            e.printStackTrace();
        }
        return socio;
    }
    
    
    public List<String> obtenerListaTitulos(){
        List<String> titulos = new ArrayList<>();
        for (int i = 0; i < listaLibros.size(); i++) {
            for (Libro libro : listaLibros) {
                titulos.add(libro.getTitulo());
            }
        }
        return titulos;
    }


    //Getters y setters
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public List<Libro> getListaLibros() {
        return listaLibros;
    }
    public void setListaLibros(List<Libro> listaLibros) {
        this.listaLibros = listaLibros;
    }
    public Libro getLibro() {
        return libro;
    }
    public void setLibro(Libro libro) {
        this.libro = libro;
    }
    public Socio getSocio() {
        return socio;
    }
    public void setSocio(Socio socio) {
        this.socio = socio;
    }
    public Date getFechaInicio() {
        return fechaInicio;
    }
    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }
    public Date getFechaFin() {
        return fechaFin;
    }
    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }
    public int getLibro_id() {
        return libro_id;
    }
    public void setLibro_id(int libro_id) {
        this.libro_id = libro_id;
    }
    public int getSocio_id() {
        return socio_id;
    }
    public void setSocio_id(int socio_id) {
        this.socio_id = socio_id;
    }
    public Boolean getDevuelto() {
        return devuelto;
    }
    public void setDevuelto(Boolean devuelto) {
        this.devuelto = devuelto;
    }
    @Override
    public String toString() {
        return "Prestamo [id=" + id + ", listaLibros=" + listaLibros + ", socio=" + socio + ", fechaInicio="
                + fechaInicio + ", fechaFin=" + fechaFin + "]";
    }
}
