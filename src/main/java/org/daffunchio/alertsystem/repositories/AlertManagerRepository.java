package org.daffunchio.alertsystem.repositories;

import org.daffunchio.alertsystem.exceptions.NotFoundException;
import org.daffunchio.alertsystem.models.AlertManager;
import org.daffunchio.alertsystem.models.Theme;

import java.util.List;

public interface AlertManagerRepository {

    AlertManager saveAlertManager(AlertManager alertManager) throws IllegalArgumentException;

    AlertManager getAlertManager(Theme theme) throws IllegalArgumentException, NotFoundException;

    List<AlertManager> getAlertManagers();

}
