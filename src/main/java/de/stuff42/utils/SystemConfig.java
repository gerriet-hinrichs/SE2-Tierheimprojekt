package de.stuff42.utils;

import java.io.File;
import java.io.IOException;

import org.ini4j.Ini;
import org.ini4j.Profile.Section;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Utility class to access system configuration from <code>system.ini</code> file in root folder.
 */
public final class SystemConfig {

    /**
     * Logger instance used for warnings if the configuration is missing.
     */
    private static Logger logger = LoggerFactory.getLogger(SystemConfig.class);

    /**
     * Loaded ini instance.
     */
    @Nullable
    private static Ini ini;

    /**
     * Hide default constructor.
     */
    private SystemConfig() {
        // hide default constructor
    }

    /**
     * Returns the ini instance for the <code>system.ini</code> file. Creates that instance if not yet loaded.
     *
     * @return Ini instance.
     */
    @NotNull
    private static Ini getIni() {
        if (ini != null) {
            return ini;
        }
        try {
            ini = new Ini(new File(PathUtils.getApplicationRoot().toFile(), "system.ini"));
        } catch (IOException e) {
            logger.warn("system.ini file could not be loaded, using default values for all configuration.");
            ini = new Ini();
        }
        return ini;
    }

    /**
     * Loads the specified configuration value.
     *
     * @param section Value section.
     * @param key     Value key.
     * @param type    Value type information.
     * @param <T>     Value type.
     *
     * @return Loaded value or <code>null</code> if the specified value does not exist.
     */
    @Nullable
    public static <T> T get(@NotNull String section, @NotNull String key, @NotNull Class<T> type) {
        Ini ini = getIni();
        if (ini.containsKey(section)) {
            Section iniSection = ini.get(section);
            if (iniSection.containsKey(key)) {

                // if we have the key, we load the value
                return iniSection.get(key, type);
            }
        }

        // otherwise return null
        return null;
    }

    /**
     * Loads the specified configuration value and uses the given default if the value is missing.
     *
     * @param section      Value section.
     * @param key          Value key.
     * @param type         Value type information.
     * @param defaultValue Default value to be used if the specified value does not exist.
     * @param <T>          Value type.
     *
     * @return Loaded value or the given default if the specified value does not exist.
     */
    @NotNull
    public static <T> T get(@NotNull String section, @NotNull String key, @NotNull Class<T> type, @NotNull T defaultValue) {
        T value = get(section, key, type);
        return value != null ? value : defaultValue;
    }
}
