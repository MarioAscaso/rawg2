package com.daw.rawgback.domain.repositories;

import com.daw.rawgback.domain.models.Game;

import java.util.List;

public interface GameRepository {
    Game save(Game game);
    List<Game> findAll();
    void deleteById(long id);
}
