package com.vilkovandrew.helpers;

import org.aeonbits.owner.Config;

/**
 * Вспомогательный интерфейс для работы со свойствами.
 *
 * @author Вилков Андрей
 */
@Config.LoadPolicy(Config.LoadType.MERGE)
@Config.Sources({
        "system:env",
        "file:src/test/resources/tests.properties"
})
public interface AppProperties extends Config {
    /**
     * Получение ссылки на Яндекс Маркет
     * <p>
     * Автор: Вилков Андрей
     * </p>
     *
     * @return возвращает ссылку на Яндекс Маркет
     */
    @Key("yandex.market.url")
    String getYaMarketUrl();

    /**
     * Получение пути до chromedriver
     * <p>
     * Автор: Вилков Андрей
     * </p>
     *
     * @return возвращает путь до chromedriver
     */
    @Key("CHROME_DRIVER")
    String getDriverPath();

    /**
     * Получение таймаута в минутах
     * <p>
     * Автор: Вилков Андрей
     * </p>
     *
     * @return возвращает таймаут в минутах
     */
    @Key("timeout.next_page_loop")
    int getTimeoutNextPageLoop();

    /**
     * Получение параметра задающего включение скриншотов
     * <p>
     * Автор: Вилков Андрей
     * </p>
     *
     * @return возвращает {@literal on} если включено создание скриншотов
     */
    @Key("screenshot.strategy")
    String getScreenshotStrategy();
}
