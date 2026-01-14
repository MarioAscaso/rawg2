package com.daw.rawgback.domain.repositories;

import com.daw.rawgback.domain.models.Game;
import java.util.List;
import java.util.Optional;

public interface GameRepository {
    Game save(Game game);
    List<Game> findAll();
    void deleteById(Long id);

    // Metodo clave para no duplicar juegos: Buscar si ya existe por nombre.
    Optional<Game> findByName(String name);
}