import org.daffunchio.alertsystem.models.Theme;
import org.daffunchio.alertsystem.repositories.ThemeRepository;
import org.daffunchio.alertsystem.repositories.impl.ThemeRepositoryImpl;
import org.daffunchio.alertsystem.services.AlertManagerService;
import org.daffunchio.alertsystem.services.ThemeService;
import org.daffunchio.alertsystem.services.impl.AlertManagerServiceImpl;
import org.daffunchio.alertsystem.services.impl.ThemeServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

public class ThemeServiceTests {
    private AlertManagerService alertManagerService = mock(AlertManagerServiceImpl.class);
    private ThemeRepository themeRepository = mock(ThemeRepositoryImpl.class);
    private ThemeService themeService = new ThemeServiceImpl(themeRepository, alertManagerService);


    @Test
    @DisplayName("Valid case")
    void testCreateTheme() {
        Theme expected = createTheme();

        Mockito.when(themeRepository.save(Mockito.any())).thenReturn(expected);

        assertDoesNotThrow(
                () -> {
                    Theme result = themeService.registerTheme(expected.getTitle(), expected.getDescription(), expected.isUrgent());
                    assertNotNull(result, "The object is not null");
                    assertEquals(expected.getTitle(), result.getTitle());
                }, "The themeService did not throw any exception.\"");

        Mockito.verify(themeRepository).save(Mockito.any());


    }


    @Test
    @DisplayName("Service registerTheme throws IllegalArgumentException when arguments are null")
    void testCreateThemeThrowsIllegalArgumentExceptionWhenArgumentsAreNull() {

        Mockito.when(themeRepository.save(Mockito.any())).thenThrow(new IllegalArgumentException());
        assertThrows(
                IllegalArgumentException.class,
                () -> {
                    themeService.registerTheme(null, null, false);
                }
                , "Expected exception thrown"
        );

    }

    private Theme createTheme() {
        return new Theme("Theme test", "test", true);

    }


}


