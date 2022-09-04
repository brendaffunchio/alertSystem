package org.daffunchio.alertsystem.services;

import org.daffunchio.alertsystem.exceptions.NotFoundException;
import org.daffunchio.alertsystem.models.AlertListener;
import org.daffunchio.alertsystem.models.Theme;
import org.daffunchio.alertsystem.models.User;

import java.util.List;

public interface AlertListenerService {

    List<AlertListener> listAllAlerts();

    AlertListener createAlertListener(User user, Theme theme) throws IllegalArgumentException;

    AlertListener getAlertListenerById(Long id) throws NotFoundException;

}
