package org.daffunchio.alertsystem.models;

import org.daffunchio.alertsystem.services.AlertListenerService;

import java.util.ArrayList;
import java.util.List;

public class AlertManagerInformative extends AlertManager {
    private List<AlertListener> listeners = new ArrayList<>();

    public AlertManagerInformative(Theme theme, AlertListenerService alertListenerService) {

        super(theme, alertListenerService);
    }

    @Override
    public void notify(Alert alert) {

        for (AlertListener listener : this.listeners) {

            listener.update(this.copyAlert(alert));

        }
        this.addAlert(alert);
    }

    @Override
    public void notify(Alert alert, Long userId) {

        for (AlertListener listener : this.listeners) {

            if (listener.getId().equals(userId)) {

                listener.update(this.copyAlert(alert));
                return;
            }
        }

        this.addAlert(alert);
    }


    public void addListener(AlertListener listener) {

        this.listeners.add(listener);
    }

    public void removeListener(Long listenerId) {
        if (listenerId != null) {

            for (int i = 0; i < this.listeners.size(); i++) {

                if (listenerId.equals(this.listeners.get(i).getId())) {

                    this.listeners.remove(i);
                    return;
                }
            }
        }

    }

    @Override
    public void subscribe(User user) throws IllegalStateException {

        for (AlertListener alertListener : user.getListeners()
        ) {
            if (this.theme.getId().equals(alertListener.getTheme().getId())) {

                throw new IllegalStateException("User already subscribed to theme " + this.theme.getTitle());
            }
        }
        AlertListener listener = this.getAlertListenerService().createAlertListener(user, this.theme);
        user.addListener(listener);
        this.addListener(listener);
    }

    @Override
    public void unsubscribe(User user) {
        user.removeListener(this.getTheme().getId());
        this.removeListener(user.getId());
    }
}
