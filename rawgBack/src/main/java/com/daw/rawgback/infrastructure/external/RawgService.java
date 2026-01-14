package com.daw.rawgback.infrastructure.external;

import com.daw.rawgback.domain.services.ExternalGameService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
public class RawgService implements ExternalGameService {

    // Leemos la API KEY del fichero application.properties para no escribirla en el código.
    @Value("${rawg.api.key}")
    private String apiKey;

    @Value("${rawg.api.url}")
    private String apiUrl;

    // Obtener lista de juegos (búsqueda o populares)
    @Override
    public String fetchGamesFromApi(String search) {
        try {
            String url = apiUrl + "?key=" + apiKey;

            if (search != null && !search.trim().isEmpty() && !search.equals("null")) {
                // Si hay búsqueda, añadimos &search=...
                String encodedSearch = search.trim().replace(" ", "%20"); // Codificar espacios
                url += "&search=" + encodedSearch;
            } else {
                // Si no, pedimos los mejores ordenados por metacritic
                url += "&ordering=-metacritic&page_size=20";
            }

            return makeRequest(url);

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return "{\"error\": \"Error interno al conectar con RAWG\"}";
        }
    }

    // Obtener detalles de un juego concreto
    @Override
    public String fetchGameDetails(String gameId) {
        try {
            // URL para detalle: /games/{id}
            String url = apiUrl + "/" + gameId + "?key=" + apiKey;
            return makeRequest(url);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return "{\"error\": \"Error al obtener detalles\"}";
        }
    }

    // Metodo privado para evitar repetir el código de HttpClient
    private String makeRequest(String url) throws IOException, InterruptedException {
        // Cliente HTTP nativo de Java (desde Java 11)
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        // Enviamos petición y devolvemos el cuerpo (Body) como String (JSON)
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }
}