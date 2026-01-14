package com.daw.rawgback.app;

import com.daw.rawgback.domain.models.Game;
import com.daw.rawgback.domain.models.User;
import com.daw.rawgback.infrastructure.repositories.JpaGameRepository;
import com.daw.rawgback.infrastructure.repositories.JpaUserRepository;
import com.daw.rawgback.domain.services.ExternalGameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import com.daw.rawgback.domain.repositories.GameRepository; // Asegúrate de importar esto
import com.daw.rawgback.domain.repositories.UserRepository; // <--- Asegúrate de importar esto

import java.io.IOException;
import java.util.List;

@Component
public class RawgApp {

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private UserRepository userRepository; // <--- CAMBIO: Usamos la interfaz del dominio (antes era JpaUserRepository)

    @Autowired
    private ExternalGameService externalGameService;

    public String searchGames(String query) throws IOException {
        return externalGameService.fetchGamesFromApi(query);
    }

    @Transactional // Importante para que guarde las relaciones
    public void saveFavorite(String username, Game gameData) {
        // 1. Buscamos al usuario
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

        // 2. Comprobamos si el juego ya existe en BD para no duplicarlo
        Game gameToSave = gameRepository.findByName(gameData.getName())
                .orElseGet(() -> gameRepository.save(gameData)); // Si no existe, lo crea

        // 3. Añadimos el juego a la lista del usuario (si no lo tiene ya)
        if (!user.getFavorites().contains(gameToSave)) {
            user.getFavorites().add(gameToSave);
            userRepository.save(user); // Al guardar el usuario, se guarda la relación en la tabla intermedia
        }
    }

    public List<Game> getFavorites(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
        return user.getFavorites();
    }

    public String getGameDetails(String gameId) {
        return externalGameService.fetchGameDetails(gameId);
    }
}