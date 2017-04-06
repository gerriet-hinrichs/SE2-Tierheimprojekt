package de.stuff42.se2tierheimprojekt.service;

import java.time.OffsetDateTime;

import de.stuff42.utils.SystemConfig;

import org.springframework.stereotype.Service;

/**
 * App start service to provide basic information that is required on app start.
 */
@Service
public class AppStartService extends BaseService {

    public String getBuildVersion() {
        return SystemConfig.get("BUILD", "Version", String.class, "UNKNOWN");
    }

    public OffsetDateTime getBuildTime() {
        return OffsetDateTime.parse(SystemConfig.get(
                "BUILD",
                "Timestamp",
                String.class,
                "1970-01-01T00:00:00.000+00:00"
        ));
    }

    public Boolean isDebug() {
        return SystemConfig.get("BUILD", "Debug", Boolean.class, false);
    }
}
