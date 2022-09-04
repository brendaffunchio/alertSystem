package org.daffunchio.alertsystem.services;

import org.daffunchio.alertsystem.exceptions.NotFoundException;
import org.daffunchio.alertsystem.models.Alert;
import org.daffunchio.alertsystem.models.User;

import java.util.List;

public interface UserService {

    List<User> listAllUsers();

    User registerUser(String username, String password, Long phone) throws IllegalArgumentException;

    User getUserByUsername(String username) throws NotFoundException;

    List<Alert> listAlertsUser(String username) throws NotFoundException;

    void markAlertAsRead(Long idAlert, String username) throws NotFoundException;


}
