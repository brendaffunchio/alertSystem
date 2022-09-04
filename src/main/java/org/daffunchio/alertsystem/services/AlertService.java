package org.daffunchio.alertsystem.services;

import org.daffunchio.alertsystem.exceptions.NotFoundException;
import org.daffunchio.alertsystem.models.Alert;

import java.util.List;

public interface AlertService {
    List<Alert> listAllAlerts();

    Alert registerAlert(String title);

    void sendAlert(String title, Long themeId) throws NotFoundException;

    void sendAlertToUser(String title, Long themeId, Long userId) throws NotFoundException;

    Alert getAlertById(Long id) throws NotFoundException;

    List<Alert> listUserUnReadAlertsOrderByExpirationDate(String username) throws NotFoundException;

    List<Alert> listThemeAlertsOrderByExpirationDate(Long idTheme) throws NotFoundException;
}
