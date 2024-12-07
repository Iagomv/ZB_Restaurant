package Modulo;




//*El objeto Libro con sus atributos,constructores Getters y Setters */

public class Libro {
    private int id;
    private String titulo;
    private int numeroEjemplares;
    private String editorial;
    private int numeroPaginas;
    private int anoEdicion;
    private int stock;
    private String urlImagen;
    private Boolean Descatalogado;


    
    public Libro(int id, String titulo, int numeroEjemplares,int stock, String editorial, int numeroPaginas, int anoEdicion,
            String urlImagen) {
        this.id = id;
        this.titulo = titulo;
        this.numeroEjemplares = numeroEjemplares;
        this.editorial = editorial;
        this.numeroPaginas = numeroPaginas;
        this.anoEdicion = anoEdicion;
        this.urlImagen = urlImagen;
    }
    public Libro(String titulo, int numeroEjemplares,int stock, String editorial, int numeroPaginas, int anoEdicion,
            String urlImagen) {
        this.titulo = titulo;
        this.numeroEjemplares = numeroEjemplares;
        this.stock = stock;
        this.editorial = editorial;
        this.numeroPaginas = numeroPaginas;
        this.anoEdicion = anoEdicion;
        this.urlImagen = urlImagen;
    }
    // Constructor, getters y setters
    public Libro(String titulo, int numeroEjemplares, String editorial, int numeroPaginas, int anoEdicion,
            String urlImagen) {
        this.id = id;
        this.titulo = titulo;
        this.numeroEjemplares = numeroEjemplares;
        this.editorial = editorial;
        this.numeroPaginas = numeroPaginas;
        this.anoEdicion = anoEdicion;
        this.urlImagen = urlImagen;
    }
    public Libro(){}
    public Libro(String titulo, int numeroEjemplares,int stock, String editorial, int numeroPaginas, int anoEdicion,
        String urlImagen, Boolean Descatalogado) {
        this.id = id;
        this.titulo = titulo;
        this.numeroEjemplares = numeroEjemplares;
        this.editorial = editorial;
        this.numeroPaginas = numeroPaginas;
        this.anoEdicion = anoEdicion;
        this.urlImagen = urlImagen;
        this.Descatalogado = Descatalogado;
    }
    
    @Override
    public String toString() {
        return getTitulo();
    }
    
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getTitulo() {
        return titulo;
    }
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
    public int getNumeroEjemplares() {
        return numeroEjemplares;
    }
    public void setNumeroEjemplares(int numeroEjemplares) {
        this.numeroEjemplares = numeroEjemplares;
    }
    public int getStock() {
        return stock;
    }
    public void setStock(int stock) {
        this.stock = stock;
    }
    public String getEditorial() {
        return editorial;
    }
    public void setEditorial(String editorial) {
        this.editorial = editorial;
    }
    public int getNumeroPaginas() {
        return numeroPaginas;
    }
    public void setNumeroPaginas(int numeroPaginas) {
        this.numeroPaginas = numeroPaginas;
    }
    public int getAnoEdicion() {
        return anoEdicion;
    }
    public void setAnoEdicion(int anoEdicion) {
        this.anoEdicion = anoEdicion;
    }
    public String getUrlImagen() {
        return urlImagen;
    }
    public void setUrlImagen(String urlImagen) {
        this.urlImagen = urlImagen;
    }
    public Boolean getDescatalogado() {
        return Descatalogado;
    }
    public void setDescatalogado(Boolean descatalogado) {
        Descatalogado = descatalogado;
    }
    
}
