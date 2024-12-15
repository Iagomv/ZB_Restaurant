package Model;

import java.util.Random;
import Static.Personal;

public class Restaurante {
    private CSala cSala = new CSala(10); // Sala con 10 mesas por defecto (puedes cambiarlo)
    private Camarero[] camareros;
    private Cocinero[] cocineros;
    private Sommelier sommelier;
    private Mesa[] mesas;
    private Cliente[] clientes;
    private int cantidadMesas;

    public int getCantidadMesas() {
        return cantidadMesas;
    }

    public void setCantidadMesas(int cantidadMesas) {
        this.cantidadMesas = cantidadMesas;
    }

    public Restaurante(Camarero[] camareros, Cocinero[] cocineros, CSala cSala, Sommelier sommelier,
            int cantidadMesas) {
        this.camareros = camareros;
        this.cocineros = cocineros;
        this.cSala = cSala;
        this.sommelier = sommelier;
        this.clientes = new Cliente[0]; // Iniciar la lista de clientes como vacía
        this.cantidadMesas = cantidadMesas;
        this.mesas = generarMesas(); // Generar las mesas
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

    // Generación de mesas con capacidad aleatoria de 2 o 4 comensales
    private Mesa[] generarMesas() {
        Mesa[] thisMesas = new Mesa[cantidadMesas];
        Random random = new Random(); // Instancia de Random fuera del ciclo

        for (int i = 0; i < cantidadMesas; i++) {
            int capacidadMesa = (random.nextInt(2) == 0) ? 2 : 4; // Alternar entre 2 o 4 comensales
            Mesa mesa = new Mesa(i + 1, capacidadMesa, "Libre", null, camareros[i % camareros.length], null);
            thisMesas[i] = mesa;
        }

        // Si quieres usar las mesas globalmente en Personal, puedes hacer esto:
        Personal.mesas = thisMesas;

        return thisMesas;
    }
}
