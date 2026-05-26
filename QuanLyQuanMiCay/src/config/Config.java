package config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config {
    private static final Properties props = new Properties();

    static {
        try (InputStream in = Config.class.getResourceAsStream("/config.properties")) {
            if (in != null) {
                props.load(in);
            } else {
                System.err.println("Warning: config.properties not found on classpath; using defaults.");
            }
        } catch (IOException e) {
            System.err.println("Error loading config.properties");
            e.printStackTrace();
        }
    }

    public static String get(String key, String defaultValue) {
        return props.getProperty(key, defaultValue);
    }
}