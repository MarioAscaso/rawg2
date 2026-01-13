package com.daw.rawgback.infrastructure.repositories;

import com.daw.rawgback.domain.models.Game;
import com.daw.rawgback.domain.repositories.GameRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JpaGameRepository extends JpaRepository<Game, Long>, GameRepository {
    Optional<Game> findByName(String name);
}
