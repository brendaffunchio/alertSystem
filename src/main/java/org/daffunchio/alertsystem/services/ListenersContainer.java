package org.daffunchio.alertsystem.services;

import org.daffunchio.alertsystem.models.AlertListener;

import java.util.List;

public interface ListenersContainer {
    void addListener(AlertListener listener);

    List<AlertListener> getGlobalListeners();

    void removeListener(Long listenerId);
}
