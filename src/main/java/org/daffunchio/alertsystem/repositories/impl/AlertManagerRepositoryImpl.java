package org.daffunchio.alertsystem.repositories.impl;

import org.daffunchio.alertsystem.exceptions.NotFoundException;
import org.daffunchio.alertsystem.models.AlertManager;
import org.daffunchio.alertsystem.models.Theme;
import org.daffunchio.alertsystem.repositories.AlertManagerRepository;

import java.util.ArrayList;
import java.util.List;


public class AlertManagerRepositoryImpl implements AlertManagerRepository {
    List<AlertManager> alertManagers = new ArrayList<>();

    @Override
    public AlertManager saveAlertManager(AlertManager alertManager) throws IllegalArgumentException {
        if (alertManager == null) throw new IllegalArgumentException("Alert manager is null");
        this.alertManagers.add(alertManager);
        return alertManager;
    }

    @Override
    public AlertManager getAlertManager(Theme theme) throws IllegalArgumentException, NotFoundException {

        if (theme == null) throw new IllegalArgumentException("Theme is null");
        for (AlertManager alertManager : this.alertManagers) {

            if (alertManager.getTheme().getId().equals(theme.getId())) {
                return alertManager;
            }
        }
        throw new NotFoundException("Alert Manager was not found");
    }

    @Override
    public List<AlertManager> getAlertManagers() {
        return this.alertManagers;
    }

}
