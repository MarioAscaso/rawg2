package com.daw.rawgback.infrastructure.external;

import com.daw.rawgback.domain.models.User;
import com.daw.rawgback.domain.services.InternalUserService;
import com.daw.rawgback.infrastructure.repositories.JpaUserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService implements InternalUserService {

    private final JpaUserRepository jpaUserRepository;

    public UserService(JpaUserRepository jpaUserRepository) {
        this.jpaUserRepository = jpaUserRepository;
    }

    @Override
    public String fetchUsersFromDataBase(String search) {
        Optional<User> userOptional = jpaUserRepository.findByUsername(search);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            return "Usuario encontrado: " + user.getUsername() + " (ID: " + user.getId() + ")";
        } else {
            return "El usuario '" + search + "' no existe en la base de datos.";
        }
    }
}