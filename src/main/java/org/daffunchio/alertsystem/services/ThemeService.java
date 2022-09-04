package org.daffunchio.alertsystem.services;

import org.daffunchio.alertsystem.exceptions.NotFoundException;
import org.daffunchio.alertsystem.models.Theme;

import java.util.List;

public interface ThemeService {
    List<Theme> listAllThemes();

    Theme registerTheme(String title, String description, boolean urgent) throws IllegalStateException, IllegalArgumentException;

    Theme getThemeById(Long id) throws NotFoundException;

    List<Theme> listUrgentThemes();
}
