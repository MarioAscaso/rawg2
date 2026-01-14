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

    @Value("${rawg.api.key}")
    private String apiKey;

    @Value("${rawg.api.url}")
    private String apiUrl;

    @Override
    public String fetchGamesFromApi(String search) {
        try {
            // URL base
            String url = apiUrl + "?key=" + apiKey;

            // Lógica: ¿Es búsqueda o portada?
            if (search != null && !search.trim().isEmpty() && !search.equals("null")) {
                String encodedSearch = search.trim().replace(" ", "%20");
                url += "&search=" + encodedSearch;
            } else {
                url += "&ordering=-metacritic&page_size=20";
            }

            return makeRequest(url);

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return "{\"error\": \"Error interno al conectar con RAWG\"}";
        }
    }

    // === NUEVO MÉTODO: OBTENER DETALLES DE UN JUEGO ===
    @Override
    public String fetchGameDetails(String gameId) {
        try {
            // Construimos la URL específica: https://api.rawg.io/api/games/{id}?key={clave}
            String url = apiUrl + "/" + gameId + "?key=" + apiKey;

            return makeRequest(url);

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return "{\"error\": \"Error al obtener detalles del juego\"}";
        }
    }

    // Método auxiliar para no repetir código de HttpClient
    private String makeRequest(String url) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }
}