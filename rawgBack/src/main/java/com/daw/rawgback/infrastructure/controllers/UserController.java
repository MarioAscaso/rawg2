package com.daw.rawgback.infrastructure.controllers;

import com.daw.rawgback.app.UserApp;
import com.daw.rawgback.domain.models.User;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "*")
public class UserController {

    private final UserApp userApp;

    public UserController(UserApp userApp){
        this.userApp = userApp;
    }

    @PostMapping("/users")
    public void save(@RequestBody User user){
        userApp.saveUserInDB(user);
    }

    @GetMapping("/users")
    public String search(@RequestParam String search) throws IOException{
        return userApp.searchUser(search);
    }
}
