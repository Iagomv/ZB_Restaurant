package API;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import Handlers.PedidoToJSON;
import Model.Pedido;

public class InsertarAPI {
    private static final String API_URL = "http://dam2.colexio-karbo.com:6240/api/Pedidos";
    private static final String API_URL2 = "http://localhost:6240/api/Pedidos";

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

    // Método para actualizar el estado de un pedido
    public boolean actualizarEstadoPedido(String id, String nuevoEstado) {
        try {
            // Validar que el estado es válido
            String[] estadosValidos = { "pendiente", "entrante", "primero", "postre", "cancelado" };
            boolean estadoValido = false;
            for (String estado : estadosValidos) {
                if (estado.equals(nuevoEstado)) {
                    estadoValido = true;
                    break;
                }
            }

            if (!estadoValido) {
                System.err.println("Estado inválido: " + nuevoEstado);
                return false;
            }

            // Crear el JSON con el nuevo estado
            String json = objectMapper.writeValueAsString(new EstadoPedido(nuevoEstado));

            // Construir la solicitud HTTP
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(API_URL + "/" + id)) // URL con el ID del pedido
                    .header("Content-Type", "application/json")
                    .PUT(HttpRequest.BodyPublishers.ofString(json))
                    .build();

            // Enviar la solicitud
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // Manejar la respuesta
            if (response.statusCode() == 200) {
                System.out.println("Estado del pedido actualizado correctamente.");
                return true;
            } else if (response.statusCode() == 404) {
                System.err.println("Pedido no encontrado: " + id);
            } else {
                System.err.println("Error al actualizar el estado del pedido. Código: " + response.statusCode());
                System.err.println("Respuesta: " + response.body());
            }
        } catch (Exception e) {
            System.err.println("Error al actualizar el estado del pedido: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    // Método para actualizar un pedido completo por ID
    public boolean actualizarPedido(String id, Pedido nuevoPedido) {
        try {
            PedidoToJSON pedidoToJSON = new PedidoToJSON();
            String json = pedidoToJSON.convertirPedidoAJson(nuevoPedido);

            if (json != null) {
                String cleanId = id.replace("\"", "");

                // System.out.println("JSON enviado: " + json);
                System.out.println("URL construida: " + API_URL + "/" + cleanId);

                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create(API_URL + "/" + cleanId))
                        .header("Content-Type", "application/json")
                        .PUT(HttpRequest.BodyPublishers.ofString(json))
                        .build();

                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

                if (response.statusCode() == 200) {
                    System.out.println("Pedido actualizado correctamente. Respuesta: " + response.body());
                    return true;
                } else {
                    System.err.println("Error al actualizar el pedido. Código: " + response.statusCode());
                    System.err.println("Respuesta del servidor: " + response.body());
                }
            } else {
                System.err.println("No se pudo convertir el nuevo pedido a JSON.");
            }
        } catch (Exception e) {
            System.err.println("Error al actualizar el pedido: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    public Pedido obtenerPedido(String id) {
        try {
            // Construir la solicitud HTTP GET
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(API_URL + "/" + id)) // URL del endpoint con el ID del pedido
                    .header("Content-Type", "application/json")
                    .GET()
                    .build();

            // Enviar la solicitud
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // Manejar la respuesta
            if (response.statusCode() == 200) {
                // Convertir el JSON de respuesta en un objeto Pedido
                return objectMapper.readValue(response.body(), Pedido.class);
            } else if (response.statusCode() == 404) {
                System.err.println("Pedido no encontrado: " + id);
            } else {
                System.err.println("Error al consultar el pedido. Código: " + response.statusCode());
                System.err.println("Respuesta del servidor: " + response.body());
            }
        } catch (Exception e) {
            System.err.println("Error al consultar el pedido: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    // Clase auxiliar para convertir el estado a JSON
    private static class EstadoPedido {
        private String estado;

        public EstadoPedido(String estado) {
            this.estado = estado;
        }

        public String getEstado() {
            return estado;
        }

        public void setEstado(String estado) {
            this.estado = estado;
        }
    }

}