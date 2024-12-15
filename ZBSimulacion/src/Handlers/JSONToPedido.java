package Handlers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import Model.Bebida;
import Model.Pedido;
import Model.Plato;
import Model.Cliente;

import java.io.IOException;

public class JSONToPedido {
    private ObjectMapper objectMapper = new ObjectMapper();

    public Pedido convertirJsonAPedido(String json) {
        try {
            // Convertir el JSON a un nodo JsonNode
            JsonNode rootNode = objectMapper.readTree(json);

            // Crear un objeto Pedido
            Pedido pedido = new Pedido();

            // Asignar las propiedades del pedido desde el JSON (manteniendo como String)
            pedido.setIdPedido(rootNode.path("idPedido").asText()); // idPedido como String
            pedido.setEstado(rootNode.path("estado").asText()); // estado como String

            // Mantener el precio como String
            pedido.setPrecio(rootNode.path("precio").asText()); // precio como String

            // Convertir Cliente (mantener como String)
            Cliente cliente = new Cliente();
            JsonNode clienteNode = rootNode.path("cliente");
            cliente.setNumeroCliente(clienteNode.path("numeroCliente").asInt());
            cliente.setNumeroComensales(clienteNode.path("numeroComensales").asInt());
            cliente.setNumeroMesa(clienteNode.path("numeroMesa").asInt());
            cliente.setEstado(clienteNode.path("estado").asText());
            pedido.setCliente(cliente);

            // Convertir Bebida (mantener como String)
            Bebida bebida = new Bebida();
            JsonNode bebidaNode = rootNode.path("bebida");
            bebida.setNombre(bebidaNode.path("nombre").asText());
            bebida.setTipo(bebidaNode.path("tipo").asText());
            bebida.setPrecio(bebidaNode.path("precio").asText());
            bebida.setStock(bebidaNode.path("stock").asText());
            bebida.setEstado(bebidaNode.path("estado").asText());
            pedido.setBebidaPedido(bebida);

            // Convertir Platos (entrante, primero, postre) (mantener como String)
            Plato entrante = convertirPlatoDeJson(rootNode.path("entrante"));
            pedido.setEntrante(entrante);

            Plato primero = convertirPlatoDeJson(rootNode.path("primero"));
            pedido.setPrimero(primero);

            Plato postre = convertirPlatoDeJson(rootNode.path("postre"));
            pedido.setPostre(postre);

            return pedido;

        } catch (IOException e) {
            System.out.println("Hubo un error al convertir el JSON a Pedido");
            e.printStackTrace();
        }
        return null;
    }

    private Plato convertirPlatoDeJson(JsonNode platoNode) {
        Plato plato = new Plato();
        plato.setNombre(platoNode.path("nombre").asText()); 
        plato.setTipo(platoNode.path("tipo").asText()); 
        plato.setPrecio(platoNode.path("precio").asText()); 
        plato.setEstado(platoNode.path("estado").asText()); 
        plato.setStock(platoNode.path("stock").asText()); 
        return plato;
    }
}
