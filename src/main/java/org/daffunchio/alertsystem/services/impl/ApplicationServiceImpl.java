package org.daffunchio.alertsystem.services.impl;

import lombok.NoArgsConstructor;
import org.daffunchio.alertsystem.exceptions.NotFoundException;
import org.daffunchio.alertsystem.models.Alert;
import org.daffunchio.alertsystem.models.AlertManager;
import org.daffunchio.alertsystem.models.Theme;
import org.daffunchio.alertsystem.models.User;
import org.daffunchio.alertsystem.services.*;

import java.util.List;
import java.util.Scanner;

@NoArgsConstructor
public class ApplicationServiceImpl implements ApplicationService {

    private final Scanner scanner = ScannerSingleton.getScanner();
    private String username;
    private String alertTitle;
    private Long themeId;

    private UserService userService;
    private ThemeService themeService;
    private AlertService alertService;
    private AlertManagerService alertManagerService;

    public ApplicationServiceImpl(UserService userService, ThemeService themeService,
                                  AlertService alertService, AlertManagerService alertManagerService) {
        this.userService = userService;
        this.themeService = themeService;
        this.alertManagerService = alertManagerService;
        this.alertService = alertService;
    }

    @Override
    public void registerUser() {
        System.out.print("Username: ");
        this.username = this.scanner.nextLine();
        System.out.print("Password: ");
        String password = this.scanner.nextLine();
        System.out.print("Phone: ");
        Long userPhone = this.scanner.nextLong();
        try {
            User user = this.userService.registerUser(username, password, userPhone);

            System.out.println("User created: " + "id: " + user.getId() + ",username: " + user.getUsername());
        } catch (IllegalStateException | IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void createTheme() {
        System.out.print("Title: ");
        String title = this.scanner.nextLine();
        System.out.print("Description: ");
        String description = this.scanner.nextLine();
        System.out.print("Is urgent(true or false): ");
        boolean urgent = this.scanner.nextBoolean();
        try {
            Theme theme = this.themeService.registerTheme(title, description, urgent);

            System.out.println("Theme created: " + "id: " + theme.getId() + ",title: " + theme.getTitle());
        } catch (IllegalStateException | IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

    }


    @Override
    public void subscribeUserToTheme() {
        System.out.print("Username: ");
        this.username = this.scanner.nextLine();
        System.out.print("Theme id: ");
        this.themeId = this.scanner.nextLong();
        try {
            User user = this.userService.getUserByUsername(username);
            Theme theme = this.themeService.getThemeById(themeId);
            AlertManager alertManager = this.alertManagerService.getAlertManager(theme);
            alertManager.subscribe(user);
            System.out.println("User " + user.getUsername() + "subscribed to " + theme.getTitle());
        } catch (NotFoundException | IllegalArgumentException | IllegalStateException e) {
            System.out.println(e.getMessage());
        }

    }

    @Override
    public void listUserUnReadAlertsOrderByExpirationDate() {
        System.out.print("Username: ");
        this.username = this.scanner.nextLine();
        try {
            List<Alert> alerts = this.alertService.listUserUnReadAlertsOrderByExpirationDate(username);
            if (alerts.isEmpty()) {
                System.out.println("There are no unread alerts");
            } else {
                for (Alert alert : alerts) {
                    System.out.println("Alert id: " + alert.getId() + ",title: " + alert.getTitle());
                }
            }
        } catch (NotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void sendAlert() {
        System.out.print("Alert title: ");
        this.alertTitle = this.scanner.nextLine();
        System.out.print("Theme id: ");
        this.themeId = this.scanner.nextLong();
        try {
            this.alertService.sendAlert(alertTitle, themeId);
        } catch (NotFoundException | IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void sendAlertToUser() {
        System.out.print("Alert title: ");
        this.alertTitle = this.scanner.nextLine();
        System.out.print("Theme id: ");
        this.themeId = this.scanner.nextLong();
        System.out.print("User id: ");
        Long userId = this.scanner.nextLong();
        try {
            this.alertService.sendAlertToUser(alertTitle, themeId, userId);
        } catch (NotFoundException | IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void listAlertsForOneTheme() {
        System.out.println("Theme id: ");
        this.themeId = this.scanner.nextLong();
        try {
            List<Alert> alerts = this.alertService.listThemeAlertsOrderByExpirationDate(themeId);
            if (alerts.isEmpty()) {
                System.out.println("There are no alerts for theme with id: " + themeId);
            } else {
                for (Alert alert : alerts) {

                    System.out.println("Alert id: " + alert.getId() + ",title: " + alert.getTitle());
                }
            }
        } catch (NotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void listAlertsForOneTheme(Long themeId) {
        try {
            List<Alert> alerts = this.alertService.listThemeAlertsOrderByExpirationDate(themeId);
            if (alerts.isEmpty()) {
                System.out.println("There are no alerts for theme with id: " + themeId);
            } else {
                for (Alert alert : alerts) {

                    System.out.println("Alert id: " + alert.getId() + ",title: " + alert.getTitle());
                }
            }
        } catch (NotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void markAlertAsRead() {
        System.out.print("Username: ");
        this.username = this.scanner.nextLine();
        System.out.print("Alert id: ");
        Long alertId = this.scanner.nextLong();
        try {
            this.userService.markAlertAsRead(alertId, username);
            System.out.println("Alert marked as read");
        } catch (NotFoundException | IllegalStateException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void listUrgentThemes() {
        List<Theme> themes = this.themeService.listUrgentThemes();
        if (themes.isEmpty()) {
            System.out.println("There are no urgent themes");
        } else {
            for (Theme theme : themes
            ) {
                System.out.println("Theme id: " + theme.getId() + ",title: " + theme.getTitle() + "\n");
            }
        }
    }

    @Override
    public void listAllThemes() {
        List<Theme> themes = this.themeService.listAllThemes();
        if (themes.isEmpty()) {
            System.out.println("There are no themes");
        } else {
            for (Theme theme : themes
            ) {
                System.out.println("Theme id: " + theme.getId() + ",title: " + theme.getTitle() + "\n");
            }
        }
    }

    @Override
    public void listUsers() {

        for (User user : this.userService.listAllUsers()) {
            System.out.println("User id: " + user.getId() + ",username: " + user.getUsername() + "\n");
        }


    }

    @Override
    public void listAlertsForAllThemes() {
        List<AlertManager> alertManagers = this.alertManagerService.listAlertsManagers();
        for (AlertManager alertManager : alertManagers) {
            if (alertManager.getTheme().isUrgent()) {

                System.out.println("\nTheme id: " + alertManager.getTheme().getId() + ",theme: " + alertManager.getTheme().getTitle() + ", URGENT");
                System.out.println("Alerts: ");
                listAlertsForOneTheme(alertManager.getTheme().getId());
            }
            if (!alertManager.getTheme().isUrgent()) {
                System.out.println("\nTheme id: " + alertManager.getTheme().getId() + ",theme: " + alertManager.getTheme().getTitle() + ", INFORMATIVE");
                System.out.println("Alerts: ");
                listAlertsForOneTheme(alertManager.getTheme().getId());

            }
        }
    }

}
