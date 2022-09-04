package org.daffunchio.alertsystem.services;

import org.daffunchio.alertsystem.exceptions.NotFoundException;
import org.daffunchio.alertsystem.models.AlertManager;
import org.daffunchio.alertsystem.models.Theme;

import java.util.List;

public interface AlertManagerService {
    void createAlertManager(Theme theme) throws IllegalArgumentException, IllegalStateException;

    AlertManager getAlertManager(Theme theme) throws NotFoundException, IllegalArgumentException;

    List<AlertManager> listAlertsManagers();

    List<AlertManager> listUrgentAlertsManagers();
}
