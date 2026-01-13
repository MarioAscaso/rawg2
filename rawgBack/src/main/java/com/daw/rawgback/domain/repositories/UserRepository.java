package com.daw.rawgback.domain.repositories;

import com.daw.rawgback.domain.models.User;

import java.util.List;

public interface UserRepository {
    User save(User user);
    List<User> findAll();
    void deleteByUsername(Long id);
}
