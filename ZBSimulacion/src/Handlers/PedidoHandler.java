package Handlers;

import Model.Bebida;
import Model.Cliente;
import Model.Pedido;
import Model.Plato;
import Static.Carta;

public class PedidoHandler {

    public Pedido crearPedido(int entranteElegido, int primeroElegido, int postreElegido, int bebidaElegida,
            Cliente cliente) {
        try {
            Bebida bebida = Carta.bebidas.get(bebidaElegida);
            Plato entrante = Carta.entrantes.get(entranteElegido);
            Plato primero = Carta.primeros.get(primeroElegido);
            Plato postre = Carta.postres.get(postreElegido);
            Pedido pedido = new Pedido("0", "pendiente", bebida, entrante, primero, postre, cliente.getMesa(),
                    cliente);
            return pedido;
        } catch (Exception e) {
            System.out.println("PedidoHandler: Hubo un error al crear el pedido");
            e.printStackTrace();
        }
        return null;
    }
}
