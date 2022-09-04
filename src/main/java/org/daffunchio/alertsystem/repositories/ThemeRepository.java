package org.daffunchio.alertsystem.repositories;

import org.daffunchio.alertsystem.exceptions.NotFoundException;
import org.daffunchio.alertsystem.models.Theme;

import java.util.List;

public interface ThemeRepository {
    Theme save(Theme theme) throws IllegalArgumentException;

    Theme getById(Long id) throws NotFoundException;

    List<Theme> getThemes();
}
