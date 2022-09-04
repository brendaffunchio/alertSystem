
import org.daffunchio.alertsystem.exceptions.NotFoundException;
import org.daffunchio.alertsystem.models.*;
import org.daffunchio.alertsystem.repositories.UserRepository;
import org.daffunchio.alertsystem.repositories.impl.UserRepositoryImpl;
import org.daffunchio.alertsystem.services.AlertListenerService;
import org.daffunchio.alertsystem.services.AlertManagerService;
import org.daffunchio.alertsystem.services.UserService;
import org.daffunchio.alertsystem.services.impl.AlertListenerServiceImpl;
import org.daffunchio.alertsystem.services.impl.UserServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;

public class UserServiceTests {

    private UserRepository userRepository = mock(UserRepositoryImpl.class);
    private AlertListenerService alertListenerService = mock(AlertListenerServiceImpl.class);
    private AlertManagerService alertManagerService = mock(AlertManagerService.class);
    private UserService userService = new UserServiceImpl(userRepository, alertManagerService);


    @Nested
    class TestsCreateUser {

        @Test
        @DisplayName("Valid case")
        void testRegisterUser() {
            User expected = createUser();

            Mockito.when(userRepository.save(Mockito.any())).thenReturn(expected);

            assertDoesNotThrow(
                    () -> {
                        User result = userService.registerUser(expected.getUsername(), expected.getPassword(), expected.getPhone());
                        assertNotNull(result, "The object is not null");
                        assertEquals(expected.getUsername(), result.getUsername());
                    }, "The userService did not throw any exception.\"");


            Mockito.verify(userRepository).save(Mockito.any());

            Mockito.verify(alertManagerService).listUrgentAlertsManagers();

        }

        @Test
        @DisplayName("Service registerUser throws IllegalArgumentException when arguments are null")
        void testRegisterUserThrowsIllegalArgumentExceptionWhenArgumentsAreNull() {

            Mockito.when(userRepository.save(Mockito.any())).thenThrow(new IllegalArgumentException());

            assertThrows(
                    IllegalArgumentException.class,
                    () -> {
                        userService.registerUser(null, null, null);
                    }
                    , "Expected exception thrown"
            );

            Mockito.verify(alertManagerService, never()).listUrgentAlertsManagers();
        }
    }

    @Nested
    class TestsMarkAlertAsRead {
        @Test
        @DisplayName("Valid case")
        public void testMarkAlertAsRead() {
            User user = createUser();
            Alert alert = createAlert();
            user.addAlert(alert);

            Mockito.when(userRepository.getByUsername(Mockito.any())).thenReturn(user);

            assertDoesNotThrow(
                    () -> {
                        userService.markAlertAsRead(alert.getId(), user.getUsername());

                    }, "The userService did not throw any exception.\"");

            Mockito.verify(userRepository).getByUsername(user.getUsername());


        }

        @Test
        @DisplayName("Service markAlertAsRead throws NotFoundException when user was not found")
        public void testMarkAlertAsReadThrowsNotFoundExceptionWhenTheUserWasNotFound() {
            String username = "userTest";
            Long alertId = 1L;

            Mockito.when(userRepository.getByUsername(Mockito.any())).thenThrow(new NotFoundException());
            assertThrows(
                    NotFoundException.class,
                    () -> {
                        userService.markAlertAsRead(alertId, username);
                    }
                    , "Expected exception thrown"
            );

            Mockito.verify(userRepository).getByUsername(username);

        }

        @Test
        @DisplayName("Service markAlertAsRead throws IllegalStateException because the alert was previously marked")
        public void testMarkAlertAsReadThrowsIllegalStateExceptionBecauseItWasPreviouslyMarked() {
            User user = createUser();
            Alert alert = createAlert();
            user.addAlert(alert);
            alert.setRead(true);

            Mockito.when(userRepository.getByUsername(Mockito.any())).thenReturn(user);

            assertThrows(
                    IllegalStateException.class,
                    () -> {
                        userService.markAlertAsRead(alert.getId(), user.getUsername());
                    }
                    , "Expected exception thrown"
            );

            Mockito.verify(userRepository).getByUsername(user.getUsername());

        }
    }

    private User createUser() {
        return new User("JuanLopez17", "123456", 11489563L);

    }

    private Theme createTheme() {
        return new Theme("Theme test", "test", true);

    }

    private Alert createAlert() {
        Alert alert = new Alert();
        alert.setTitle("Test alert");
        return alert;
    }

    private AlertManager createAlertManagerUrgent() {
        return new AlertManagerUrgent(createTheme(), alertListenerService);

    }


}
