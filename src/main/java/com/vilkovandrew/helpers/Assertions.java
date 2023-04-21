package com.vilkovandrew.helpers;

/**
 * Вспомогательный класс для работы с утверждениями.
 *
 * @author Вилков Андрей
 */
public class Assertions {

    /**
     * Проверка что переданное состояние true
     *
     * <p>
     * Автор: Вилков Андрей
     * </p>
     *
     * @param condition текущее состояние
     * @param message   сообщение об ошибке утверждения
     */
    public static void assertTrue(boolean condition, String message) {
        org.junit.jupiter.api.Assertions.assertTrue(condition, message);
    }

    /**
     * Проверка что ожидаемыое значение не равно актуальному
     *
     * <p>
     * Автор: Вилков Андрей
     * </p>
     *
     * @param unexpected ожидаемое значение
     * @param actual     актуальное значение
     * @param message    сообщение об ошибке утверждения
     */
    public static void assertNotEquals(int unexpected, int actual, String message) {
        org.junit.jupiter.api.Assertions.assertNotEquals(unexpected, actual, message);
    }

    /**
     * Ошибка теста с сообщением
     *
     * <p>
     * Автор: Вилков Андрей
     * </p>
     *
     * @param message сообщение об ошибке утверждения
     */
    public static void fail(String message) {
        org.junit.jupiter.api.Assertions.fail(message);
    }

    /**
     * Ошибка теста с сообщением и оригинальным исключением
     *
     * <p>
     * Автор: Вилков Андрей
     * </p>
     *
     * @param message сообщение об ошибке утверждения
     * @param cause   исходное исключение
     */
    public static void fail(String message, Throwable cause) {
        org.junit.jupiter.api.Assertions.fail(message, cause);
    }


}
