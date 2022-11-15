package com.pipefy.config;

import org.aeonbits.owner.Config;

@Config.Sources({
        "classpath:api.properties"})
public interface ConfigurationAPI extends Config {

    @Key("api.base.uri")
    String baseURI();
}
