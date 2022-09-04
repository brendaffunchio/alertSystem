package org.daffunchio.alertsystem.repositories.impl;

import org.daffunchio.alertsystem.exceptions.NotFoundException;
import org.daffunchio.alertsystem.models.Alert;
import org.daffunchio.alertsystem.repositories.AlertRepository;

import java.util.ArrayList;
import java.util.List;


public class AlertRepositoryImpl implements AlertRepository {
    List<Alert> alerts = new ArrayList<>();

    @Override
    public Alert save(Alert alert) throws IllegalArgumentException {
        if (alert == null) throw new IllegalArgumentException("Alert is null");
        this.alerts.add(alert);
        return alert;
    }

    @Override
    public Alert getById(Long id) throws NotFoundException {
        for (Alert alert : this.alerts) {

            if (alert.getId().equals(id)) {
                return alert;
            }
        }
        throw new NotFoundException("Alert was not found");

    }

    @Override
    public List<Alert> getAlerts() {
        return this.alerts;
    }

}
