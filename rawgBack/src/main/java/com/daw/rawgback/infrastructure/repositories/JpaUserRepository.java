package com.daw.rawgback.infrastructure.repositories;

import com.daw.rawgback.domain.models.User;
import com.daw.rawgback.domain.repositories.UserRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

// Extendemos de JpaRepository (Spring Data) Y de nuestra interfaz de dominio (UserRepository).
// Esto cumple el contrato del dominio usando la magia de Spring Data.
public interface JpaUserRepository extends JpaRepository<User, Long>, UserRepository {
    // Spring implementa esto automáticamente basándose en el nombre del método.
    Optional<User> findByUsername(String username);
}