package com.daw.rawgback.domain.repositories;

import com.daw.rawgback.domain.models.User;
import java.util.List;
import java.util.Optional;

// Definimos las operaciones disponibles para Usuarios.
public interface UserRepository {
    // Guardar o actualizar
    User save(User user);

    // Obtener todos
    List<User> findAll();

    // Buscar por nombre (Optional por si no existe, evitar NullPointer)
    Optional<User> findByUsername(String username);

    // Buscar por email
    Optional<User> findByEmail(String email);

    // Verificar existencia por ID
    boolean existsById(Long id);

    // Borrar por ID
    void deleteById(Long id);
}