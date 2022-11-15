package com.pipefy.hooks;

import com.google.common.collect.ImmutableMap;
import com.pipefy.config.ConfigurationAPI;
import com.pipefy.config.ConfigurationManager;
import io.restassured.RestAssured;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

import java.io.IOException;

import static com.github.automatedowl.tools.AllureEnvironmentWriter.allureEnvironmentWriter;
import static io.restassured.RestAssured.baseURI;

public class BaseHooks {
    private static ConfigurationAPI configuration;
    private static ConfigurationManager configurationManager = ConfigurationManager.getInstance();

    @BeforeAll
    public static void beforeAllTests() throws IOException {
        configurationManager.setup();
        configuration = ConfigurationManager.getConfiguration();
        baseURI = configuration.baseURI();
        RestAssured.useRelaxedHTTPSValidation();
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    @AfterAll
    public static void setAllureEnvironment() {
        allureEnvironmentWriter(
                ImmutableMap.<String, String>builder()
                        .put("URI", baseURI = configuration.baseURI())
                        .build());
    }
}
