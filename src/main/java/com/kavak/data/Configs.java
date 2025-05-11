package com.kavak.data;

import org.aeonbits.owner.Config;
import org.aeonbits.owner.ConfigCache;

@Config.LoadPolicy(Config.LoadType.MERGE)
@Config.Sources({
        "system:properties",
        "system:env",
        "classpath:config.properties"})
public interface Configs extends Config {
    static Configs getConfigs() {
        return ConfigCache.getOrCreate(Configs.class);
    }

    @Key("env")
    String env();

    @Key("browser")
    String browser();

    @Key("${env}.kavak.baseurl")
    String kavakBaseUrl();

    @Key("${env}.api.baseurl")
    String apiBaseUrl();

    @Key("driver.options.headless")
    boolean isHeadless();

}
