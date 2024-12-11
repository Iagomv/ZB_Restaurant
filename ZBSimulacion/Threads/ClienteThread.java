package Threads;

import Model.Cliente;
import Model.Pedido;
import Static.Personal;

public class ClienteThread extends Cliente implements Runnable {

    public ClienteThread(Cliente cliente) {
        super(cliente.getNumeroCliente(), cliente.getNumeroComensales(), cliente.getNumeroMesa(), cliente.getEstado(),
                cliente.getPedido());
    }

    public void run(String accion) {
        switch (accion) {
            case "generarCliente":
                System.out.println("El cliente " + getNumeroCliente() + " ha llegado a la CSala");
                Personal.CSala.acquire();
                System.out.println("El cliente " + getNumeroCliente() + " se sienta a la mesa");
                break;
            case "1":

                break;
            case "2":
                break;

            default:
                break;
        }

    }

}
