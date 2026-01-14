package com.daw.rawgback.domain.services;

import java.io.IOException;

public interface ExternalGameService {
    String fetchGamesFromApi(String search) throws IOException;
    String fetchGameDetails(String gameId);
}
