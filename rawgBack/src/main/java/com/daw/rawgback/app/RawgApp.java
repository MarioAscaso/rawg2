package com.daw.rawgback.app;

import com.daw.rawgback.domain.models.Game;
import com.daw.rawgback.domain.models.User;
import com.daw.rawgback.domain.repositories.GameRepository;
import com.daw.rawgback.domain.repositories.UserRepository;
import com.daw.rawgback.domain.services.ExternalGameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;

// @Component: Indica a Spring que esta clase es un "Bean" (un componente gestionado).
// Esto permite inyectarla luego en el Controlador usando @Autowired.
@Component
public class RawgApp {

    // Inyección de dependencias: Necesitamos acceder a la BD (Repositorios) y a la API externa (Service).
    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ExternalGameService externalGameService;

    // Caso de uso: BUSCAR JUEGOS
    // Simplemente delega la tarea al servicio externo que conecta con la API de RAWG.
    public String searchGames(String query) throws IOException {
        return externalGameService.fetchGamesFromApi(query);
    }

    // Caso de uso: OBTENER DETALLES
    // Pide la descripción detallada de un juego específico.
    public String getGameDetails(String gameId) {
        return externalGameService.fetchGameDetails(gameId);
    }

    // Caso de uso: GUARDAR FAVORITO
    // @Transactional es VITAL aquí. Significa: "O se hace al completo o no se hace nada".
    // Si guardamos el juego pero falla al asignarlo al usuario, se deshacen los cambios (rollback).
    @Transactional
    public void saveFavorite(String username, Game gameData) {
        // 1. Buscamos quién es el usuario que está guardando el favorito.
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

        // 2. Lógica de "Busca o Crea":
        // Antes de guardar el juego, miramos si ya existe en nuestra tabla 'games' por su nombre.
        // Si no existe (.orElseGet), lo guardamos nuevo. Si existe, usamos el que ya hay.
        // Esto evita tener el mismo juego repetido 50 veces en la BD.
        Game gameToSave = gameRepository.findByName(gameData.getName())
                .orElseGet(() -> gameRepository.save(gameData));

        // 3. Añadimos la relación:
        // Solo si el usuario no tiene ya ese juego en su lista, lo añadimos.
        if (!user.getFavorites().contains(gameToSave)) {
            user.getFavorites().add(gameToSave);
            userRepository.save(user); // Al guardar el usuario, JPA actualiza la tabla intermedia 'users_favorites'.
        }
    }

    // Caso de uso: LISTAR FAVORITOS
    public List<Game> getFavorites(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
        // Gracias a FetchType.EAGER en el modelo User, la lista de favoritos ya viene cargada.
        return user.getFavorites();
    }
}