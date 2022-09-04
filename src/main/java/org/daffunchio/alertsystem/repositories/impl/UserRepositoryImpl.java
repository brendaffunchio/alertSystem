package org.daffunchio.alertsystem.repositories.impl;

import org.daffunchio.alertsystem.exceptions.NotFoundException;
import org.daffunchio.alertsystem.models.User;
import org.daffunchio.alertsystem.repositories.UserRepository;

import java.util.ArrayList;
import java.util.List;


public class UserRepositoryImpl implements UserRepository {


    List<User> users = new ArrayList<>();

    @Override
    public User save(User user) throws IllegalArgumentException {
        if (user == null) throw new IllegalArgumentException("User is null");
        this.users.add(user);
        return user;
    }

    @Override
    public User getByUsername(String username) throws NotFoundException {

        for (User user : this.users) {

            if (user.getUsername().equals(username)) {
                return user;
            }

        }
        throw new NotFoundException("User was not found");
    }

    @Override
    public List<User> getUsers() {
        return this.users;
    }
}
