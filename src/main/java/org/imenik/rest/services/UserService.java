package org.imenik.rest.services;

import org.imenik.rest.models.User;

public interface UserService {
    User getUser(String username);
}
