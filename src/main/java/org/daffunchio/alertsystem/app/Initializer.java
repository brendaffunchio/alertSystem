package org.daffunchio.alertsystem.app;

import org.daffunchio.alertsystem.models.AlertManager;
import org.daffunchio.alertsystem.models.Theme;
import org.daffunchio.alertsystem.models.User;

public class Initializer {

    public static void initializer() {
        Theme theme1 = Application.getContainer().getThemeService().registerTheme("System Notification", "..", true);
        Theme theme2 = Application.getContainer().getThemeService().registerTheme("Account Notification", "..", true);
        Theme theme3 = Application.getContainer().getThemeService().registerTheme("Messages Notification", "..", true);
        Theme theme4 = Application.getContainer().getThemeService().registerTheme("Loreal Paris", "..", false);
        Theme theme5 = Application.getContainer().getThemeService().registerTheme("Adidas", "..", false);
        Theme theme6 = Application.getContainer().getThemeService().registerTheme("Pantene", "..", false);

        User user1 = Application.getContainer().getUserService().registerUser("JuanLopez17", "123456", 18569L);
        User user2 = Application.getContainer().getUserService().registerUser("RamonaPerez6", "123456", 18569L);
        User user3 = Application.getContainer().getUserService().registerUser("PepeAlvarez96", "123456", 18569L);
        User user4 = Application.getContainer().getUserService().registerUser("Bren17", "123456", 18569L);
        User user5 = Application.getContainer().getUserService().registerUser("JorgePerez89", "123456", 18569L);

        AlertManager alertManagerLorealPublishAPost = Application.getContainer().getAlertManagerService().getAlertManager(theme4);
        AlertManager alertManagerAdidasPresentsNewModel = Application.getContainer().getAlertManagerService().getAlertManager(theme5);
        AlertManager alertManagerPanteneSendMessage = Application.getContainer().getAlertManagerService().getAlertManager(theme6);
        alertManagerLorealPublishAPost.subscribe(user1);
        alertManagerLorealPublishAPost.subscribe(user2);
        alertManagerAdidasPresentsNewModel.subscribe(user3);
        alertManagerAdidasPresentsNewModel.subscribe(user5);
        alertManagerPanteneSendMessage.subscribe(user4);

        Application.getContainer().getAlertService().sendAlert("New design in the app", theme1.getId());
        Application.getContainer().getAlertService().sendAlert("New chats in the app", theme1.getId());
        Application.getContainer().getAlertService().sendAlert("Your password will expire", theme2.getId());
        Application.getContainer().getAlertService().sendAlert("Added a group chat room in the app", theme3.getId());
        Application.getContainer().getAlertService().sendAlertToUser("Loreal Paris published a new product", theme4.getId(), user1.getId());
        Application.getContainer().getAlertService().sendAlertToUser("Adidas launches a new model of shoes", theme5.getId(), user3.getId());
        Application.getContainer().getAlertService().sendAlertToUser("Pantene released a new shampoo", user4.getId(), theme6.getId());

    }
}
