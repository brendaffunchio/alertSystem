package org.daffunchio.alertsystem.models;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class Alert {
    private static Long incrementalId = 0L;
    protected Long id;
    protected LocalDate expiration_date;
    protected String title;
    protected boolean read;

    public Alert(String title) {
        this.expiration_date = LocalDate.now().plusDays(7);
        this.title = title;
        this.read = false;
        this.id = incrementalId++;
    }

    public Alert() {
        this.id = incrementalId++;
    }
}
