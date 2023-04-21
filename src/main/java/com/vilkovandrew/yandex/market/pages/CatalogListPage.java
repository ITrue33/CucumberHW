package com.vilkovandrew.yandex.market.pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import com.vilkovandrew.helpers.Assertions;
import com.vilkovandrew.helpers.Properties;
import com.vilkovandrew.yandex.market.helpers.CheckBoxFilter;
import com.vilkovandrew.yandex.market.helpers.Filter;
import com.vilkovandrew.yandex.market.helpers.Product;
import com.vilkovandrew.yandex.market.helpers.RangeFilter;
import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import io.qameta.allure.model.Status;
import io.qameta.allure.model.StepResult;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.codeborne.selenide.CollectionCondition.*;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static java.lang.String.format;
import static java.time.Duration.ofSeconds;

/**
 * Класс представляющий Page Object для страницы 'Результаты поиска в каталоге Яндекс Маркет'.
 *
 * @author Vilkov Andrew
 */
public class CatalogListPage {
    /**
     * Локатор для списка товаров.
     * <p>
     * Автор: Вилков Андрей
     * </p>
     */
    public static final By LOCATOR_VIRTUOSO_ITEM_LIST = By.xpath("//*[@data-test-id='virtuoso-item-list']");

    /**
     * Локатор для прелоадера в блоке товаров.
     * <p>
     * Автор: Вилков Андрей
     * </p>
     */
    public static final By LOCATOR_PRELOADER = By.xpath("//*[@id='searchResults']/../div[@data-auto='preloader']");
    /**
     * Локатор для поля ввода.
     * <p>
     * Автор: Вилков Андрей
     * </p>
     */
    public static final By LOCATOR_INPUT_TEXT = By.xpath(".//input[@type='text']");
    /**
     * Локатор для значений в блоке фильтра.
     * <p>
     * Автор: Вилков Андрей
     * </p>
     */
    public static final By LOCATOR_DATA_FILTER_VALUE = By.xpath(".//*[@data-filter-value-id and .//span[text()!='']]/label");

    /**
     * Локатор для блоков фильтров.
     * <p>
     * Автор: Вилков Андрей
     * </p>
     */
    private static final By LOCATOR_FILTER_BLOCK = By.xpath("//*[@data-grabber='SearchFilters']//*[@data-filter-id]");

    /**
     * Локатор поля минимального значения фильтра диапазона
     * <p>
     * Автор: Вилков Андрей
     * </p>
     */
    private static final By MIN_VALUE_RANGE_FILTER = By.xpath(".//*[@data-auto=\"filter-range-min\"]//input");

    /**
     * Локатор поля максимального значения фильтра диапазона
     * <p>
     * Автор: Вилков Андрей
     * </p>
     */
    private static final By MAX_VALUE_RANGE_FILTER = By.xpath(".//*[@data-auto=\"filter-range-max\"]//input");

    /**
     * Кнопка 'Показать всё'/'Ещё' в блоке фильтра
     * <p>
     * Автор: Вилков Андрей
     * </p>
     */
    private static final By MORE_VALUE_BUTTON = By.xpath(".//button[@aria-expanded]");

    /**
     * Локатор кнопки 'Вперёд', для перехода на следующую страницу.
     * <p>
     * Автор: Вилков Андрей
     * </p>
     */
    private static final By LOCATOR_NEXT_PAGE_BUTTON = By.xpath("//*[@data-auto='pagination-next']");

    /**
     * Локатор для списка товаров.
     * <p>
     * Автор: Вилков Андрей
     * </p>
     */
    private static final By LOCATOR_PRODUCT_ITEM = By.xpath("//article//h3[@data-zone-name='title']/a[@href]");

    /**
     * Локатор элемента появляющегося последним для определения загрузки страницы.
     * <p>
     * Автор: Вилков Андрей
     * </p>
     */
    private static final By PAGE_LOAD_LOCATOR = By.id("greed");

    /**
     * Локатор для кнопки найти.
     * <p>
     * Автор: Вилков Андрей
     * </p>
     */
    private static final By LOCATOR_SEARCH_BUTTON = By.xpath("//button[@data-r='search-button']");

    /**
     * Локатор для поля поиска.
     * <p>
     * Автор: Вилков Андрей
     * </p>
     */
    private static final By LOCATOR_SEARCH_INPUT = By.xpath("//input[@id='header-search']");

