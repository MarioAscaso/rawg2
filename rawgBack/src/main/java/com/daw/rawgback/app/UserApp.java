package com.daw.rawgback.app;

import com.daw.rawgback.domain.models.User;
import com.daw.rawgback.domain.repositories.UserRepository;
import com.daw.rawgback.domain.services.InternalUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public class UserApp {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private InternalUserService internalUserService;

    // Necesitamos el codificador para no guardar contraseñas en texto plano.
    @Autowired
    private PasswordEncoder passwordEncoder;

    // Caso de uso: BUSCAR USUARIO (Interno)
    public String searchUser(String username) throws IOException{
        return internalUserService.fetchUsersFromDataBase(username);
    }

    // Caso de uso: REGISTRAR/GUARDAR USUARIO
    public void saveUserInDB(User user){
        // 1. SEGURIDAD: Nunca guardar la contraseña tal cual llega. La encriptamos.
        // Si un hacker entra en la BD, solo verá códigos raros, no las contraseñas reales.
        String encodedPass = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPass);

        // 2. Lógica de negocio: Si no se especifica rol, por defecto es USER (no Admin).
        if (user.getRole() == null || user.getRole().isEmpty()) {
            user.setRole("USER");
        }

        userRepository.save(user);
    }

    // Caso de uso: LISTAR TODOS (Para el panel de admin)
    public List<User> getUsers(){
        return userRepository.findAll();
    }
}