import org.daffunchio.alertsystem.exceptions.NotFoundException;
import org.daffunchio.alertsystem.models.*;
import org.daffunchio.alertsystem.repositories.AlertRepository;
import org.daffunchio.alertsystem.repositories.impl.AlertRepositoryImpl;
import org.daffunchio.alertsystem.services.*;
import org.daffunchio.alertsystem.services.impl.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;

public class AlertServiceTests {

    private ThemeService themeService = mock(ThemeServiceImpl.class);
    private UserService userService = mock(UserServiceImpl.class);
    private AlertListenerService alertListenerService = mock(AlertListenerServiceImpl.class);
    private AlertManagerService alertManagerService = mock(AlertManagerServiceImpl.class);
    private AlertRepository alertRepository = mock(AlertRepositoryImpl.class);
    private AlertService alertService = new AlertServiceImpl(alertRepository, userService, themeService, alertManagerService);

    @Nested
    class TestsSendAlert {
        @Test
        @DisplayName("valid case")
        public void testSendAlert() {
            Theme theme = createTheme();
            AlertManager alertManager = createAlertManager(theme);
            Alert alert = createAlert();
            alertManager.getAlerts().add(alert);

            Mockito.when(themeService.getThemeById(Mockito.any())).thenReturn(theme);
            Mockito.when(alertManagerService.getAlertManager(Mockito.any())).thenReturn(alertManager);
            Mockito.when(alertRepository.save(Mockito.any())).thenReturn(alert);

            assertDoesNotThrow(
                    () -> {
                        alertService.sendAlert(alert.getTitle(), theme.getId());

                    }, "The alertService did not throw any exception.\"");

            Mockito.verify(alertRepository).save(Mockito.any());
        }

        @Test
        @DisplayName("Service sendAlert throws NotFoundException when theme is null")
        public void testSendAlertThrowsNotFoundExceptionWhenThemeIsNull() {
            Theme theme = createTheme();
            AlertManager alertManager = createAlertManager(theme);
            Alert alert = createAlert();
            alertManager.getAlerts().add(alert);

            Mockito.when(themeService.getThemeById(Mockito.any())).thenThrow(new NotFoundException());
            assertThrows(NotFoundException.class,
                    () -> {
                        alertService.sendAlert(alert.getTitle(), theme.getId());

                    }, "Expected exception thrown.\"");

            Mockito.verify(alertRepository, never()).save(Mockito.any());
        }

        @Test
        @DisplayName("Service sendAlert throws NotFoundException when alertManager is null")
        public void testSendAlertThrowsNotFoundExceptionWhenAlertManagerIsNull() {
            Theme theme = createTheme();
            AlertManager alertManager = createAlertManager(theme);
            Alert alert = createAlert();
            alertManager.getAlerts().add(alert);

            Mockito.when(themeService.getThemeById(Mockito.any())).thenReturn(theme);
            Mockito.when(alertManagerService.getAlertManager(Mockito.any())).thenThrow(new NotFoundException());


            assertThrows(NotFoundException.class,
                    () -> {
                        alertService.sendAlert(alert.getTitle(), theme.getId());

                    }, "Expected exception thrown.\"");

            Mockito.verify(alertRepository, never()).save(Mockito.any());
        }
    }

    @Nested
    class TestsSendAlertToUser {
        @Test
        @DisplayName("valid case")
        public void testSendAlertToUser() {
            Theme theme = createTheme();
            AlertManager alertManager = createAlertManager(theme);
            User user = createUser();
            Alert alert = createAlert();
            user.addAlert(alert);

            Mockito.when(themeService.getThemeById(Mockito.any())).thenReturn(theme);
            Mockito.when(alertManagerService.getAlertManager(Mockito.any())).thenReturn(alertManager);
            Mockito.when(alertRepository.save(Mockito.any())).thenReturn(alert);

            assertDoesNotThrow(
                    () -> {
                        alertService.sendAlertToUser(alert.getTitle(), theme.getId(), user.getId());


                    }, "The alertService did not throw any exception.\"");


            Mockito.verify(alertRepository).save(Mockito.any());
        }

        @Test
        @DisplayName("Service sendAlertToUser throws NotFoundException when theme is null")
        public void testSendAlertToUserThrowsNotFoundExceptionWhenThemeIsNull() {
            Theme theme = createTheme();
            User user = createUser();
            Alert alert = createAlert();
            user.addAlert(alert);

            Mockito.when(themeService.getThemeById(Mockito.any())).thenThrow(new NotFoundException());

            assertThrows(NotFoundException.class,
                    () -> {
                        alertService.sendAlertToUser(alert.getTitle(), theme.getId(), user.getId());


                    }, "Expected exception thrown.\"");


            Mockito.verify(alertRepository, never()).save(Mockito.any());
        }

        @Test
        @DisplayName("Service sendAlertToUser throws NotFoundException when alertManager is null")
        public void testSendAlertToUserThrowsNotFoundExceptionWhenAlertManagerIsNull() {
            Theme theme = createTheme();
            User user = createUser();
            Alert alert = createAlert();
            user.addAlert(alert);

            Mockito.when(themeService.getThemeById(Mockito.any())).thenReturn(theme);
            Mockito.when(alertManagerService.getAlertManager(Mockito.any())).thenThrow(new NotFoundException());

            assertThrows(NotFoundException.class,
                    () -> {
                        alertService.sendAlertToUser(alert.getTitle(), theme.getId(), user.getId());


                    }, "Expected exception thrown.\"");

            Mockito.verify(alertRepository, never()).save(Mockito.any());
        }