    /**
     * Установка диапазона значений для фильтра
     *
     * <p>
     * Автор: Вилков Андрей
     * </p>
     *
     * @param filterName имя фильтра
     * @param minValue   минимальное значение диапазона
     * @param maxValue   максимальное значение диапазона
     * @return {@link CatalogListPage} возврещает экземпляр текущей страницы
     */
    @Step("Установка фильтру {filterName} значений: от {minValue} до {maxValue} ")
    public CatalogListPage setRangeFilter(String filterName, int minValue, int maxValue) {
        Optional<SelenideElement> filterBlock = getFilterBlockElement(filterName);

        Assertions.assertTrue(filterBlock.isPresent(), format("Фильтр с именем '%s' не найден.", filterName));

        SelenideElement filterElement = filterBlock.get();
        filterElement.scrollIntoView(true);
        SelenideElement minValueField = filterElement.$(MIN_VALUE_RANGE_FILTER);
        SelenideElement maxValueField = filterElement.$(MAX_VALUE_RANGE_FILTER);

        minValueField.setValue(String.valueOf(minValue));
        maxValueField.setValue(String.valueOf(maxValue));

        preloaderWait();
        return this;
    }

    /**
     * Ожидание загрузки товаров и пропадания прелоадера в блоке товаров
     * <p>
     * Автор: Вилков Андрей
     * </p>
     */
    private void preloaderWait() {
        SelenideElement preloader = $(LOCATOR_PRELOADER).shouldBe(visible);
        preloader.should(disappear, ofSeconds(20));
    }

    /**
     * Установка значения для фильтра с несколькими возможными значениями
     *
     * <p>
     * Автор: Вилков Андрей
     * </p>
     *
     * @param filterName имя фильтра
     * @param values     значения фильтра
     * @return {@link CatalogListPage} возврещает экземпляр текущей страницы
     */
    @Step("Установка фильтру {filterName} значений: {values} ")
    public CatalogListPage setManyValueFilter(String filterName, List<String> values) {
        Optional<SelenideElement> filterBlock = getFilterBlockElement(filterName);
        Assertions.assertTrue(filterBlock.isPresent(), format("Фильтр с именем '%s' не найден.", filterName));

        SelenideElement filterElement = filterBlock.get();
        filterElement.scrollIntoView(true);
        if (filterElement.$$(MORE_VALUE_BUTTON).size() > 0) {
            SelenideElement moreButton = filterElement.$(MORE_VALUE_BUTTON);
            if (!Boolean.parseBoolean(moreButton.getDomAttribute("aria-expanded"))) {
                moreButton.click();
                SelenideElement preloader = $x("//*[@role='progressbar']").shouldBe(visible);
                preloader.should(disappear, ofSeconds(20));
            }
        }

        for (String value : values) {
            if (filterElement.$$(LOCATOR_INPUT_TEXT).size() > 0) {
                SelenideElement inputField = filterElement.$(LOCATOR_INPUT_TEXT);
                inputField.click();
                inputField.setValue(value);
                inputField.shouldHave(exactValue(value));
            }

            ElementsCollection presenceValue = filterElement.$$(LOCATOR_DATA_FILTER_VALUE);
            presenceValue.shouldHave(exactTexts(value));

            Assertions.assertNotEquals(0, presenceValue.size(),
                    format("Ожидаем что для фильтра '%s' есть значение '%s', совпадений не найдено.", filterName, value));

            Optional<SelenideElement> filterValue = presenceValue.asFixedIterable().stream().filter(e -> e.getText().equalsIgnoreCase(value)).findAny();

            Assertions.assertTrue(filterValue.isPresent(), "Пункт фильтра '" + value + "' не найден.");
            SelenideElement filterValueElement = filterValue.get();
            filterValueElement.click();
            preloaderWait();
        }
        return this;
    }

    /**
     * Вспомогательный метод для получения блока фильтра по его имени
     * <p>
     * Автор: Вилков Андрей
     * </p>
     *
     * @param filterName имя фильтра
     * @return {@link Optional<SelenideElement>} возвращает результат поиска блока фильтра по его имени
     */
    private Optional<SelenideElement> getFilterBlockElement(String filterName) {
        ElementsCollection filtersList = $$(LOCATOR_FILTER_BLOCK);

        Optional<SelenideElement> filterBlock = filtersList.asFixedIterable().stream()
                .filter(e -> {
                    String elementName = e.scrollTo().getText().toUpperCase();
                    return elementName.contains(filterName.toUpperCase());
                })
                .findAny();
        return filterBlock;
    }

