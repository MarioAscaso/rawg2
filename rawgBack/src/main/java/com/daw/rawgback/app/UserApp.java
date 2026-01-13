package com.daw.rawgback.app;

import com.daw.rawgback.domain.models.User;
import com.daw.rawgback.domain.repositories.UserRepository;
import com.daw.rawgback.domain.services.InternalUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder; // IMPORTANTE
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public class UserApp {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private InternalUserService internalUserService;

    @Autowired // Inyectamos el encriptador que definimos en SecurityConfig
    private PasswordEncoder passwordEncoder;

    public String searchUser(String username) throws IOException{
        return internalUserService.fetchUsersFromDataBase(username);
    }

    public void saveUserInDB(User user){
        // 1. ENCRIPTAMOS la contraseña antes de guardar
        String encodedPass = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPass);

        // 2. Asignamos rol por defecto si no viene
        if (user.getRole() == null || user.getRole().isEmpty()) {
            user.setRole("USER");
        }

        userRepository.save(user);
    }

    public List<User> getUsers(){
        return userRepository.findAll();
    }
}