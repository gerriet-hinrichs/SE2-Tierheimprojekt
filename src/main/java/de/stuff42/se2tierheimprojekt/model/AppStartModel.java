package de.stuff42.se2tierheimprojekt.model;

import java.time.format.DateTimeFormatter;

import de.stuff42.se2tierheimprojekt.service.AppStartService;

public class AppStartModel {

    private final AppStartService service;

    public AppStartModel(AppStartService service) {
        this.service = service;
    }

    public String getBuildVersionJS() {

        // escape quotes in string
        return "'" + service.getBuildVersion().replaceAll("'", "\\'") + "'";
    }

    public String getBuildTimeJS() {

        // create date object from time
        return "new Date('" + service.getBuildTime().format(DateTimeFormatter.ISO_OFFSET_DATE_TIME) + "')";
    }

    public Boolean getIsDebug() {
        return service.isDebug();
    }

    public String getDebugJS() {

        // properly stringify
        return String.valueOf(service.isDebug());
    }
}
