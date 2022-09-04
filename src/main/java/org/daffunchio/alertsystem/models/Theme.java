package org.daffunchio.alertsystem.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Theme {
    private static Long incrementalId = 0L;
    private Long id;
    private String title;
    private String description;

    private boolean urgent;

    public Theme(String title, String description, boolean urgent) {
        this.title = title;
        this.description = description;
        this.id = incrementalId++;
        this.urgent = urgent;
    }

    public Theme() {
        this.id = incrementalId++;
    }

}
