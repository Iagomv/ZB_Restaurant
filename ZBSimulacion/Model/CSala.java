package Model;

import java.util.concurrent.Semaphore;

import Static.Personal;

public class CSala extends Semaphore {
    private int cantidadMesas;

    public CSala(int cantidadMesas) {
        super(cantidadMesas);
        this.cantidadMesas = cantidadMesas;
        System.out.println("CSala creado con " + cantidadMesas + " mesas");
    }

    public void acquire() {
        try {
            super.acquire();
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            System.out.println("Interruted Exception en CSala");
            e.printStackTrace();
        }
    }

    public void release() {
        super.release();
    }

    @Override
    public String toString() {
        return "CSala [cantidadMesas=" + cantidadMesas + "]";
    }

    public Mesa asignarMesa(Cliente cliente) {
        int comensales = cliente.getNumeroComensales();
        for (Mesa mesa : Personal.mesas) {
            if (!mesa.isOcupada() && (mesa.getCapacidadMesa() >= comensales)) {
                mesa.setCliente(cliente);
                acquire();
                System.out.println(
                        "Se ha asignado la mesa(" + mesa.getCapacidadMesa() + ") " + mesa.getNumeroMesa()
                                + " al cliente " + cliente.getNumeroCliente() + "(" + comensales + ")");
                return mesa;
            }
        }
        return null;
    }

}
