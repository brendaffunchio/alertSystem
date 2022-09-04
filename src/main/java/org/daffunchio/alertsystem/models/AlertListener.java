package org.daffunchio.alertsystem.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AlertListener {

    private User user;
    private Theme theme;

    public void update(Alert alert) {
        user.addAlert(alert);
        System.out.println("usuario: " + user.getUsername() + " recibio la alerta: " + alert.getTitle());
    }


    public Long getId() {
        return this.getUser().getId();
    }


}
