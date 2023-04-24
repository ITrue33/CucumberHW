package com.vilkovandrew.test;

import com.vilkovandrew.yandex.market.helpers.CheckBoxFilter;
import com.vilkovandrew.yandex.market.helpers.Filter;
import com.vilkovandrew.yandex.market.helpers.Product;
import com.vilkovandrew.yandex.market.helpers.RangeFilter;
import com.vilkovandrew.yandex.market.pages.CatalogListPage;
import com.vilkovandrew.yandex.market.pages.MainPageMarket;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.codeborne.selenide.Selenide.open;
import static com.vilkovandrew.helpers.Assertions.assertTrue;
import static java.lang.String.format;

/**
 * Класс содержащий реализацию шагов
 *
 * @author Вилков Андрей
 */
public class MyStepDef {
    /**
     * Для хранения запомненого товара между шагами сценария
     * <p>
     * Автор: Вилков Андрей
     * </p>
     */
    private Product currentProduct;

    /**
     * Открытие страницы сайта по переданной ссылке
     * <p>
     * Автор: Вилков Андрей
     * </p>
     *
     * @param url ссылка на сайт
     */
    @Given("^перейти на сайт (.+)")
    public void перейти_на_сайт(String url) {
        open(url);
    }

    /**
     * Нажатие кнопки 'Каталог' для открытия каталога
     * <p>
     * Автор: Вилков Андрей
     * </p>
     */
    @Given("^открыть каталог")
    public void открытьКаталог() {
        new MainPageMarket().openCatalog();
    }

    /**
     * Наведение курсора на категорию товара в каталоге
     * <p>
     * Автор: Вилков Андрей
     * </p>
     *
     * @param catalogSection имя категории в каталоге
     */
    @Given("^навести курсор на раздел (.+)")
    public void навестиКурсорНаРазделРаздел(String catalogSection) {
        new MainPageMarket().moveCursorToSection(catalogSection);
    }

    /**
     * Открытие раздела из категории каталога
     * <p>
     * Автор: Вилков Андрей
     * </p>
     *
     * @param itemSection имя раздела в категории каталога
     */
    @Given("^открыть подраздел (.+)")
    public void открытьПодраздел(String itemSection) {
        new MainPageMarket().openSectionItem(itemSection);
    }

    /**
     * Установка значений фильтров
     * <p>
     * Автор: Вилков Андрей
     * </p>
     *
     * @param table таблица содержащая значения для фильтров
     * @see DataTable
     */
    @When("^задать параметр")
    public void задатьПараметр(DataTable table) {
        List<Filter> filterList = getFiltersFromDataTable(table);

        CatalogListPage listPage = new CatalogListPage();
        listPage.setFilters(filterList);
    }

    /**
     * Для преобразования таблицы с данными в список фильтров
     * <p>
     * Автор: Вилков Андрей
     * </p>
     *
     * @param table - таблица с данными
     * @return {@link List<Filter>} - список фильтров
     */
    private static List<Filter> getFiltersFromDataTable(DataTable table) {
        List<Filter> filterList = table.asMaps().stream()
                .map(e -> {
                    if (Objects.nonNull(e.get("Value")))
                        return new CheckBoxFilter(e.get("filterName"), Product::getHeader, e.get("Value").split(","));
                    else if (Objects.nonNull(e.get("ValueFrom"))) {
                        int valueFrom = Integer.parseInt(e.get("ValueFrom"));
                        int valueTo = Integer.parseInt(e.get("ValueTo"));
                        return new RangeFilter(e.get("filterName"), Product::getPrice, valueFrom, valueTo);
                    }
                    return null;
                })
                .collect(Collectors.toList());

        return filterList;
    }

    /**
     * Проверка что на странице отображается товаров больше чем переданное значение
     * <p>
     * Автор: Вилков Андрей
     * </p>
     *
     * @param countOnPage ожидаемое количество элементов на странице
     */
    @Then("на странице отображается более {int} элементов.")
    public void наПСтраницеОтображаетсяБолееЭлементов(int countOnPage) {
        CatalogListPage listPage = new CatalogListPage();
        listPage.numberItemsOnPageMoreThan(countOnPage);
    }

    /**
     * Переход на страницу по номеру
     * <p>
     * Автор: Вилков Андрей
     * </p>
     *
     * @param pageNumber номер страницы
     */
    @And("перейти на {int} страницу")
    public void перейтиНаСтраницу(int pageNumber) {
        CatalogListPage listPage = new CatalogListPage();
        listPage.goToPage(pageNumber);
    }

    /**
     * Получение товара со страницы поиска по номеру товара по порядку,
     * сохранение его в переменную {@code currentProduct} и поиск в каталоге по названию товара
     * <p>
     * Автор: Вилков Андрей
     * </p>
     *
     * @param number номер по порядку
     */
    @And("запомнить {int} наименование товара и ввести в поисковую строку")
    public void запомнитьНаименованиеТовараВвестиВПоисковуюСтроку(int number) {
        CatalogListPage listPage = new CatalogListPage();
        currentProduct = listPage.getProductByIndexOnPage(number);
        listPage.search(currentProduct.getHeader());
    }

    /**
     * Проверка наличия запомненого товара {@code currentProduct} на странице.
     * <p>
     * Автор: Вилков Андрей
     * </p>
     */
    @And("в результатах поиска есть искомый товар")
    public void вРезультатахПоискаЕстьИскомыйТовар() {
        CatalogListPage listPage = new CatalogListPage();
        assertTrue(Objects.nonNull(currentProduct), format("Ожидалось наличие запомненого товара. Товар не был предварительно запомнен.\n"));
        listPage.containsOnPage(currentProduct);
    }

    /**
     * Проверка соответствия всех товаров переданным фильтрам
     * <p>
     * Автор: Вилков Андрей
     * </p>
     *
     * @param table таблица содержащая значения для фильтров
     * @see DataTable
     */
    @And("проверяем соответствие всех товаров на всех страницах")
    public void проверяемСоответствиеВсехТоваровНаВсехСтраницах(DataTable table) {
        List<Filter> filterList = getFiltersFromDataTable(table);
        CatalogListPage listPage = new CatalogListPage();
        listPage.isAllProductsMatchFilters(filterList);
    }
}
