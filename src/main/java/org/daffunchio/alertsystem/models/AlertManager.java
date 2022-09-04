package org.daffunchio.alertsystem.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.daffunchio.alertsystem.services.AlertListenerService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
public abstract class AlertManager {

    protected Theme theme;
    private AlertListenerService alertListenerService;
    protected List<Alert> alerts;

    public AlertManager(Theme theme, AlertListenerService alertListenerService) {
        this.theme = theme;
        this.alertListenerService = alertListenerService;
        this.alerts = new ArrayList<>();
    }

    public abstract void notify(Alert alert);

    public abstract void notify(Alert alert, Long userId);

    public abstract void subscribe(User user) throws IllegalStateException;

    public abstract void unsubscribe(User user);

    protected Alert copyAlert(Alert alert) {
        Alert copy = new Alert();
        copy.setTitle(alert.getTitle());
        copy.setExpiration_date(alert.getExpiration_date());
        return copy;
    }

    protected void addAlert(Alert alert) {
        this.alerts.add(alert);
    }

    public List<Alert> getNotExpiredAlerts() {

        List<Alert> alerts = this.alerts.stream()
                .filter(alert -> alert.getExpiration_date().isAfter(LocalDate.now()))
                .collect(Collectors.toList());
        return alerts;
    }
}
