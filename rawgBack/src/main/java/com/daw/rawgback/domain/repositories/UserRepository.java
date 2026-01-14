package com.daw.rawgback.domain.repositories;

import com.daw.rawgback.domain.models.User;
import java.util.List;
import java.util.Optional;

public interface UserRepository {

    User save(User user);
    List<User> findAll();
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);

    // Para comprobar si existe antes de borrar
    boolean existsById(Long id);

    // Para borrar por ID
    void deleteById(Long id);
}