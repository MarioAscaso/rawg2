package com.daw.rawgback.infrastructure.external;

import com.daw.rawgback.domain.services.ExternalGameService;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class RawgService implements ExternalGameService {

    private final OkHttpClient client = new OkHttpClient();

    @Value("${rawg.api.key}")
    private String apiKey;

    @Value("${rawg.api.url}")
    private String apiUrl;

    @Override
    public String fetchGamesFromApi(String search) {
        String url = apiUrl + "?key=" + apiKey + "&search=" + search;
        Request request = new Request.Builder().url(url).build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                return response.body().string();
            }
        } catch (Exception e) {
            // Logueamos el error pero no lanzamos excepción para que el servidor no de un 500
            System.out.println("Error de red con RAWG: " + e.getMessage());
        }

        // Devolvemos un JSON vacío que el Frontend pueda entender
        return "{\"results\": []}";
    }
}
