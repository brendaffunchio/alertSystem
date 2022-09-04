package org.daffunchio.alertsystem.app;

import lombok.Getter;
import org.daffunchio.alertsystem.models.AlertListener;
import org.daffunchio.alertsystem.repositories.*;
import org.daffunchio.alertsystem.repositories.impl.*;
import org.daffunchio.alertsystem.services.*;
import org.daffunchio.alertsystem.services.impl.*;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Container implements ListenersContainer {

    private UserService userService;
    private ThemeService themeService;
    private AlertService alertService;
    private AlertListenerService alertListenerService;
    private AlertManagerService alertManagerService;
    private ApplicationService applicationService;
    private UserRepository userRepository;
    private ThemeRepository themeRepository;
    private AlertRepository alertRepository;
    private AlertListenerRepository alertListenerRepository;
    private AlertManagerRepository alertManagerRepository;


    private List<AlertListener> globalListeners;


    public Container() {
        this.userRepository = new UserRepositoryImpl();
        this.themeRepository = new ThemeRepositoryImpl();
        this.alertRepository = new AlertRepositoryImpl();
        this.alertListenerRepository = new AlertListenerRepositoryImpl();
        this.alertManagerRepository = new AlertManagerRepositoryImpl();

        this.alertListenerService = new AlertListenerServiceImpl(alertListenerRepository);
        this.alertManagerService = new AlertManagerServiceImpl(alertManagerRepository, alertListenerService);
        this.userService = new UserServiceImpl(userRepository, alertManagerService);
        this.themeService = new ThemeServiceImpl(themeRepository, alertManagerService);
        this.alertService = new AlertServiceImpl(alertRepository, userService, themeService, alertManagerService);

        this.applicationService = new ApplicationServiceImpl(userService, themeService, alertService, alertManagerService);
        globalListeners = new ArrayList<>();

    }

    @Override
    public void addListener(AlertListener listener) {

        this.globalListeners.add(listener);
    }

    @Override
    public List<AlertListener> getGlobalListeners() {
        return this.globalListeners;
    }

    @Override
    public void removeListener(Long listenerId) {
        if (listenerId != null) {

            for (int i = 0; i < this.globalListeners.size(); i++) {

                if (listenerId.equals(this.globalListeners.get(i).getId())) {

                    this.globalListeners.remove(i);
                    return;
                }
            }
        }

    }
}
