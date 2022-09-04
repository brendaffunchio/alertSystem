package org.daffunchio.alertsystem.services.impl;

import lombok.NoArgsConstructor;
import org.daffunchio.alertsystem.exceptions.NotFoundException;
import org.daffunchio.alertsystem.models.Alert;
import org.daffunchio.alertsystem.models.AlertManager;
import org.daffunchio.alertsystem.models.Theme;
import org.daffunchio.alertsystem.models.User;
import org.daffunchio.alertsystem.repositories.AlertRepository;
import org.daffunchio.alertsystem.services.AlertManagerService;
import org.daffunchio.alertsystem.services.AlertService;
import org.daffunchio.alertsystem.services.ThemeService;
import org.daffunchio.alertsystem.services.UserService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@NoArgsConstructor
public class AlertServiceImpl implements AlertService {

    AlertRepository alertRepository;
    UserService userService;
    ThemeService themeService;
    AlertManagerService alertManagerService;

    public AlertServiceImpl(AlertRepository alertRepository, UserService userService,
                            ThemeService themeService, AlertManagerService alertManagerService) {
        this.alertRepository = alertRepository;
        this.themeService = themeService;
        this.userService = userService;
        this.alertManagerService = alertManagerService;
    }

    @Override
    public List<Alert> listAllAlerts() {
        return alertRepository.getAlerts();
    }

    @Override
    public Alert registerAlert(String title) {
        if (title == null) throw new IllegalArgumentException("Title is null");
        Alert alert = new Alert(title);
        return alertRepository.save(alert);
    }

    @Override
    public void sendAlert(String title, Long themeId) throws NotFoundException, IllegalArgumentException {
        Theme theme = themeService.getThemeById(themeId);
        AlertManager alertManager = alertManagerService.getAlertManager(theme);

        alertManager.notify(this.registerAlert(title));
    }

    @Override
    public void sendAlertToUser(String title, Long themeId, Long userId) throws NotFoundException {
        Theme theme = themeService.getThemeById(themeId);
        AlertManager alertManager = alertManagerService.getAlertManager(theme);

        alertManager.notify(this.registerAlert(title), userId);
    }

    @Override
    public Alert getAlertById(Long id) throws NotFoundException {
        return alertRepository.getById(id);

    }

    @Override
    public List<Alert> listUserUnReadAlertsOrderByExpirationDate(String username) throws NotFoundException {

        User user = userService.getUserByUsername(username);
        List<Alert> unReads = new ArrayList<>();
        for (Alert alert : user.getAlerts()
        ) {

            if (!alert.isRead() && alert.getExpiration_date().isAfter(LocalDate.now())) {
                unReads.add(alert);
            }

        }
        unReads.sort(Comparator.comparing(Alert::getExpiration_date).reversed());
        return unReads;
    }

    @Override
    public List<Alert> listThemeAlertsOrderByExpirationDate(Long idTheme) throws NotFoundException {

        Theme theme = themeService.getThemeById(idTheme);
        List<Alert> alerts = alertManagerService.getAlertManager(theme).getNotExpiredAlerts();
        alerts.sort(Comparator.comparing(Alert::getExpiration_date).reversed());
        return alerts;
    }


}
