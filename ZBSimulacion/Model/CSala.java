package Model;

import java.util.concurrent.Semaphore;

public class CSala extends Semaphore {
    private int cantidadMesas;

    public CSala(int cantidadMesas) {
        super(cantidadMesas);
        this.cantidadMesas = cantidadMesas;
    }

    public void acquire() {
        try {
            super.acquire();
            Thread.sleep(1000);
            System.out.println("CSala sienta al cliente en la mesa");
        } catch (InterruptedException e) {
            System.out.println("Interruted Exception en CSala");
            e.printStackTrace();
        }
    }

    public void release() {
        super.release();
    }
}
