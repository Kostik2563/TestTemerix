package ua.com.kulibin.test;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ua.com.kulibin.WebDriverSettings;
import ua.com.kulibin.testSettings.FirstTestSetting;

/**
 * в разделе "Электроинструменты" / "Дрели"
 * на трех товарах с акционным тикетом  проверить есть ли в карточке товара акционная цена
 * Для этого, из раздела "Электроинструменты"  перейти в раздел "Дрели"  используя меню.
 * Рандомно, на страничке выбрать товар, провалиться в карточку товара и проверить наличие акционной и старой цены
 * Так для 3-х товаров (выбор количества проверяемых товаров сделать гибким)
 */
public class FirstTest extends WebDriverSettings implements FirstTestSetting {

    @BeforeMethod(description = "opens the drill section")
    public void openSection() {
        drillsPage.openSection();
    }

    @Test(description = "the test checks the presence of the old and current price in the product card a given number of times",
            invocationCount = NUMBER_PRODUCTS_FOR_FIRST_TEST)
    public void testAvailabilityPrices() {
        drillsPage.openPageWithPromotionalProducts(drillsPage.getNumberOfPages());
        drillsPage.fallInRandomProductCard();
        drillsPage.checkCurrentPrice();
        drillsPage.checkOldPrice();
    }

    @AfterMethod(description = "return to the previous page")
    public void goBack() {
        drillsPage.goBack();
    }

}