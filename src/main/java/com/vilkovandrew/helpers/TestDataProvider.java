package com.vilkovandrew.helpers;

import com.vilkovandrew.yandex.market.helpers.CheckBoxFilter;
import com.vilkovandrew.yandex.market.helpers.Product;
import org.junit.jupiter.params.provider.Arguments;

import java.util.List;
import java.util.stream.Stream;

/**
 * Класс предоставления данных для тестов.
 *
 * @author Вилков Андрей
 */
public class TestDataProvider {

    /**
     * Поставщик тестовых данных для yaMarketProductTest.
     * <p>
     * Автор: Вилков Андрей
     * </p>
     *
     * @return массив тестовых данных
     */
    public static Stream<Arguments> yaMarketProductTest() {

        return Stream.of(
                Arguments.of(Properties.appProperties.getYaMarketUrl(), "Электроника", "Смартфоны", List.of(new CheckBoxFilter("Производитель", Product::getHeader, "Apple"))),
                Arguments.of(Properties.appProperties.getYaMarketUrl(), "Электроника", "Смартфоны", List.of(new CheckBoxFilter("Производитель", Product::getHeader, "ASUS"))),
                Arguments.of(Properties.appProperties.getYaMarketUrl(), "Электроника", "Смартфоны", List.of(new CheckBoxFilter("Производитель", Product::getHeader, "Black Shark"))),
                Arguments.of(Properties.appProperties.getYaMarketUrl(), "Электроника", "Смартфоны", List.of(new CheckBoxFilter("Производитель", Product::getHeader, "OnePlus"))),
                Arguments.of(Properties.appProperties.getYaMarketUrl(), "Электроника", "Смартфоны", List.of(new CheckBoxFilter("Производитель", Product::getHeader, "Google"))),
                Arguments.of(Properties.appProperties.getYaMarketUrl(), "Электроника", "Смартфоны", List.of(new CheckBoxFilter("Производитель", Product::getHeader, "Seals")))
        );
    }
}
