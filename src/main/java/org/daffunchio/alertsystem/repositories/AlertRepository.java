package org.daffunchio.alertsystem.repositories;

import org.daffunchio.alertsystem.exceptions.NotFoundException;
import org.daffunchio.alertsystem.models.Alert;

import java.util.List;

public interface AlertRepository {
    Alert save(Alert alert) throws IllegalArgumentException;

    Alert getById(Long id) throws NotFoundException;

    List<Alert> getAlerts();
}
