package org.daffunchio.alertsystem.models;

import org.daffunchio.alertsystem.app.Application;
import org.daffunchio.alertsystem.services.AlertListenerService;


public class AlertManagerUrgent extends AlertManager {

    public AlertManagerUrgent(Theme theme, AlertListenerService alertListenerService) {
        super(theme, alertListenerService);

    }

    @Override
    public void notify(Alert alert) {

        for (AlertListener listener : Application.getContainer().getGlobalListeners()) {
            if (listener.getTheme().getId().equals(this.theme.getId()))
                listener.update(this.copyAlert(alert));
        }
        this.addAlert(alert);
    }

    @Override
    public void notify(Alert alert, Long userId) {

        for (AlertListener listener : Application.getContainer().getGlobalListeners()) {

            if (listener.getId().equals(userId) && listener.getTheme().getId().equals(this.theme.getId())) {

                listener.update(this.copyAlert(alert));
                return;
            }
        }
        this.addAlert(alert);
    }

    @Override
    public void subscribe(User user) throws IllegalStateException {
        for (AlertListener alertListener : user.getListeners()
        ) {
            if (this.theme.getId().equals(alertListener.getTheme().getId())) {
                throw new IllegalStateException("User " + user.getUsername() + " already subscribed to theme " + this.theme.getTitle());
            }
        }
        AlertListener listener = this.getAlertListenerService().createAlertListener(user, this.theme);
        user.addListener(listener);
        Application.getContainer().addListener(listener);
    }

    @Override
    public void unsubscribe(User user) {
        user.removeListener(this.getTheme().getId());
        Application.getContainer().removeListener(user.getId());
    }
}
