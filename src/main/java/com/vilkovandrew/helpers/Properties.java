package com.vilkovandrew.helpers;

import org.aeonbits.owner.ConfigFactory;

/**
 * Вспомогательный класс для работы со свойствами.
 *
 * @author Вилков Андрей
 */
public class Properties {
    /**
     * Экземпляр класса реализующий интерфейс {@link AppProperties}
     * <p>
     * Автор: Вилков Андрей
     * </p>
     */
    public static AppProperties appProperties = ConfigFactory.create(AppProperties.class);
}
