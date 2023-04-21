package com.vilkovandrew.yandex.market.helpers;

/**
 * Базовый класс для создания фильтров.
 *
 * @author Вилков Андрей
 */
public abstract class Filter {
    /**
     * Тип фильтра
     * <p>
     * Автор: Вилков Андрей
     * </p>
     *
     * @see FilterType
     */
    private final FilterType type;
    /**
     * Имя фильтра
     * <p>
     * Автор: Вилков Андрей
     * </p>
     */
    private final String filterName;

    /**
     * Конструктор класса {@link Filter}.
     * <p>
     * Автор: Вилков Андрей
     * </p>
     *
     * @param type       {@link FilterType} тип фильтра
     * @param filterName имя фильтра
     */
    public Filter(FilterType type, String filterName) {
        this.type = type;
        this.filterName = filterName;
    }

    /**
     * Получение типа фильтра.
     * <p>
     * Автор: Вилков Андрей
     * </p>
     *
     * @return {@link FilterType} тип фильтра
     */
    public FilterType getType() {
        return type;
    }

    /**
     * Получение имени фильтра.
     * <p>
     * Автор: Вилков Андрей
     * </p>
     *
     * @return имя фильтра
     */
    public String getFilterName() {
        return filterName;
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
    public abstract boolean isMatches(Product product);


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
        return "Filter{filterName='" + filterName + "'}";
    }
}
