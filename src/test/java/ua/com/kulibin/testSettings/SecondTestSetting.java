package ua.com.kulibin.testSettings;

public interface SecondTestSetting {
    // колиество страниц которые будут проверяться на наличие цен
    int NUMBER_TEST_PAGES = 2;

    // если поставить true, то, в случаи если товар не имеет цену, на экран будет выводиться ссылка на этот товар
    boolean RECEIVE_INFORMATION_ABOUT_PRODUCT_WHERE_NO_PRICE = false;
}
