package org.daffunchio.alertsystem.models;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class User {
    private static Long incrementalId = 0L;
    private Long id;
    private String username;
    private String password;
    private Long phone;

    private List<Alert> alerts = new ArrayList<>();
    private List<AlertListener> listeners = new ArrayList<>();

    public User(String username, String password, Long phone) {

        this.username = username;
        this.password = password;
        this.phone = phone;
        this.id = incrementalId++;
    }

    public User() {
        this.id = incrementalId++;
    }

    public void addAlert(Alert alert) {
        this.alerts.add(alert);
    }

    public void addListener(AlertListener listener) {

        this.listeners.add(listener);
    }

    public void removeListener(Long themeId) {
        if (themeId != null) {

            for (int i = 0; i < this.listeners.size(); i++) {

                if (themeId.equals(this.listeners.get(i).getTheme().getId())) {

                    this.listeners.remove(i);
                    return;
                }
            }
        }
    }
}
