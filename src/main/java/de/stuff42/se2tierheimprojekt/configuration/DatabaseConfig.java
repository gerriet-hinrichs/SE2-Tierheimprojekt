package de.stuff42.se2tierheimprojekt.configuration;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import de.stuff42.se2tierheimprojekt.Application;
import de.stuff42.utils.PathUtils;

import org.ini4j.Ini;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Configuration class providing database system configuration.
 */
public class DatabaseConfig {

    /**
     * Logger instance to be used.
     */
    private final Logger logger = LoggerFactory.getLogger(DatabaseConfig.class);

    /**
     * Cache for loaded ini files.
     */
    private Map<String, Ini> iniInstances = new HashMap<>();

    /**
     * Configuration prefix for the values loaded from <code>database.ini</code>.
     */
    static String configurationPrefix = "";

    /**
     * Loads database config file with the given name.
     *
     * @param name Name to be loaded.
     *
     * @return Loaded ini file.
     */
    @NotNull
    private Ini getIni(@NotNull String name) {
        if (iniInstances.containsKey(name)) {
            return iniInstances.get(name);
        }
        try {
            File iniBasePath = new File(PathUtils.getApplicationRoot().toFile(), "database");
            iniInstances.put(name, new Ini(new File(iniBasePath, name + ".ini")));
        } catch (IOException e) {
            logger.error(name + ".ini database configuration file could not be loaded.", e);

            // we terminate the application if one configuration could not be loaded
            Application.stop();
        }
        return iniInstances.get(name);
    }

    /**
     * Loads selected database connection ini file.
     *
     * @return Selected database configuration ini file.
     */
    @NotNull
    private Ini getSelectedDatabaseConfiguration() {
        Ini databaseIni = getIni("database");

        // add configuration prefix here to load the correct field
        String configurationName = databaseIni.get("Database", configurationPrefix + "Configuration");
        return getIni(configurationName);
    }

    /**
     * Returns the <code>spring.datasource.url</code> configuration property.
     *
     * @return Property value.
     */
    @NotNull
    private String getDataSourceUrl() {
        Ini ini = getSelectedDatabaseConfiguration();
        return "jdbc:" + ini.get("Configuration", "driver")
                + "://" + ini.get("Connection", "host")
                + ":" + ini.get("Connection", "port")
                + "/" + ini.get("Connection", "name");
    }

    /**
     * Returns the <code>spring.datasource.username</code> configuration property.
     *
     * @return Property value.
     */
    @NotNull
    private String getDataSourceUsername() {
        return getSelectedDatabaseConfiguration().get("Login", "user");
    }

    /**
     * Returns the <code>spring.datasource.password</code> configuration property.
     *
     * @return Property value.
     */
    @NotNull
    private String getDataSourcePassword() {
        return getSelectedDatabaseConfiguration().get("Login", "password");
    }

    /**
     * Returns the <code>spring.datasource.testWhileIdle</code> configuration property.
     *
     * @return Property value.
     */
    @Contract(pure = true)
    private boolean getDataSourceTestWhileIdle() {
        return true;
    }

    /**
     * Returns the <code>spring.datasource.validationQuery</code> configuration property.
     *
     * @return Property value.
     */
    @Contract(pure = true)
    @NotNull
    private String getDataSourceValidationQuery() {
        return "SELECT 1";
    }

    /**
     * Returns the <code>spring.jpa.show-sql</code> configuration property.
     *
     * @return Property value.
     */
    private boolean getJpsShowSql() {
        return Boolean.getBoolean(getSelectedDatabaseConfiguration().get("Configuration", "logging"));
    }

    /**
     * Returns the <code>spring.jpa.hibernate.ddl-auto</code> configuration property.
     *
     * @return Property value.
     */
    @NotNull
    private String getJpaHibernateDdlAuto() {
        return getSelectedDatabaseConfiguration().get("Configuration", "mode");
    }

    /**
     * Returns the <code>spring.jpa.hibernate.naming-strategy</code> configuration property.
     *
     * @return Property value.
     */
    @Contract(pure = true)
    @NotNull
    private String getJpaHibernateNamingStrategy() {
        return "org.hibernate.cfg.ImprovedNamingStrategy";
    }

    /**
     * Returns the <code>spring.jpa.properties.hibernate.dialect</code> configuration property.
     *
     * @return Property value.
     */
    @NotNull
    private String getJpaPropertiesHibernateDialect() {
        return "org.hibernate.dialect." +
                getSelectedDatabaseConfiguration().get("Configuration", "dialect")
                + "Dialect";
    }

    /**
     * Updates the given configuration instance with the values from this database configuration instance.
     *
     * @param properties Properties instance to be updated.
     */
    void updateConfigurationProperties(Properties properties) {
        properties.put("spring.datasource.url", getDataSourceUrl());
        properties.put("spring.datasource.username", getDataSourceUsername());
        properties.put("spring.datasource.password", getDataSourcePassword());
        properties.put("spring.datasource.testWhileIdle", getDataSourceTestWhileIdle());
        properties.put("spring.datasource.validationQuery", getDataSourceValidationQuery());
        properties.put("spring.jpa.show-sql", getJpsShowSql());
        properties.put("spring.jpa.hibernate.ddl-auto", getJpaHibernateDdlAuto());
        properties.put("spring.jpa.hibernate.naming-strategy", getJpaHibernateNamingStrategy());
        properties.put("spring.jpa.properties.hibernate.dialect", getJpaPropertiesHibernateDialect());
    }
}
