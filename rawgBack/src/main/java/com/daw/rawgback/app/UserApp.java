package com.daw.rawgback.app;

import com.daw.rawgback.domain.models.User;
import com.daw.rawgback.domain.repositories.UserRepository;
import com.daw.rawgback.domain.services.InternalUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public class UserApp {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private InternalUserService internalUserService;

    public String searchUser(String username) throws IOException{
        return internalUserService.fetchUsersFromDataBase(username);
    }

    public void saveUserInDB(User user){
        userRepository.save(user);
    }

    public List<User> getUsers(){
        return userRepository.findAll();
    }
}
