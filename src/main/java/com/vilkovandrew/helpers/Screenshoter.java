package com.vilkovandrew.helpers;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Attachment;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.io.File;
import java.nio.file.Files;

import static com.codeborne.selenide.Selenide.webdriver;

/**
 * Вспомогательный класс для создания скриншотов.
 *
 * @author Вилков Андрей
 */
public class Screenshoter {
    /**
     * Создание скриншота страницы.
     * <p>
     * Автор: Вилков Андрей
     * </p>
     *
     * @return возвращает скриншот в виде масива байт
     */
    @Attachment
    public static byte[] getScreen() {
        File screenshot = ((TakesScreenshot) webdriver().driver()).getScreenshotAs(OutputType.FILE);
        try {
            return Files.readAllBytes(screenshot.toPath());
        } catch (Exception e) {
            Assertions.fail("Не удалось получить скриншот", e);
        }
        return new byte[0];
    }

    /**
     * Создание скриншота элемента.
     * <p>
     * Автор: Вилков Андрей
     * </p>
     *
     * @param element экземпляр класса {@link SelenideElement}, скриншот которого будет сделан
     * @return возвращает скриншот в виде масива байт
     */
    @Attachment
    public static byte[] getScreen(SelenideElement element) {

        element.scrollTo();

        File screenshot = element.getScreenshotAs(OutputType.FILE);
        try {
            return Files.readAllBytes(screenshot.toPath());
        } catch (Exception e) {
            Assertions.fail("Не удалось получить скриншот", e);
        }
        return new byte[0];
    }
}
