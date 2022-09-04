package org.daffunchio.alertsystem.repositories.impl;

import org.daffunchio.alertsystem.exceptions.NotFoundException;
import org.daffunchio.alertsystem.models.AlertListener;
import org.daffunchio.alertsystem.repositories.AlertListenerRepository;

import java.util.ArrayList;
import java.util.List;


public class AlertListenerRepositoryImpl implements AlertListenerRepository {

    List<AlertListener> alertListeners = new ArrayList<>();


    @Override
    public AlertListener save(AlertListener alertListener) throws IllegalArgumentException {
        if (alertListener == null) throw new IllegalArgumentException("Alert Listener is null");
        this.alertListeners.add(alertListener);
        return alertListener;
    }

    @Override
    public AlertListener getById(Long id) throws NotFoundException {
        for (AlertListener alertListener : this.alertListeners) {

            if (alertListener.getId().equals(id)) {
                return alertListener;
            }
        }
        throw new NotFoundException("Alert listener was not found");

    }

    @Override
    public List<AlertListener> getAlertListeners() {
        return this.alertListeners;
    }

}
