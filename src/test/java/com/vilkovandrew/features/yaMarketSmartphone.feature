@smartphone
Feature: Проверка работы фильтров Яндекс Макета

  Scenario Outline: Проверка на смартфонах
    Given перейти на сайт <url>
    And открыть каталог
    And навести курсор на раздел <раздел_каталога>
    And открыть подраздел <подраздел>
    When задать параметр
      | filterName    | Value | ValueFrom | ValueTo |
      | Производитель | Apple |           |         |
    Then проверяем соответствие всех товаров на всех страницах
      | filterName    | Value | ValueFrom | ValueTo |
      | Производитель | Apple |           |         |





    Examples:
      | url                      | раздел_каталога | подраздел |
      | https://market.yandex.ru | Электроника     | Смартфоны |