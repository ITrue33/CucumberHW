package com.vilkovandrew.listeners;

import com.codeborne.selenide.Selenide;
import com.vilkovandrew.helpers.Assertions;
import com.vilkovandrew.helpers.Properties;
import io.qameta.allure.Allure;
import io.qameta.allure.listener.StepLifecycleListener;
import io.qameta.allure.model.Status;
import io.qameta.allure.model.StepResult;
import org.openqa.selenium.OutputType;

import java.io.IOException;
import java.nio.file.Files;
import java.util.Objects;

/**
 * Класс для реакции на события жизненного цикла шага {@link io.qameta.allure.Step}.
 *
 * @author Вилков Андрей
 */
public class MyStepLifecycleListener implements StepLifecycleListener {

    /**
     * Создание скриншотов перед завершением шага.
     *
     * <p>
     * Автор: Вилков Андрей
     * </p>
     */
    @Override
    public void beforeStepStop(StepResult result) {
        if (
                !result.getStatus().equals(Status.PASSED)
                        ||
                        (Properties.appProperties.getScreenshotStrategy().equalsIgnoreCase("on")
                                &&
                                !result.getName().equalsIgnoreCase("Проверка соответствия товаров фильтрам")
                                &&
                                !result.getName().equalsIgnoreCase("Получаем список товаров с текущей страницы")
                                &&
                                !result.getName().startsWith("Проверяем что товар соответствует фильтру")
                                &&
                                !result.getName().startsWith("Установка значений для фильтров")
                        )
        )
            try {
                Allure.addAttachment(Long.toString(System.currentTimeMillis()),
                        Files.newInputStream(Objects.requireNonNull(Selenide.screenshot(OutputType.FILE)).toPath()));
            } catch (IOException e) {
                Assertions.fail("Не удалось сохранить скриншот");
            }
    }
}
