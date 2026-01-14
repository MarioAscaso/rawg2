package com.daw.rawgback.domain.services;
import java.io.IOException;

// Contrato para cualquier servicio que traiga juegos de fuera (API).
public interface ExternalGameService {
    String fetchGamesFromApi(String search) throws IOException;
    String fetchGameDetails(String gameId);
}