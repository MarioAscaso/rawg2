package com.daw.rawgback.domain.repositories;

import com.daw.rawgback.domain.models.User;
import java.util.List;
import java.util.Optional; // <--- NO OLVIDES ESTE IMPORT

public interface UserRepository {
    User save(User user);
    List<User> findAll();

    // Si tenías deleteByUsername cámbialo a deleteById si usas IDs, o déjalo si es tu lógica
    // void deleteByUsername(Long id);

    // AÑADE ESTO:
    Optional<User> findByUsername(String username);
}