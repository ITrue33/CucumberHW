package com.vilkovandrew.yandex.market.helpers;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

/**
 * Класс представляющий фильтр с несколькими возможными вариантами значений.
 *
 * @author Vilkov Andrew
 */
public class CheckBoxFilter extends Filter {

    /**
     * Функция получения значения из экземпляра класса {@link Product},
     * по которому будет осуществляться проверка соответствия товара фильтру
     * <p>
     * Автор: Вилков Андрей
     * </p>
     */
    private final Function<Product, String> checkingField;

    /**
     * Список параметров фильтра
     * <p>
     * Автор: Вилков Андрей
     * </p>
     */
    private final List<String> values;

    /**
     * Конструктор класса {@link CheckBoxFilter}.
     * <p>
     * Автор: Вилков Андрей
     * </p>
     *
     * @param filterName    имя фильтра
     * @param checkingField функция получения значения из экземпляра класса {@link Product}
     *                      по которому будет осуществляться проверка соответствия товара фильтру
     * @param values        список параметров фильтра
     */
    public CheckBoxFilter(String filterName, Function<Product, String> checkingField, String... values) {
        super(FilterType.CHECKBOX, filterName);
        this.values = Arrays.asList(values);
        this.checkingField = checkingField;
    }

    /**
     * Получение списка параметров фильтра.
     * <p>
     * Автор: Вилков Андрей
     * </p>
     *
     * @return список параметров фильтра
     */
    public List<String> getValues() {
        return values;
    }

    /**
     * Проверка соответствия товара фильтру.
     * <p>
     * Автор: Вилков Андрей
     * </p>
     *
     * @param product проверяемый товар
     * @return true если товар соответствует фильтру, в противном случае false
     */
    @Override
    public boolean isMatches(Product product) {
        for (String value : values) {
            if (checkingField.apply(product).toLowerCase().contains(value.toLowerCase())) return true;
        }
        return false;
    }

    /**
     * Получение строкового представления фильтра.
     * <p>
     * Автор: Вилков Андрей
     * </p>
     *
     * @return строковое представление фильтра.
     */
    @Override
    public String toString() {
        return "Filter{" +
                "filterName='" + getFilterName() + '\'' +
                ", values=" + String.join(",", values) + '}';
    }
}