    /**
     * Получение товаров со страницы поиска.
     * <p>
     * Автор: Вилков Андрей
     * </p>
     *
     * @return {@link ElementsCollection} коллекцию содержащую товары
     */
    @Step("Получаем список товаров с текущей страницы")
    public ElementsCollection getProductOnPage() {
        $(PAGE_LOAD_LOCATOR).shouldBe(exist, ofSeconds(20));

        scrollToBottom();

        return $$(LOCATOR_PRODUCT_ITEM);
    }

    /**
     * Переход на конкретную страницу по номеру.
     * <p>
     * Автор: Вилков Андрей
     * </p>
     *
     * @param pageNumber номер страницы
     * @return {@link CatalogListPage} возврещает экземпляр текущей страницы
     */
    @Step("Переход на страницу №{pageNumber}")
    public CatalogListPage goToPage(int pageNumber) {
        String currentUrl = webdriver().driver().getCurrentFrameUrl();
        Pattern pageCurrentNumberPattern = Pattern.compile(".*(page=\\d+).?");
        Matcher matcher = pageCurrentNumberPattern.matcher(currentUrl);
        if (matcher.find()) {
            currentUrl = currentUrl.replace(matcher.group(1), format("page=%d", pageNumber));
        } else {
            currentUrl = format("%s&page=%d", currentUrl, pageNumber);
        }
        scrollToBottom();
        return open(currentUrl, CatalogListPage.class);
    }

    /**
     * Прокрутка страницы вниз на высоту элемента содержащего список товаров
     * <p>
     * Автор: Вилков Андрей
     * </p>
     */
    private void scrollToBottom() {
        $(LOCATOR_VIRTUOSO_ITEM_LIST).scrollIntoView(true);
        Pattern digit = Pattern.compile("(\\d+).*");
        SelenideElement scroller = $(LOCATOR_VIRTUOSO_ITEM_LIST);
        int scrollerHight = 0;
        do {
            String hight = scroller.getCssValue("paddingBottom");
            Matcher matcher = digit.matcher(hight);
            if (matcher.find()) {
                scrollerHight = Integer.parseInt(matcher.group(1));
            }
            actions().scrollByAmount(0, scrollerHight).perform();
        } while (scrollerHight > 0);
        $$(LOCATOR_PRODUCT_ITEM).last().scrollTo();
        actions().pause(100).perform();
    }

    /**
     * Ввод текста в поле поиска на странице и нажатие кнопки 'Найти'.
     * <p>
     * Автор: Вилков Андрей
     * </p>
     *
     * @param searchText текст для ввода в строку поиска
     * @return {@link CatalogListPage} возврещает экземпляр текущей страницы
     */
    @Step("Вводим в поисковую строку '{searchText}' и нажимаем поиск")
    public CatalogListPage search(String searchText) {
        SelenideElement searchInput = $(LOCATOR_SEARCH_INPUT);
        searchInput
                .scrollTo()
                .setValue(searchText);

        $(LOCATOR_SEARCH_BUTTON).click();
        return this;
    }

    /**
     * Переход на следующую страницу.
     * <p>
     * Автор: Вилков Андрей
     * </p>
     *
     * @return true если есть кнопка перехода на следующую страницу и false если кнопки перехода на следующую страницу нет.
     */
    private boolean goToNextPage() {
        if ($$(LOCATOR_NEXT_PAGE_BUTTON).size() > 0) {
            WebElement nextPageButton = Wait().until(ExpectedConditions.visibilityOfElementLocated(LOCATOR_NEXT_PAGE_BUTTON));
            actions().scrollToElement(nextPageButton).click(nextPageButton).perform();
            return true;
        }
        return false;
    }

    /**
     * Установка значений фильтров
     * <p>
     * Автор: Вилков Андрей
     * </p>
     *
     * @param filters список фильтров
     * @return {@link CatalogListPage} возврещает экземпляр текущей страницы
     * @see Filter
     */
    @Step("Установка значений для фильтров {filters}")
    public CatalogListPage setFilters(List<Filter> filters) {
        filters.forEach(f -> {
            switch (f.getType()) {
                case RANGE: {
                    RangeFilter rfilter = (RangeFilter) f;
                    setRangeFilter(rfilter.getFilterName(), rfilter.getMinValue(), rfilter.getMaxValue());
                }
                break;
                case CHECKBOX: {
                    CheckBoxFilter chfilter = (CheckBoxFilter) f;
                    setManyValueFilter(chfilter.getFilterName(), chfilter.getValues());
                }
                break;
            }
        });
        return this;
    }

