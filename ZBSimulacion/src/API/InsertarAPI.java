package API;

import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import Handlers.PedidoToJSON;
import Model.Pedido;

public class InsertarAPI {
    private static final String API_URL = "http://localhost:6240/api/pedidos";
    private final HttpClient client = HttpClient.newHttpClient();
    private final ObjectMapper objectMapper = new ObjectMapper();

    public Pedido enviarPedido(Pedido pedido) {
        try {
            // Convertir el pedido a JSON
            PedidoToJSON pedidoToJSON = new PedidoToJSON();
            String json = pedidoToJSON.convertirPedidoAJson(pedido);
            if (json != null) {
                // Crear la solicitud HTTP
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create(API_URL))
                        .header("Content-Type", "application/json")
                        .POST(HttpRequest.BodyPublishers.ofString(json))
                        .build();

                // Enviar la solicitud
                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

                // Manejar la respuesta
                if (response.statusCode() == 201) {
                    System.out.println("El camarero crea la comanda: " + response.body());
                    pedido.setIdPedido(response.body());
                    return pedido;
                } else {
                    System.err.println("Error al enviar el pedido. Código: " + response.statusCode());
                    System.err.println("Respuesta: " + response.body());
                }

            } else {
                System.err.println("No se pudo convertir el pedido a JSON.");
            }
        } catch (Exception e) {
            System.err.println("Error al enviar el pedido: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
}