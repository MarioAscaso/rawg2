package com.daw.rawgback.domain.services;

import java.io.IOException;

public interface InternalUserService {
    String fetchUsersFromDataBase(String username) throws IOException;
}
