package org.daffunchio.alertsystem.repositories;

import org.daffunchio.alertsystem.exceptions.NotFoundException;
import org.daffunchio.alertsystem.models.User;

import java.util.List;

public interface UserRepository {
    User save(User user) throws IllegalArgumentException;

    User getByUsername(String username) throws NotFoundException;

    List<User> getUsers();
}
