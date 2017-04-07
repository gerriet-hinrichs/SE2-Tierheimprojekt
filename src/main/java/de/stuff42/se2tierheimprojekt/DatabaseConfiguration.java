package de.stuff42.se2tierheimprojekt;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.ini4j.Ini;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.stuff42.utils.PathUtils;

/**
 * Configuration class providing database system configuration.
 */
public class DatabaseConfiguration {

    /**
     * Logger instance to be used.
     */
    private final Logger logger = LoggerFactory.getLogger(DatabaseConfiguration.class);

    /**
     * Cache for loaded ini files.
     */
    private Map<String, Ini> iniInstances = new HashMap<>();

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
        String configurationName = databaseIni.get("Database", "Configuration");
        return getIni(configurationName);
    }

    /**
     * Returns the <code>spring.datasource.url</code> configuration property.
     *
     * @return Property value.
     */
    @NotNull
    public String getDataSourceUrl() {
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
    public String getDataSourceUsername() {
        return getSelectedDatabaseConfiguration().get("Login", "user");
    }

    /**
     * Returns the <code>spring.datasource.password</code> configuration property.
     *
     * @return Property value.
     */
    @NotNull
    public String getDataSourcePassword() {
        return getSelectedDatabaseConfiguration().get("Login", "password");
    }

    /**
     * Returns the <code>spring.datasource.testWhileIdle</code> configuration property.
     *
     * @return Property value.
     */
    public boolean getDataSourceTestWhileIdle() {
        return true;
    }

    /**
     * Returns the <code>spring.datasource.validationQuery</code> configuration property.
     *
     * @return Property value.
     */
    @NotNull
    public String getDataSourceValidationQuery() {
        return "SELECT 1";
    }

    /**
     * Returns the <code>spring.jpa.show-sql</code> configuration property.
     *
     * @return Property value.
     */
    public boolean getJpsShowSql() {
        return Boolean.getBoolean(getSelectedDatabaseConfiguration().get("Configuration", "logging"));
    }

    /**
     * Returns the <code>spring.jpa.hibernate.ddl-auto</code> configuration property.
     *
     * @return Property value.
     */
    @NotNull
    public String getJpaHibernateDdlAuto() {
        return getSelectedDatabaseConfiguration().get("Configuration", "mode");
    }

    /**
     * Returns the <code>spring.jpa.hibernate.naming-strategy</code> configuration property.
     *
     * @return Property value.
     */
    @NotNull
    public String getJpaHibernateNamingStrategy() {
        return "org.hibernate.cfg.ImprovedNamingStrategy";
    }

    /**
     * Returns the <code>spring.jpa.properties.hibernate.dialect</code> configuration property.
     *
     * @return Property value.
     */
    @NotNull
    public String getJpaPropertiesHibernateDialect() {
        return "org.hibernate.dialect." +
                getSelectedDatabaseConfiguration().get("Configuration", "dialect")
                + "Dialect";
    }

    /**
     * Updates the given configuration instance with the values from this database configuration instance.
     *
     * @param properties Properties instance to be updated.
     */
    public void updateConfigurationProperties(Properties properties) {
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
