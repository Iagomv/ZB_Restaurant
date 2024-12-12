package Threads;

import Model.Camarero;
import Model.Cliente;
import Model.Pedido;
import Model.Plato;

public class CamareroThread extends Camarero implements Runnable {

    public CamareroThread(Camarero camarero) {
        super(camarero.getIdCamarero(), camarero.getEstado(), camarero.getPedido(), camarero.getPlato());
    }

    @Override
    public void run() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'run'");
    }

    public void llevarComandaCocina() {
        // TODO Auto-generated method stub
    }

}
