package com.vilkovandrew.test;

import com.codeborne.selenide.Configuration;
import io.cucumber.java.BeforeAll;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Selenide.webdriver;

/**
 * Хуки для настройки окружения перед или после каждого сценария
 *
 * @author Вилков Андрей
 */
public class Hooks {
    /**
     * Конфигурирование драйвера перед тестами.
     *
     * <p>
     * Автор: Вилков Андрей
     * </p>
     */
    @BeforeAll
    public static void beforeTest() {

        ChromeOptions options = new ChromeOptions();
        options.addArguments(
                "--remote-allow-origins=*",
                "--incognito",
                "--disable-blink-features=AutomationControlled",
                "--no-sandbox",
                "--disable-dev-shm-usage",
                "--disable-infobars"
        );

        options.addArguments("user-agent=\"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/111.0.0.0 Safari/537.36\"");
        options.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});
        options.setPageLoadStrategy(PageLoadStrategy.NORMAL);
        options.setPageLoadTimeout(Duration.ofSeconds(120));

        Configuration.browserCapabilities = options;
        open();
        webdriver().object().manage().window().maximize();
    }
}
