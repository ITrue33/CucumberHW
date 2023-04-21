package com.vilkovandrew;

import com.codeborne.selenide.junit5.ScreenShooterExtension;
import com.vilkovandrew.yandex.market.helpers.Filter;
import com.vilkovandrew.yandex.market.pages.MainPageMarket;
import io.qameta.allure.Feature;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;

import static com.codeborne.selenide.Selenide.open;

/**
 * Класс содержащий тесты.
 *
 * @author Вилков Андрей
 * @see BaseTest
 */
public class Tests extends BaseTest {
    @RegisterExtension
    static ScreenShooterExtension screenshotEmAll = new ScreenShooterExtension(true);

    /**
     * Тест для Яндекс Маркета
     * <p>
     * Автор: Вилков Андрей
     * </p>
     *
     * @param url            URL который будет открыть
     * @param catalogSection имя категории каталога
     * @param itemSection    имя раздела категории
     * @param filters        фильтры
     * @see Filter
     */
    @Feature("Проверка YandexMarket")
    @DisplayName("Проверка работы фильтров в каталоге")
    @ParameterizedTest(name = "{index} {argumentsWithNames}")
    @MethodSource("com.vilkovandrew.helpers.TestDataProvider#yaMarketProductTest")
    void yaMarketProductTest(String url, String catalogSection, String itemSection, List<Filter> filters) {
        open(url, MainPageMarket.class)
                .openCatalog()
                .moveCursorToSection(catalogSection)
                .openSectionItem(itemSection)
                .setFilters(filters)
                .isAllProductsMatchFilters(filters);
    }

}
