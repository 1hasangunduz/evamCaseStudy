package tests.unitTests;

import com.kavak.data.Configs;
import com.kavak.utilities.Log;

@org.testng.annotations.Test
public class Tests {


    public void configs() {
        var config = Configs.getConfigs();
        Log.pass("env: " + config.env());
        Log.pass("kavakBaseUrl: " + config.kavakBaseUrl());
        Log.pass("apiBaseUrl: " + config.apiBaseUrl());
        Log.pass("isHeadless: " + config.isHeadless());
        Log.pass("isB");
    }

}
