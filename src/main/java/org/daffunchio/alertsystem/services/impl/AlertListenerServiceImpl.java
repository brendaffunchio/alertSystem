package org.daffunchio.alertsystem.services.impl;

import lombok.NoArgsConstructor;
import org.daffunchio.alertsystem.exceptions.NotFoundException;
import org.daffunchio.alertsystem.models.AlertListener;
import org.daffunchio.alertsystem.models.Theme;
import org.daffunchio.alertsystem.models.User;
import org.daffunchio.alertsystem.repositories.AlertListenerRepository;
import org.daffunchio.alertsystem.services.AlertListenerService;

import java.util.List;

@NoArgsConstructor
public class AlertListenerServiceImpl implements AlertListenerService {

    AlertListenerRepository alertListenerRepository;

    public AlertListenerServiceImpl(AlertListenerRepository alertListenerRepository) {
        this.alertListenerRepository = alertListenerRepository;
    }

    @Override
    public List<AlertListener> listAllAlerts() {
        return alertListenerRepository.getAlertListeners();
    }

    @Override
    public AlertListener createAlertListener(User user, Theme theme) throws IllegalArgumentException {
        if (user == null) throw new IllegalArgumentException("User is null");
        if (theme == null) throw new IllegalArgumentException("Theme is null");
        AlertListener alertListener = new AlertListener(user, theme);
        return alertListenerRepository.save(alertListener);
    }

    @Override
    public AlertListener getAlertListenerById(Long id) throws NotFoundException {
        return alertListenerRepository.getById(id);

    }

}
