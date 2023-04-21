package com.vilkovandrew.yandex.market.helpers;

import java.util.function.Function;

/**
 * Класс представляющий фильтр с диапазоном значений.
 *
 * @author Vilkov Andrew
 */
public class RangeFilter extends Filter {
    /**
     * Функция получения значения из экземпляра класса {@link Product},
     * по которому будет осуществляться проверка соответствия товара фильтру
     * <p>
     * Автор: Вилков Андрей
     * </p>
     */
    private final Function<Product, Integer> checkingField;

    /**
     * Минимальное значение диапазона
     * <p>
     * Автор: Вилков Андрей
     * </p>
     */
    private final int minValue;
    /**
     * Максимальное значение диапазона
     * <p>
     * Автор: Вилков Андрей
     * </p>
     */
    private final int maxValue;

    /**
     * Конструктор класса {@link RangeFilter}.
     * <p>
     * Автор: Вилков Андрей
     * </p>
     *
     * @param filterName    имя фильтра
     * @param checkingField функция получения значения из экземпляра класса {@link Product}
     *                      по которому будет осуществляться проверка соответствия товара фильтру
     * @param minValue      минимальное значение диапазона
     * @param maxValue      максимальное значение диапазона
     */
    public RangeFilter(String filterName, Function<Product, Integer> checkingField, int minValue, int maxValue) {
        super(FilterType.RANGE, filterName);
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.checkingField = checkingField;
    }

    /**
     * Получение минимальное значение диапазона
     * <p>
     * Автор: Вилков Андрей
     * </p>
     *
     * @return минимальное значение диапазона
     */
    public int getMinValue() {
        return minValue;
    }

    /**
     * Получение максимального значение диапазона
     * <p>
     * Автор: Вилков Андрей
     * </p>
     *
     * @return максимальное значение диапазона
     */
    public int getMaxValue() {
        return maxValue;
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
        Integer currentValue = checkingField.apply(product);
        return currentValue >= minValue && currentValue <= maxValue;
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
        StringBuilder builder = new StringBuilder("Filter{filterName='");
        builder.append(getFilterName())
                .append("', minValues=")
                .append(minValue)
                .append(", maxValues=")
                .append(maxValue)
                .append("}");
        return builder.toString();
    }
}
