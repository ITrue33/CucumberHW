package com.vilkovandrew;


import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.ExcludeTags;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.Suite;

import static io.cucumber.junit.platform.engine.Constants.*;

/**
 * Класс для запуска сценариев
 *
 * @author Вилков Андрей
 */

@Suite
@IncludeEngines("cucumber")
@ExcludeTags("laptop")

@ConfigurationParameter(key = FEATURES_PROPERTY_NAME, value = "src/test/java/com/vilkovandrew/features")
@ConfigurationParameter(key = GLUE_PROPERTY_NAME, value = "com.vilkovandrew.test")
@ConfigurationParameter(key = PLUGIN_PROPERTY_NAME, value = "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm")
@ConfigurationParameter(key = PLUGIN_PROPERTY_NAME, value = "pretty")
@ConfigurationParameter(key = PLUGIN_PROPERTY_NAME, value = "json:target/cucumber-report/report.json")
@ConfigurationParameter(key = PLUGIN_PUBLISH_QUIET_PROPERTY_NAME, value = "true")

public class CucumberRunner {

}

