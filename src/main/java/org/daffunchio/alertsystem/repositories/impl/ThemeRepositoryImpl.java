package org.daffunchio.alertsystem.repositories.impl;

import org.daffunchio.alertsystem.exceptions.NotFoundException;
import org.daffunchio.alertsystem.models.Theme;
import org.daffunchio.alertsystem.repositories.ThemeRepository;

import java.util.ArrayList;
import java.util.List;

public class ThemeRepositoryImpl implements ThemeRepository {
    List<Theme> themes = new ArrayList<>();

    @Override
    public Theme save(Theme theme) throws IllegalArgumentException {
        if (theme == null) throw new IllegalArgumentException("Theme is null");
        this.themes.add(theme);
        return theme;
    }

    @Override
    public Theme getById(Long id) throws NotFoundException {
        for (Theme theme : this.themes
        ) {
            if (theme.getId().equals(id)) {
                return theme;
            }
        }
        throw new NotFoundException("Theme was not found");
    }

    @Override
    public List<Theme> getThemes() {
        return this.themes;
    }
}
