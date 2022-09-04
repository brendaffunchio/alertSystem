package org.daffunchio.alertsystem.services.impl;

import lombok.NoArgsConstructor;
import org.daffunchio.alertsystem.exceptions.NotFoundException;
import org.daffunchio.alertsystem.models.Theme;
import org.daffunchio.alertsystem.repositories.ThemeRepository;
import org.daffunchio.alertsystem.services.AlertManagerService;
import org.daffunchio.alertsystem.services.ThemeService;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
public class ThemeServiceImpl implements ThemeService {
    ThemeRepository themeRepository;
    AlertManagerService alertManagerService;

    public ThemeServiceImpl(ThemeRepository themeRepository, AlertManagerService alertManagerService) {

        this.themeRepository = themeRepository;
        this.alertManagerService = alertManagerService;
    }

    @Override
    public List<Theme> listAllThemes() {
        return themeRepository.getThemes();
    }

    @Override
    public Theme registerTheme(String title, String description, boolean urgent) throws IllegalArgumentException, IllegalStateException {
        if (title == null) throw new IllegalArgumentException("Title is null");
        if (description == null) throw new IllegalArgumentException("Description is null");
        if (title.isEmpty()) throw new IllegalArgumentException("Title is empty");
        if (description.isEmpty()) throw new IllegalArgumentException("Description is empty");

        Theme theme = new Theme();
        theme.setTitle(title);
        theme.setDescription(description);
        theme.setUrgent(urgent);
        alertManagerService.createAlertManager(theme);
        return themeRepository.save(theme);
    }

    @Override
    public Theme getThemeById(Long id) throws NotFoundException {
        Theme theme = themeRepository.getById(id);
        if (theme == null) throw new NotFoundException("Theme was not found");
        return theme;
    }

    @Override
    public List<Theme> listUrgentThemes() {
        List<Theme> urgent = new ArrayList<>();
        for (Theme theme : themeRepository.getThemes()
        )
            if (theme.isUrgent()) {
                urgent.add(theme);
            }
        return urgent;
    }
}
