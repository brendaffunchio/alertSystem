package org.daffunchio.alertsystem.services.impl;

import org.daffunchio.alertsystem.app.Application;
import org.daffunchio.alertsystem.exceptions.NotFoundException;
import org.daffunchio.alertsystem.models.*;
import org.daffunchio.alertsystem.repositories.AlertManagerRepository;
import org.daffunchio.alertsystem.services.AlertListenerService;
import org.daffunchio.alertsystem.services.AlertManagerService;

import java.util.ArrayList;
import java.util.List;

public class AlertManagerServiceImpl implements AlertManagerService {

    private AlertManagerRepository alertManagerRepository;
    private AlertListenerService alertListenerService;

    public AlertManagerServiceImpl(AlertManagerRepository alertManagerRepository, AlertListenerService alertListenerService) {
        this.alertManagerRepository = alertManagerRepository;
        this.alertListenerService = alertListenerService;
    }

    @Override
    public void createAlertManager(Theme theme) throws IllegalArgumentException, IllegalStateException {
        if (theme == null) throw new IllegalArgumentException("Theme is null");
        if (theme.isUrgent()) {
            AlertManager alertManagerUrgent = new AlertManagerUrgent(theme, this.alertListenerService);
            this.alertManagerRepository.saveAlertManager(alertManagerUrgent);

            for (User user : Application.getContainer().getUserService().listAllUsers()) {
                alertManagerUrgent.subscribe(user);
            }

        } else {
            AlertManager alertManagerInformative = new AlertManagerInformative(theme, this.alertListenerService);
            alertManagerRepository.saveAlertManager(alertManagerInformative);
        }
    }

    @Override
    public AlertManager getAlertManager(Theme theme) throws NotFoundException, IllegalArgumentException {

        if (theme == null) throw new IllegalArgumentException("Theme is null");
        return this.alertManagerRepository.getAlertManager(theme);
    }

    @Override
    public List<AlertManager> listAlertsManagers() {
        return this.alertManagerRepository.getAlertManagers();
    }

    @Override
    public List<AlertManager> listUrgentAlertsManagers() {
        List<AlertManager> urgent = new ArrayList<>();
        for (AlertManager alertManager : this.alertManagerRepository.getAlertManagers()
        ) {
            if (alertManager.getTheme().isUrgent()) urgent.add(alertManager);
        }
        return urgent;
    }
}
