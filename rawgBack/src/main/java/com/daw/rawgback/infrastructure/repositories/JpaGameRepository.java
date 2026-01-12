package com.daw.rawgback.infrastructure.repositories;

import com.daw.rawgback.domain.models.Game;
import com.daw.rawgback.domain.repositories.GameRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaGameRepository extends JpaRepository<Game, Long>, GameRepository {
}