        @Test
        @DisplayName("Service sendAlertToUser throws IllegalArgumentException when alert is null")
        public void testSendAlertToUserThrowsIllegalArgumentExceptionWhenAlertIsNull() {
            Theme theme = createTheme();
            User user = createUser();
            AlertManager alertManager = createAlertManager(theme);
            Alert alert = createAlert();
            user.addAlert(alert);

            Mockito.when(themeService.getThemeById(Mockito.any())).thenReturn(theme);
            Mockito.when(alertManagerService.getAlertManager(Mockito.any())).thenReturn(alertManager);
            Mockito.when(alertRepository.save(Mockito.any())).thenThrow(new IllegalArgumentException());

            assertThrows(IllegalArgumentException.class,
                    () -> {
                        alertService.sendAlertToUser(alert.getTitle(), theme.getId(), user.getId());


                    }, "Expected exception thrown.\"");

            Mockito.verify(alertRepository).save(Mockito.any());
        }

    }

    @Nested
    class TestsListUserUnreadAlertOrderByExpirationDate {
        @Test
        @DisplayName("valid case")
        public void testListUserUnreadAlertsOrderByExpirationDate() {

            User user = createUser();

            Mockito.when(userService.getUserByUsername(Mockito.any())).thenReturn(user);


            List<Alert> result = alertService.listUserUnReadAlertsOrderByExpirationDate(user.getUsername());

            assertEquals(user.getAlerts().size(), result.size());

            assertTrue(checkListIsOrderedByExpirationDate(result));
            for (int i = 0; i < result.size(); i++) {

                assertNotNull(result.get(i), "Object from result list is not null");

            }
        }

        @Test
        @DisplayName("Service listUserUnreadAlertsOrderByExpirationDate throws NotFoundException when user is null")
        public void testListUserUnreadAlertsOrderByExpirationDateThrowsNotFoundExceptionWhenUserIsNull() {

            User user = createUser();

            Mockito.when(userService.getUserByUsername(Mockito.any())).thenThrow(new NotFoundException());


            assertThrows(NotFoundException.class,
                    () -> {
                        alertService.listUserUnReadAlertsOrderByExpirationDate(user.getUsername());


                    }, "Expected exception thrown.\"");


        }
    }

    @Nested
    class TestsListThemeAlertsOrderByExpirationDate {
        @Test
        @DisplayName("Valid case ")
        public void testListThemeAlertsOrderByExpirationDate() {
            List<Alert> alerts = listAlerts();
            Theme theme = createTheme();
            AlertManager alertManager = mock(AlertManager.class);

            Mockito.when(themeService.getThemeById(Mockito.any())).thenReturn(theme);
            Mockito.when(alertManagerService.getAlertManager(Mockito.any())).thenReturn(alertManager);
            Mockito.when(alertManager.getNotExpiredAlerts()).thenReturn(alerts);

            List<Alert> result = alertService.listThemeAlertsOrderByExpirationDate(theme.getId());

            assertEquals(alerts.size(), result.size());
            assertTrue(checkListIsOrderedByExpirationDate(result));
            for (int i = 0; i < result.size(); i++) {

                assertNotNull(result.get(i), "Object from result list is not null");

            }

            Mockito.verify(alertManagerService).getAlertManager(Mockito.any());
        }

        @Test
        @DisplayName("Service listThemeAlertsOrderByExpirationDate throws NotFoundException when theme is null")
        public void testListThemeAlertsOrderByExpirationDateThrowsNotFoundExceptionWhenThemeIsNull() {

            Theme theme = createTheme();

            Mockito.when(themeService.getThemeById(Mockito.any())).thenThrow(new NotFoundException());
            assertThrows(NotFoundException.class,
                    () -> {
                        alertService.listThemeAlertsOrderByExpirationDate(theme.getId());


                    }, "Expected exception thrown.\"");

        }

        @Test
        @DisplayName("Service listThemeAlertsOrderByExpirationDate throws NotFoundException when alertManager is null")
        public void testListThemeAlertsOrderByExpirationDateThrowsNotFoundExceptionWhenAlertManagerIsNull() {

            Theme theme = createTheme();

            Mockito.when(themeService.getThemeById(Mockito.any())).thenReturn(theme);
            Mockito.when(alertManagerService.getAlertManager(Mockito.any())).thenThrow(new NotFoundException());
            assertThrows(NotFoundException.class,
                    () -> {
                        alertService.listThemeAlertsOrderByExpirationDate(theme.getId());


                    }, "Expected exception thrown.\"");

        }
    }

    private User createUser() {
        User user = new User("JuanLopez17", "123456", 11489563L);
        for (int i = 0; i < 4; i++) {
            user.addAlert(createAlert());
        }


        return user;
    }

    private Theme createTheme() {
        return new Theme("Theme test", "test", true);

    }

    private AlertManager createAlertManager(Theme theme) {

        AlertManager alertManager = null;
        if (theme.isUrgent()) {
            alertManager = new AlertManagerUrgent(theme, alertListenerService);

        } else {
            alertManager = new AlertManagerInformative(theme, alertListenerService);
        }
        return alertManager;
    }

    private Alert createAlert() {
        return new Alert("alert");

    }

    private List<Alert> listAlerts() {
        List<Alert> alerts = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            alerts.add(createAlert());
        }
        return alerts;
    }

    private boolean checkListIsOrderedByExpirationDate(List<Alert> alerts) {

        if (alerts.size() < 2) return true;
        for (int i = 0; i < alerts.size() - 2; i++) {

            if (alerts.get(i).getExpiration_date().compareTo(alerts.get(i + 1).getExpiration_date()) > 0) {
                return false;
            }

        }

        return true;
    }
}