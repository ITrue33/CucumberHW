package com.vilkovandrew.yandex.market.pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import com.vilkovandrew.helpers.Assertions;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.Optional;

import static com.codeborne.selenide.CollectionCondition.anyMatch;
import static com.codeborne.selenide.Selenide.*;
import static java.lang.String.format;
import static java.time.Duration.ofSeconds;

/**
 * Класс представляющий Page Object для главной страницы 'Яндекс Маркет'.
 *
 * @author Vilkov Andrew
 */
public class MainPageMarket {

    /**
     * Локатор кнопки 'Каталог'.
     * <p>
     * Автор: Вилков Андрей
     * </p>
     */
    private static final By LOCATOR_CATALOG_BUTTON = By.id("catalogPopupButton");

    /**
     * Локатор вкладки с категориями.
     * <p>
     * Автор: Вилков Андрей
     * </p>
     */
    private static final By LOCATOR_TAB_LIST_CATEGORY = By.xpath("//li[@role=\"tab\"]/a");

    /**
     * Локатор разделов категории каталога.
     * <p>
     * Автор: Вилков Андрей
     * </p>
     */
    private static final By LOCATOR_ITEMS = By.xpath("//div[@role='tabpanel']/div//ul[@data-autotest-id='subItems']/li");

    /**
     * Открытие каталога, если он еще не открыт
     * <p>
     * Автор: Вилков Андрей
     * </p>
     */
    @Step("Открытие каталога")
    public MainPageMarket openCatalog() {
        SelenideElement catalogButton = $(LOCATOR_CATALOG_BUTTON);
        boolean isExpanded = Boolean.getBoolean(catalogButton.getDomAttribute("aria-expanded"));
        if (!isExpanded) {
            catalogButton.click();
        }
        return this;
    }

    /**
     * Наведение курсора на категорию товара в каталоге
     * <p>
     * Автор: Вилков Андрей
     * </p>
     *
     * @param sectionName имя категории в каталоге
     */
    @Step("Наведение курсора на раздел {sectionName}")
    public MainPageMarket moveCursorToSection(String sectionName) {
        ElementsCollection categories = $$(LOCATOR_TAB_LIST_CATEGORY).shouldBe(anyMatch(
                format("Ищем раздел '%s' в каталоге", sectionName), e -> e.getText().equalsIgnoreCase(sectionName)), ofSeconds(20));
        Optional<SelenideElement> itemCategory = categories.asDynamicIterable().stream().filter(e -> e.getText().equalsIgnoreCase(sectionName)).findAny();

        Assertions.assertTrue(itemCategory.isPresent(),
                format("Пункт меню \"%s\" не найден в каталоге", sectionName));

        SelenideElement sectionItem = itemCategory.get();
        actions().moveToElement(sectionItem).pause(5).perform();
        return this;
    }

    /**
     * Открытие раздела из категории каталога
     * <p>
     * Автор: Вилков Андрей
     * </p>
     *
     * @param itemName имя раздела в категории каталога
     */
    @Step("Переходим в раздел {itemName}")
    public CatalogListPage openSectionItem(String itemName) {
        ElementsCollection itemList = $$(LOCATOR_ITEMS);
        Optional<SelenideElement> item = itemList.asDynamicIterable().stream()
                .filter(e -> itemName.equalsIgnoreCase(e.getText()))
                .findAny();

        Assertions.assertTrue(item.isPresent(),
                format("Раздел \"%s\" не найден в текущей категории", itemName));

        SelenideElement itemElement = item.get();
        actions()
                .moveByOffset(itemElement.getLocation().getX(), itemElement.getLocation().getY())
                .moveToElement(itemElement)
                .click()
                .perform();
        Wait().until(ExpectedConditions.titleContains(itemName));
        return page(CatalogListPage.class);
    }
}
