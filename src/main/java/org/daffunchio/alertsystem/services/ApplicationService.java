package org.daffunchio.alertsystem.services;

public interface ApplicationService {
    void registerUser();

    void createTheme();

    void subscribeUserToTheme();

    void listUserUnReadAlertsOrderByExpirationDate();

    void sendAlert();

    void sendAlertToUser();

    void listAlertsForOneTheme(Long themeId);

    void listAlertsForOneTheme();

    void markAlertAsRead();

    void listUrgentThemes();

    void listAllThemes();

    void listUsers();

    public void listAlertsForAllThemes();
}
