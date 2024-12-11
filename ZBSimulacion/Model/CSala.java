package Model;

import java.util.concurrent.Semaphore;

public class CSala extends Semaphore{
    private int cantidadMesas;


    public CSala(int cantidadMesas) {
        super(cantidadMesas);
        this.cantidadMesas = cantidadMesas;
    }
}
