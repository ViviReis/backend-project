package com.pipefy.config;

import org.aeonbits.owner.ConfigCache;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public final class ConfigurationManager {

    private static volatile ConfigurationManager obj = null;
    private String automationSecretKey;
    private Properties credentials = new Properties();
    private final String credentialsFilePath = "src/test/resources/data/credentials.properties";

    private ConfigurationManager() {
    }

    public static ConfigurationAPI getConfiguration() {
        return ConfigCache.getOrCreate(ConfigurationAPI.class);
    }

    public static synchronized ConfigurationManager getInstance() {
        if (obj == null) {
            synchronized (ConfigurationManager.class) {
                if (obj == null) {
                    obj = new ConfigurationManager();
                }
            }
        }
        return obj;
    }

    private void initProperties() throws IOException {
        credentials.load(new FileInputStream(new File(credentialsFilePath)));
    }
    private void initEnvVariables() {

        String automationSecretKey = System.getenv("AUTOMATION_SECRET");
        if (automationSecretKey == null) {
            throw new RuntimeException("Missing AUTOMATION_SECRET env variable");
        }
        setAutomationSecretKey(automationSecretKey);
    }

    public void setup() throws IOException {
        initProperties();
        initEnvVariables();
    }

    public String getAutomationSecretKey() {
        return automationSecretKey;
    }

    public void setAutomationSecretKey(String automationSecretKey) {
        this.automationSecretKey = automationSecretKey;
    }

    public Properties getCredentials() {
        return credentials;
    }

    public String getUserId() {
        return getCredentials().getProperty("user.id");
    }
    public String getUserJwtToken() {
        return getCredentials().getProperty("jwt.token");
    }

    public String getPipeUuid() {
        return getCredentials().getProperty("pipe.uuid");
    }

    public String getPhaseUuid() {
        return getCredentials().getProperty("phase.uuid");
    }
}
