package model;

public class Bebida {
    
    private int id_bebida;
    private String nombre;
    private String tipo;
    private String precio;
    private String stock;
    
    public Bebida(String nombre, String tipo, String precio, String stock) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.precio = precio;
        this.stock = stock;
    }
    public Bebida(int id_bebida, String nombre, String tipo, String precio, String stock) {
        this.id_bebida = id_bebida;
        this.nombre = nombre;
        this.tipo = tipo;
        this.precio = precio;
        this.stock = stock;
    }
    
    public String[] getParametrosBebida() {
        return new String[] { this.nombre, this.tipo, this.precio, this.stock };
    }
    
    @Override
    public String toString() {
        return "Bebida nombre=" + nombre + ", tipo=" + tipo + ", precio=" + precio + ", stock=" + stock
                + "]";
    }
    public int getId() {
        return id_bebida;
    }
    public void setId(int id_bebida) {
        this.id_bebida = id_bebida;
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


}
