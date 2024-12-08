package model;



public class Comida {
    private int id;
    
    private String nombre;
    private String tipo;
    private String precio;
    private String stock;

    //TODO ID?

    
    public Comida(String nombre, String tipo, String precio, String stock) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.precio = precio;
        this.stock = stock;
    }
    public Comida(int id, String nombre, String tipo, String precio, String stock) {
        this.id = id;
        this.nombre = nombre;
        this.tipo = tipo;
        this.precio = precio;
        this.stock = stock;
    }


    public String[] getParametrosComida() {
        return new String[] { this.nombre, this.tipo, this.precio, this.stock };
    }
    
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getTipo() {
        return tipo;
    }
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    public String getPrecio() {
        return precio;
    }
    public void setPrecio(String precio) {
        this.precio = precio;
    }
    public String getStock() {
        return stock;
    }
    public void setStock(String stock) {
        this.stock = stock;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

}
