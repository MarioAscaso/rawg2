package com.daw.rawgback.infrastructure.external;

import com.daw.rawgback.domain.services.ExternalGameService;
import org.springframework.beans.factory.annotation.Value; // IMPORTANTE: Este import es clave
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
public class RawgService implements ExternalGameService {

    // Leemos los valores de tu application.properties
    @Value("${rawg.api.key}")
    private String apiKey;

    @Value("${rawg.api.url}")
    private String apiUrl;

    @Override
    public String fetchGamesFromApi(String search) {
        try {
            // 1. Construimos la URL base con la clave
            // OJO: Si apiKey es null aquí, fallará. Asegúrate de que application.properties está bien.
            String url = apiUrl + "?key=" + apiKey;

            // 2. Lógica: ¿Es búsqueda o portada?
            if (search != null && !search.trim().isEmpty() && !search.equals("null")) {
                // Búsqueda normal: Limpiamos espacios
                String encodedSearch = search.trim().replace(" ", "%20");
                url += "&search=" + encodedSearch;
            } else {
                // Portada (Inicio): Traemos los mejores juegos
                url += "&ordering=-metacritic&page_size=20";
            }

            // 3. Petición HTTP
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return response.body();

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return "{\"error\": \"Error interno al conectar con RAWG\"}";
        }
    }
}