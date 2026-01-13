package com.daw.rawgback.domain.repositories;

import com.daw.rawgback.domain.models.Game;
import java.util.List;
import java.util.Optional; // <--- Importante

public interface GameRepository {
    Game save(Game game);
    List<Game> findAll();
    void deleteById(Long id);

    // AÑADE ESTO para que RawgApp pueda usarlo sin llamar a JpaGameRepository
    Optional<Game> findByName(String name);
}