    /**
     * Проверка что на странице отображается товаров больше чем переданное значение
     * <p>
     * Автор: Вилков Андрей
     * </p>
     *
     * @param number ожидаемое количество элементов на странице
     * @return {@link CatalogListPage} возврещает экземпляр текущей страницы
     */
    @Step("Проверяем что на странице более {number} элементов")
    public CatalogListPage numberItemsOnPageMoreThan(int number) {
        int actualSize = getProductOnPage().size();
        Assertions.assertTrue(
                actualSize
                        >
                        number,
                "Ожидали что количестов элементов на странице более " + number +
                        ", количество элементов на странице: " + actualSize);
        return this;
    }

    /**
     * Проверка соответствия всех товаров на всех страницах переданным фильтрам
     * <p>
     * Автор: Вилков Андрей
     * </p>
     *
     * @param filters список фильтров
     * @return {@link CatalogListPage} возврещает экземпляр текущей страницы
     * @see Filter
     */
    @Step("Проверка соответствия товаров фильтрам")
    public CatalogListPage isAllProductsMatchFilters(List<Filter> filters) {
        final String parentUUID = Allure.getLifecycle().getCurrentTestCaseOrStep().get();

        LocalTime startTime = LocalTime.now();
        do {
            for (SelenideElement element : getProductOnPage().asDynamicIterable()) {
                element.scrollIntoView(true).shouldBe(visible, ofSeconds(20));
                Product product = new Product(element);
                UUID uuid = UUID.randomUUID();
                Allure.getLifecycle().startStep(parentUUID, uuid.toString(), new StepResult()
                        .setName(format("Тестируем %s", product.getHeader()))
                        .setStatus(Status.PASSED)
                );
                filters.forEach(f -> {
                    isMatchProductFilter(product, f);
                });
                Allure.getLifecycle().stopStep(uuid.toString());
            }
        } while (goToNextPage() && ChronoUnit.MINUTES.between(startTime, LocalTime.now()) < Properties.appProperties.getTimeoutNextPageLoop());
        return this;
    }

    /**
     * Проверка соответствия товара фильтру
     * <p>
     * Автор: Вилков Андрей
     * </p>
     *
     * @param product проверяемый продукт
     * @param filter  проверяемый фильтр
     * @return {@link CatalogListPage} возврещает экземпляр текущей страницы
     */
    @Step("Проверяем что товар соответствует фильтру {filter.filterName}")
    public CatalogListPage isMatchProductFilter(Product product, Filter filter) {
        Assertions.assertTrue(filter.isMatches(product),
                format("Товар '%s' не соответствует фильтру %s\n", product.getHeader(), filter));
        return this;
    }

    /**
     * Получение товара со страницы поиска по номеру товара по порядку
     * <p>
     * Автор: Вилков Андрей
     * </p>
     *
     * @param index номер по порядку
     * @return экземпляр класса {@link Product} содержащий информацию о товаре
     */
    @Step("Получаем {index} по порядку товар на странице")
    public Product getProductByIndexOnPage(int index) {
        ElementsCollection productOnPage = getProductOnPage();
        Assertions.assertTrue(productOnPage.size() >= index,
                "Ожидали что количестов элементов на странице более " + index +
                        ", количество элементов на странице: " + productOnPage.size());
        SelenideElement product = productOnPage.get(index - 1);
        product.scrollTo();
        return new Product(product);
    }

    /**
     * Проверка наличия конкретного товара на странице.
     * <p>
     * Автор: Вилков Андрей
     * </p>
     *
     * @param product товар
     * @return {@link CatalogListPage} возврещает экземпляр текущей страницы
     */
    @Step("Проверяем наличие '{product.header}' на странице")
    public CatalogListPage containsOnPage(Product product) {
        ElementsCollection productOnPage = getProductOnPage();
        productOnPage
                .shouldHave(
                        anyMatch("Ожидали наличие товара с наименованием '"
                                        + product.getHeader()
                                        + "', товар отсутствует на странице.",
                                webElement -> webElement.getText().equals(product.getHeader())), ofSeconds(20))
                .filterBy(text(product.getHeader()))
                .shouldBe(sizeGreaterThanOrEqual(1), ofSeconds(20))
                .first()
                .scrollIntoView(true);
        return this;
    }
}

