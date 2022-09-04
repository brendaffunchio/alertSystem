package org.daffunchio.alertsystem.app;

import lombok.Getter;
import org.daffunchio.alertsystem.services.ScannerSingleton;

import java.util.Scanner;

@Getter
public class Application {

    private static Container container;
    private final Scanner scanner = ScannerSingleton.getScanner();
    private final int LIST_ALL_THEMES = 1;
    private final int LIST_URGENT_THEMES = 2;
    private final int LIST_USERS = 3;
    private final int CREATE_THEME = 4;
    private final int CREATE_USERS = 5;
    private final int LIST_UNREAD_ALERTS_USER = 6;
    private final int LIST_ALERTS_FOR_ONE_THEME = 7;
    private final int LIST_ALERTS_FOR_ALL_THEMES = 8;
    private final int CHANGE_ALERT_TO_READ = 9;

    private final int SUBSCRIBE_USER_TO_THEME = 10;
    private final int SEND_ALERT_TO_USER = 11;

    private final int SEND_URGENT_ALERT = 12;
    private final int SEND_THEME_ALERT = 13;
    private final int EXIT = 14;

    public static Container getContainer() {
        if (container == null) {
            container = new Container();
        }
        return container;
    }

    public void run() {
        Initializer.initializer();
        int chosenOption = 0;

        while (chosenOption != this.EXIT) {

            chosenOption = getOptionByMenu();
            this.scanner.nextLine();

            switch (chosenOption) {

                case LIST_ALL_THEMES:
                    this.container.getApplicationService().listAllThemes();
                    break;
                case LIST_USERS:
                    this.container.getApplicationService().listUsers();
                    break;
                case CREATE_THEME:
                    this.container.getApplicationService().createTheme();
                    break;
                case CREATE_USERS:
                    this.container.getApplicationService().registerUser();
                    break;
                case LIST_UNREAD_ALERTS_USER:
                    this.container.getApplicationService().listUserUnReadAlertsOrderByExpirationDate();
                    break;
                case LIST_ALERTS_FOR_ONE_THEME:
                    this.container.getApplicationService().listAlertsForOneTheme();
                    break;
                case CHANGE_ALERT_TO_READ:
                    this.container.getApplicationService().markAlertAsRead();
                    break;
                case SEND_ALERT_TO_USER:
                    this.container.getApplicationService().sendAlertToUser();
                    break;
                case LIST_URGENT_THEMES:
                    this.container.getApplicationService().listUrgentThemes();
                    break;
                case SEND_URGENT_ALERT:
                case SEND_THEME_ALERT:
                    this.container.getApplicationService().sendAlert();
                    break;
                case SUBSCRIBE_USER_TO_THEME:
                    this.container.getApplicationService().subscribeUserToTheme();
                    break;
                case LIST_ALERTS_FOR_ALL_THEMES:
                    this.container.getApplicationService().listAlertsForAllThemes();
                    break;
                case EXIT:
                    break;

            }
            pressKeyToContinue();
        }
        this.scanner.close();
    }

    private void pressKeyToContinue() {
        this.scanner.nextLine();
        System.out.println("\nPress Enter key to continue...");
        try {
            String key = this.scanner.nextLine();
        } catch (Exception e) {
        }
    }

    private int getOptionByMenu() {
        this.printMenu();
        return this.scanner.nextInt();

    }

    private void printMenu() {
        System.out.println("Menu:\n");
        System.out.println("1 - LIST ALL THEMES");
        System.out.println("2 - LIST URGENT THEMES");
        System.out.println("3 - LIST USERS");
        System.out.println("4 - CREATE THEME");
        System.out.println("5 - CREATE USER");
        System.out.println("6 - LIST UNREAD ALERTS FROM A USER");
        System.out.println("7 - LIST ALERTS FOR ONE THEME");
        System.out.println("8 - LIST ALERTS FOR ALL THEMES");
        System.out.println("9 - MARK USER ALERT AS READ");
        System.out.println("10 - SUBSCRIBE USER TO THEME");
        System.out.println("11 - SEND ALERT TO USER");
        System.out.println("12 - SEND URGENT ALERT TO USERS");
        System.out.println("13 - SEND THEME ALERT TO USERS");
        System.out.println("14 - EXIT");
        System.out.print("\nEnter an option: ");
    }


}
