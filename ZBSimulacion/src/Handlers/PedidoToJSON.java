package Handlers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.util.JacksonFeatureSet;
import com.fasterxml.jackson.databind.ObjectMapper;

import Model.Bebida;
import Model.Cliente;
import Model.Pedido;
import Model.Plato;

import java.util.HashMap;
import java.util.Map;

public class PedidoToJSON {
    private ObjectMapper objectMapper = new ObjectMapper();

    public String convertirPedidoAJson(Pedido pedido) {
        // Crear un mapa para organizar los datos en el formato deseado
        Map<String, Object> pedidoMap = new HashMap<>();

        // Agregar propiedades del pedido
        pedidoMap.put("idPedido", pedido.getIdPedido());
        pedidoMap.put("estado", pedido.getEstado());

        // Agregar bebida
        Bebida bebida = pedido.getBebidaPedido();
        Map<String, Object> bebidaMap = new HashMap<>();
        bebidaMap.put("nombre", bebida.getNombre());
        bebidaMap.put("tipo", bebida.getTipo());
        bebidaMap.put("precio", bebida.getPrecio());
        bebidaMap.put("stock", bebida.getStock());
        pedidoMap.put("bebida", bebidaMap);

        // Agregar platos: entrante, primero, postre
        pedidoMap.put("entrante", convertirPlatoAMapa(pedido.getEntrante()));
        pedidoMap.put("primero", convertirPlatoAMapa(pedido.getPrimero()));
        pedidoMap.put("postre", convertirPlatoAMapa(pedido.getPostre()));

        // Agregar información del cliente
        Cliente cliente = pedido.getCliente();
        Map<String, Object> clienteMap = new HashMap<>();
        clienteMap.put("numeroCliente", cliente.getNumeroCliente());
        clienteMap.put("numeroComensales", cliente.getNumeroComensales());
        clienteMap.put("numeroMesa", cliente.getNumeroMesa());
        clienteMap.put("estado", cliente.getEstado());
        pedidoMap.put("cliente", clienteMap);

        // Agregar precio total
        pedidoMap.put("precio", pedido.getPrecio());

        // Convertir el mapa a JSON
        try {
            return objectMapper.writeValueAsString(pedidoMap);
        } catch (JsonProcessingException e) {
            System.out.println("Hubo un error al convertir el pedido a JSON");
            e.printStackTrace();
        }
        return null;
    }

    private Map<String, Object> convertirPlatoAMapa(Plato plato) {
        Map<String, Object> platoMap = new HashMap<>();
        platoMap.put("nombre", plato.getNombre());
        platoMap.put("tipo", plato.getTipo());
        platoMap.put("precio", plato.getPrecio());
        platoMap.put("stock", plato.getStock());
        return platoMap;
    }
}
