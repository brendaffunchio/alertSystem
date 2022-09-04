package org.daffunchio.alertsystem.repositories;

import org.daffunchio.alertsystem.exceptions.NotFoundException;
import org.daffunchio.alertsystem.models.AlertListener;

import java.util.List;

public interface AlertListenerRepository {
    AlertListener save(AlertListener alertListener) throws IllegalArgumentException;

    AlertListener getById(Long id) throws NotFoundException;

    List<AlertListener> getAlertListeners();
}
