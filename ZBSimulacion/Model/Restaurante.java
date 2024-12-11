package Model;

import java.util.Random;

import Static.Personal;

public class Restaurante {
    private CSala cSala = new CSala(10);
    private Camarero[] camareros;
    private Cocinero[] cocineros;
    private Sommelier sommelier;
    private Mesa[] mesas;
    private Cliente[] clientes;
    private int cantidadMesas;

    public Restaurante(Camarero[] camareros, Cocinero[] cocineros, CSala cSala, Sommelier sommelier,
            int cantidadMesas) {
        this.camareros = camareros;
        this.cocineros = cocineros;
        this.cSala = cSala;
        this.sommelier = sommelier;
        this.mesas = mesas;
        this.clientes = clientes;
        this.cantidadMesas = cantidadMesas;
    }

    public CSala getcSala() {
        return cSala;
    }

    public void setcSala(CSala cSala) {
        this.cSala = cSala;
    }

    public Camarero[] getCamareros() {
        return camareros;
    }

    public void setCamareros(Camarero[] camareros) {
        this.camareros = camareros;
    }

    public Cocinero[] getCocineros() {
        return cocineros;
    }

    public void setCocineros(Cocinero[] cocineros) {
        this.cocineros = cocineros;
    }

    public Sommelier getSommelier() {
        return sommelier;
    }

    public void setSommelier(Sommelier sommelier) {
        this.sommelier = sommelier;
    }

    public Mesa[] getMesas() {
        return mesas;
    }

    public void setMesas(Mesa[] mesas) {
        this.mesas = mesas;
    }

    public Cliente[] getClientes() {
        return clientes;
    }

    public void setClientes(Cliente[] clientes) {
        this.clientes = clientes;
    }

    private void generarMesas() {
        for (int i = 0; i < cantidadMesas; i++) {
            Random random = new Random();
            int capacidadMesa = ((random.nextInt(4) + 1) > 2) ? 4 : 2; // Capacidad de 4 o 2 comensales
            Mesa mesa = new Mesa(i+1,capacidadMesa, "Libre", null, camareros[i%4], null);
    }
}
