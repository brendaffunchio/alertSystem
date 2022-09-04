package org.daffunchio.alertsystem.services.impl;

import lombok.NoArgsConstructor;
import org.daffunchio.alertsystem.exceptions.NotFoundException;
import org.daffunchio.alertsystem.models.Alert;
import org.daffunchio.alertsystem.models.AlertManager;
import org.daffunchio.alertsystem.models.User;
import org.daffunchio.alertsystem.repositories.UserRepository;
import org.daffunchio.alertsystem.services.AlertManagerService;
import org.daffunchio.alertsystem.services.UserService;

import java.util.Comparator;
import java.util.List;

@NoArgsConstructor
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private AlertManagerService alertManagerService;

    public UserServiceImpl(UserRepository userRepository, AlertManagerService alertManagerService) {
        this.userRepository = userRepository;
        this.alertManagerService = alertManagerService;
    }

    @Override
    public List<User> listAllUsers() {
        return userRepository.getUsers();

    }

    @Override
    public List<Alert> listAlertsUser(String username) throws NotFoundException {
        User user = getUserByUsername(username);
        List<Alert> alerts = user.getAlerts();
        alerts.sort(Comparator.comparing(Alert::getExpiration_date).reversed());
        return alerts;
    }

    @Override
    public void markAlertAsRead(Long idAlert, String username) throws NotFoundException {
        User user = this.userRepository.getByUsername(username);

        for (Alert alert : user.getAlerts()
        ) {

            if (alert.getId().equals(idAlert)) {
                if (alert.isRead()) {
                    throw new IllegalStateException("The alert cannot be marked as read because it was previously marked ");
                }
                alert.setRead(true);
            }
        }
    }

    @Override
    public User registerUser(String username, String password, Long phone) throws IllegalArgumentException {
        if (username == null) throw new IllegalArgumentException("Username is null");
        if (username.isEmpty()) throw new IllegalArgumentException("Username is empty");
        if (password == null) throw new IllegalArgumentException("Password is null");
        if (password.isEmpty()) throw new IllegalArgumentException("Password is empty");
        if (phone == null) throw new IllegalArgumentException("Phone is null");

        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setPhone(phone);

        User saved = userRepository.save(user);
        for (AlertManager urgentAlertManager : alertManagerService.listUrgentAlertsManagers()) {
            urgentAlertManager.subscribe(user);

        }
        return saved;

    }

    @Override
    public User getUserByUsername(String username) throws NotFoundException {
        return userRepository.getByUsername(username);

    }
}
